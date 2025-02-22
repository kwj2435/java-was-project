package com.payco.api.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class HelloHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String response = "Hello, world!";

    // 응답 헤더 설정
    httpExchange.sendResponseHeaders(200, response.getBytes().length);

    // 응답 본문 쓰기
    OutputStream os = httpExchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
