package com.payco.web.servlet;

import com.payco.web.http.HttpRequest;
import com.payco.web.http.HttpResponse;

import java.io.IOException;

public interface SimpleServlet {
  void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
