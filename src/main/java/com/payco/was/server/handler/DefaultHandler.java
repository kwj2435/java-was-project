package com.payco.was.server.handler;

import com.payco.was.utils.ConfigUtils;
import com.sun.net.httpserver.HttpExchange;

/**
 * Default VirtualHost 핸들러
 */
public class DefaultHandler extends BaseHandler {

  public DefaultHandler() {
    super(ConfigUtils.getHost("default"));
  }

  // 사용자 요청 처리
  @Override
  public void handle(HttpExchange exchange) {
    handleRequest(exchange);
  }
}
