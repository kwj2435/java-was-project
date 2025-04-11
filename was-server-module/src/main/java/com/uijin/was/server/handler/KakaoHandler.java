package com.uijin.was.server.handler;

import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;
import com.uijin.was.model.ConfigModel;

/**
 * kakao.com 호스트 Handler
 */
public class KakaoHandler extends BaseHandler{

  public KakaoHandler(ConfigModel.Host host) {
    super(host);
  }

  @Override
  public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
    super.handleRequest(httpRequest, httpResponse);
  }
}
