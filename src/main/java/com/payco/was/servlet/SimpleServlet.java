package com.payco.was.servlet;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;

public interface SimpleServlet {
  void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
