package com.payco.was.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.Map;

/**
 * 요청의 Host 헤더에 맞는 가상 호스트 핸들러를 선택하여 처리하는 핸들러
 * 가상 호스트가 없으면 기본 핸들러를 호출
 */
public class GlobalHandler implements HttpHandler {

  private final Map<String, HttpHandler> virtualHosts;
  private final HttpHandler defaultHandler;

  public GlobalHandler(Map<String, HttpHandler> virtualHosts, HttpHandler defaultHandler) {
    this.virtualHosts = virtualHosts;
    this.defaultHandler = defaultHandler;
  }

  /**
   * HTTP 요청을 처리
   * 요청의 'Host' 헤더를 확인하고 해당하는 핸들러로 요청 처리
   * 해당하는 핸들러가 없으면 기본 핸들러 호출
   */
  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String host = httpExchange.getRequestHeaders().getFirst("Host");

    if(host != null && virtualHosts.containsKey(host)) {
      virtualHosts.get(host).handle(httpExchange);
    } else {
      defaultHandler.handle(httpExchange);
    }
  }
}
