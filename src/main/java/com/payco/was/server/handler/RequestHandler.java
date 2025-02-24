package com.payco.was.server.handler;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;

/**
 * Http Request Handler Interface
 * 해당 인터페이스 구현체는 사용자의 Http 요청 처리에 대한 로직 작성 필요
 */
public interface RequestHandler {
  void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse);
}
