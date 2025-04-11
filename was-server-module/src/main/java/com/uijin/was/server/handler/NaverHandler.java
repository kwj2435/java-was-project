package com.uijin.was.server.handler;

import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;
import com.uijin.was.model.ConfigModel;

/**
 * naver.com 호스트 Handler
 */
public class NaverHandler extends BaseHandler{

  public NaverHandler(ConfigModel.Host host) {
    super(host);
  }

  @Override
  public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
    super.handleRequest(httpRequest, httpResponse);
  }
}
