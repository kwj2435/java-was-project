package com.uijin.web.servlet.impl.service;

import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;
import com.uijin.web.servlet.SimpleServlet;
import java.io.IOException;
import java.io.InputStream;

/**
 * 과제 6-2번 스펙 구현 위한 Hello Servlet
 */
public class Hello implements SimpleServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    InputStream responsePage =
        getClass().getClassLoader().getResourceAsStream("default/ServiceHello.html");
    httpResponse.writeBody(responsePage.readAllBytes());
    httpResponse.sendResponse();
  }
}
