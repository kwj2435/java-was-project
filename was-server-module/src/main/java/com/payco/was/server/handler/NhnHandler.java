package com.payco.was.server.handler;

import com.payco.web.http.HttpRequest;
import com.payco.web.http.HttpResponse;
import com.payco.was.model.ConfigModel;

/**
 * nhn.com 호스트 Handler
 */
public class NhnHandler extends BaseHandler{

  public NhnHandler(ConfigModel.Host host) {
    super(host);
  }

  @Override
  public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
    super.handleRequest(httpRequest, httpResponse);
  }
}
