package com.payco.web.servlet.impl;

import com.payco.web.http.HttpRequest;
import com.payco.web.http.HttpResponse;
import com.payco.web.servlet.SimpleServlet;

import java.io.IOException;
import java.io.InputStream;

/**
 * 과제 6-1번 스펙 구현 위한 Hello Servlet
 */
public class Hello implements SimpleServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    InputStream responsePage =
        getClass().getClassLoader().getResourceAsStream("default/Hello.html");
    httpResponse.writeBody(responsePage.readAllBytes());
    httpResponse.sendResponse();
  }
}
