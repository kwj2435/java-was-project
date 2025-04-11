package com.uijin.web.servlet;

import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;

import java.io.IOException;

/**
 * 과제 스펙 6번 구현 위한 Servlet 인터페이스
 */
public interface SimpleServlet {
  void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
