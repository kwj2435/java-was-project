package com.payco.was.servlet;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;

/**
 * 과제 6-1번 스펙 구현 위한 Hello Servlet
 */
public class Hello implements SimpleServlet{

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.writeBody("Hello World!");
  }
}
