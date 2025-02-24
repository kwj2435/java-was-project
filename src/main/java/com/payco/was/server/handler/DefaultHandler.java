package com.payco.was.server.handler;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import com.payco.was.model.ConfigModel;

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
