package com.payco.was.server.handler;

import com.payco.was.enums.HttpStatus;
import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import com.payco.was.model.ConfigModel.Host;
import com.payco.was.model.HeaderModel.HeaderDto;
import com.payco.was.servlet.ServletService;
import com.payco.was.servlet.SimpleServlet;
import com.payco.was.utils.ConfigUtils;
import com.payco.was.utils.HttpUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseHandler implements RequestHandler {
  protected static final Logger logger = LoggerFactory.getLogger(BaseHandler.class);
  protected static final ServletService servletService = new ServletService();
  protected Host host;
  protected String httpRoot;

  public BaseHandler(Host host) {
    this.host = host;
    this.httpRoot = host.getHttpRoot();
  }

  /**
   * 사용자 요청 처리
   */
  public void handleRequest(HeaderDto headerDto, OutputStream out) {
    byte[] responseBytes;
    String responseCode;
    String contentType = "text/html; charset=UTF-8";

    try {
      if(isForbidden(httpRoot, headerDto.getPath())) {
        logger.warn("Forbidden request: {}", headerDto.getPath());
        responseBytes = getErrorResponseBytes(httpRoot, host.getErrorPage().getForbidden403());
        responseCode = HttpStatus.FORBIDDEN.getCode();
      } else {
        SimpleServlet servlet = servletService.getServlet(headerDto.getPath());
        if (servlet != null) {
          logger.info("Servlet Found: {}", headerDto.getPath());
          HttpRequest httpRequest = HttpUtils.convertToHttpRequest(headerDto);
          HttpResponse httpResponse = HttpUtils.convertToHttpResponse(out);

          servlet.service(httpRequest, httpResponse);

          responseBytes = "200".getBytes();
          responseCode = HttpStatus.OK.getCode();
        } else {
          logger.info("Servlet Not Found: {}", headerDto.getPath());
          responseBytes = getErrorResponseBytes(httpRoot, host.getErrorPage().getNotFound404());
          responseCode = HttpStatus.NOT_FOUND.getCode();
        }
      }

      sendHeader(out, responseCode, contentType, responseBytes.length);
      out.write(responseBytes);
      out.flush();
    } catch (Exception e) {
      logger.error("Error while handling request", e);
      send500ErrorPage(out, httpRoot, host.getErrorPage().getInternalServerError500());
    }
  }

  /**
   * Error Page 조회 및 Response 용 Byte 변환
   */
  private byte[] getErrorResponseBytes(String httpRoot, String errorPageName) throws IOException {
    String errorPagePath = Paths.get(httpRoot, errorPageName).toString();
    InputStream errorPageStream = getClass().getClassLoader().getResourceAsStream(errorPagePath);
    byte[] responseBytes;
    if (errorPageStream != null) {
      responseBytes = errorPageStream.readAllBytes();
    } else {
      responseBytes = "500 Internal Server Error".getBytes();
    }
    return responseBytes;
  }

  /**
   * 403 Error Page 조건 체크
   * - 경로 탐색 공격
   * - 허용되지 않은 확장자
   */
  private boolean isForbidden(String httpRoot, String requestUrl) {
    // 요청 URI 가져오기
    if (httpRoot == null) {
      return true; // 요청 URI를 찾을 수 없으면 차단
    }

    // 경로 탐색 공격 방지
    logger.info("httpRoot : {}", httpRoot);
    logger.info("requestUrl : {}", requestUrl);
    Path requestPath = Paths.get(httpRoot, requestUrl).normalize();
    if (!requestPath.startsWith(Paths.get(httpRoot))) {
      return true;
    }

    // 허용되지 않은 확장자 검사
    int lastDotIndex = requestPath.toString().lastIndexOf('.');
    if (lastDotIndex == -1) {
      return false;
    }
    String extension = requestPath.toString().substring(lastDotIndex + 1);

    return ConfigUtils.getDisallowedExtensions().contains(extension);
  }

  /**
   * 500 에러 페이지 전달 -> Client
   * - 500 에러 HTML 읽기 중 실패할 경우 파일 전달 불가하여, Error 로그만 기록
   */
  private void send500ErrorPage(OutputStream out, String httpRoot, String errorPage){
    byte[] errorResponseBytes;

    try {
      errorResponseBytes = getErrorResponseBytes(httpRoot, errorPage);
    } catch (IOException e) {
      logger.error("Failed to load error page: {}", errorPage, e);
      errorResponseBytes = "Internal Server Error".getBytes();
    }

    try {
      sendHeader(
          out,
          HttpStatus.INTERNAL_SERVER_ERROR.getCode(),
          "text/html; charset=UTF-8",
          errorResponseBytes.length
      );
      out.write(errorResponseBytes);
      out.flush();
    } catch (IOException e) { // 추가 IOException 발생 시 500 에러페이지 파일 전달 불가하여, Error 로그로만 기록
      logger.error("Failed to send error response", e);
    }
  }

  /**
   * 응답 Header 전달 -> Client
   */
  private void sendHeader(OutputStream out, String responseCode, String contentType, int length)
      throws IOException {
    StringBuilder response = new StringBuilder();

    response.append(responseCode).append("\r\n");
    response.append("Date: ").append(new Date()).append("\r\n");
    response.append("Server: JHTTP 2.0").append("\r\n");
    response.append("Content-length: ").append(length).append("\r\n");
    response.append("Content-type: ").append(contentType).append("\r\n\r\n");

    out.write(response.toString().getBytes(StandardCharsets.UTF_8));
  }
}
