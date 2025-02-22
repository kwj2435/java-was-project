package com.payco.was.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class GlobalHandler implements HttpHandler {

  private Map<String, HttpHandler> virtualHosts;
  private HttpHandler defaultHandler;

  public GlobalHandler(Map<String, HttpHandler> virtualHosts, HttpHandler defaultHandler) {
    this.virtualHosts = virtualHosts;
    this.defaultHandler = defaultHandler;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String response = "123123";
    String host = httpExchange.getRequestHeaders().getFirst("Host");

    if(host != null && virtualHosts.containsKey(host)) {
      virtualHosts.get(host).handle(httpExchange);
    } else {
      defaultHandler.handle(httpExchange);
    }

    // 응답 헤더 설정
    httpExchange.sendResponseHeaders(200, response.getBytes().length);

    // 응답 본문 쓰기
    OutputStream os = httpExchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
