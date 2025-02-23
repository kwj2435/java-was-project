package com.payco.was.server.handler;

import com.payco.was.utils.ConfigUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * VirtualHost 공통 Handler
 */
public abstract class BaseHandler implements HttpHandler {

  /**
   * Error Page 조회 및 Response 용 Byte 변환
   */
  protected byte[] getErrorResponseBytes(String httpRoot, String errorPageName) throws IOException {
    String errorPagePath = Paths.get(httpRoot, errorPageName).toString();
    InputStream errorPageStream = getClass().getClassLoader().getResourceAsStream(errorPagePath);
    byte[] responseBytes;
    if(errorPageStream != null) {
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
  protected boolean isForbidden(String httpRoot, HttpExchange httpExchange) {
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
}
