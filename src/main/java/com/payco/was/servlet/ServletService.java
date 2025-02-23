package com.payco.was.servlet;

public class ServletService {

  public SimpleServlet getServlet(String url) {
    if("/Hello".equals(url)) {
      return new Hello();
    } else if ("/service.Hello".equals(url)) {
      return new com.payco.was.servlet.service.Hello();
    }
    return null;
  }
}
