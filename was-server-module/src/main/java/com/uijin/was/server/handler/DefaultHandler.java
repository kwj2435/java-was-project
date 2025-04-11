package com.uijin.was.server.handler;

import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;
import com.uijin.was.model.ConfigModel;

/**
 * 기본 Handler
 */
public class DefaultHandler extends BaseHandler {

  public DefaultHandler(ConfigModel.Host host) {
    super(host);
  }

  @Override
  public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
    super.handleRequest(httpRequest, httpResponse);
  }
}
