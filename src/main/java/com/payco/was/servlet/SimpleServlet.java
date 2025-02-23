package com.payco.was.servlet;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import java.io.IOException;

public interface SimpleServlet {
  void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
