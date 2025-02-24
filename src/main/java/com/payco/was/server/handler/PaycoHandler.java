package com.payco.was.server.handler;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import com.payco.was.model.ConfigModel;

/**
 * payco.com 호스트 Handler
 */
public class PaycoHandler extends BaseHandler{

  public PaycoHandler(ConfigModel.Host host) {
    super(host);
  }

  @Override
  public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
    super.handleRequest(httpRequest, httpResponse);
  }
}
