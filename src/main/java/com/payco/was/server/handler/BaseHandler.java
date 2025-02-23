package com.payco.was.server.handler;

import com.payco.was.enums.HttpStatus;
import com.payco.was.model.ConfigModel.Host;
import com.payco.was.utils.ConfigUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VirtualHost 공통 Handler
 * HttpHandler Overriding 메서드를 BaseHandler 클래스가 아닌 하위 클래스에서 구현해야 하기에 abstract class 사용
 */
public abstract class BaseHandler implements HttpHandler {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  protected Host host;
  protected String httpRoot;

  public BaseHandler(Host host) {
    this.host = host;
    this.httpRoot = host.getHttpRoot();
  }

  /**
   * 사용자 요청 처리
   */
  protected void handleRequest(HttpExchange exchange) {
    byte[] responseBytes;
    int responseCode;

    // 디렉터리 탐색 방어 코드
    try {
      if(isForbidden(httpRoot, exchange)) {
        responseBytes = getErrorResponseBytes(httpRoot, host.getErrorPage().getForbidden403());
        responseCode = HttpStatus.FORBIDDEN.getCode();
      } else {
        if (false) {
          responseBytes = "200".getBytes();
          responseCode = HttpStatus.OK.getCode();
        } else {
          responseBytes = getErrorResponseBytes(httpRoot, host.getErrorPage().getNotFound404());
          responseCode = HttpStatus.NOT_FOUND.getCode();
        }
      }

      exchange.sendResponseHeaders(responseCode, responseBytes.length);
      exchange.getResponseBody().write(responseBytes);
      exchange.getResponseBody().close();
    } catch (Exception e) {
      logger.error("Error while handling request", e);
      send500ErrorPage(exchange, httpRoot, host.getErrorPage().getInternalServerError500());
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
  private boolean isForbidden(String httpRoot, HttpExchange httpExchange) {
    // 경로 탐색 공격
    Path requestpath = Paths.get(httpRoot, httpExchange.getRequestURI().getPath()).normalize();
    if(!requestpath.startsWith(Paths.get(httpRoot))) {
      return true;
    }

    // 허용되지 않은 확장자
    int lastDotIndex = requestpath.toString().lastIndexOf('.');
    if(lastDotIndex == -1) {
      return false;
    }
    String extension = requestpath.toString().substring(lastDotIndex + 1);

    return ConfigUtils.getDisallowedExtensions().contains(extension);
  }

  /**
   *
   * 500 에러 페이지 전달 메서드
   * - 500 에러 HTML 읽기 중 실패할 경우 파일 전달 불가하여, Error 로그만 기록
   */
  private void send500ErrorPage(HttpExchange exchange, String httpRoot, String errorPage){
    byte[] errorResponseBytes;

    try {
      errorResponseBytes = getErrorResponseBytes(httpRoot, errorPage);
    } catch (IOException e) {
      logger.error("Failed to load error page: {}", errorPage, e);
      errorResponseBytes = "Internal Server Error".getBytes();
    }

    try {
      exchange.sendResponseHeaders(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), errorResponseBytes.length);
      try (OutputStream responseBody = exchange.getResponseBody()) {
        responseBody.write(errorResponseBytes);
      }
    } catch (IOException e) { // 추가 IOException 발생 시 500 에러페이지 파일 전달 불가하여, Error 로그로만 기록
      logger.error("Failed to send error response", e);
    }
  }
}
