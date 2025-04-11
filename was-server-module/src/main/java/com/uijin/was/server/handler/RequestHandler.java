package com.uijin.was.server.handler;

import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;

/**
 * Http Request Handler Interface
 * 해당 인터페이스 구현체는 사용자의 Http 요청 처리에 대한 로직 작성 필요
 */
public interface RequestHandler {
  void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse);
}
