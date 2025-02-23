package com.payco.was.servlet;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import java.time.LocalDateTime;

public class CurrentTimeServlet implements SimpleServlet{

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.writeBody(LocalDateTime.now().toString());
  }
}
