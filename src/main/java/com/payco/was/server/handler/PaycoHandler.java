package com.payco.was.server.handler;

import com.payco.was.enums.HttpStatus;
import com.payco.was.model.ConfigModel.Host;
import com.payco.was.utils.ConfigUtils;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class PaycoHandler extends BaseHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Host host = ConfigUtils.getHost("payco");
    String httpRoot = host.getHttpRoot();

    byte[] responseBytes;
    int responseCode;

    // 디렉터리 탐색 방어 코드
    if(isForbidden(httpRoot, exchange)) {
      responseBytes = getErrorResponseBytes(httpRoot, host.getErrorPage().getForbidden403());
      responseCode = HttpStatus.FORBIDDEN.getCode();
    } else {
      if (false) {
        responseBytes = "200".getBytes();
        responseCode = HttpStatus.OK.getCode();;
      } else {
        responseBytes = getErrorResponseBytes(httpRoot, host.getErrorPage().getNotFound404());
        responseCode = HttpStatus.NOT_FOUND.getCode();;
      }
    }

    exchange.sendResponseHeaders(responseCode, responseBytes.length);
    exchange.getResponseBody().write(responseBytes);
    exchange.getResponseBody().close();
  }
}
