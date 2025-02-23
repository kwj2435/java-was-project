package com.payco.was.servlet;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface SimpleServlet {
  void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
