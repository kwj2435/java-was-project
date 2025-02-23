package com.payco.was.server.handler;

import com.payco.was.utils.ConfigUtils;
import com.sun.net.httpserver.HttpExchange;

/**
 * nhn.com VirtualHost 핸들러
 */
public class NhnHandler extends BaseHandler {

  public NhnHandler() {
    super(ConfigUtils.getHost("nhn"));
  }

  // 사용자 요청 처리
  @Override
  public void handle(HttpExchange exchange) {
    handleRequest(exchange);
  }
}
