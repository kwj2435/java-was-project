package com.payco.was.servlet.service;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import com.payco.was.servlet.SimpleServlet;

/**
 * 과제 6-2번 스펙 구현 위한 Hello Servlet
 */
public class Hello implements SimpleServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.writeBody("Service - Hello World!");
  }
}
