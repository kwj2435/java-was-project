package com.payco.was.server.handler;

import com.payco.was.utils.ConfigUtils;
import com.sun.net.httpserver.HttpExchange;

/**
 * payco.com VirtualHost 핸들러
 */
public class PaycoHandler extends BaseHandler {

  public PaycoHandler() {
    super(ConfigUtils.getHost("payco"));
  }

  // 사용자 요청 처리
  @Override
  public void handle(HttpExchange exchange) {
    handleRequest(exchange);
  }
}
