package com.payco.web.servlet;

import com.payco.web.http.HttpRequest;
import com.payco.web.http.HttpResponse;

import java.io.IOException;

/**
 * 과제 스펙 6번 구현 위한 Servlet 인터페이스
 */
public interface SimpleServlet {
  void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
