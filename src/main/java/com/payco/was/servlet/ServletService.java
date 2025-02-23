package com.payco.was.servlet;

public class ServletService {

  /**
   * URL 별 Servlet 라우팅
   * URL 에 매핑되는 Servlet 을 찾지 못한 경우 return null -> 404 Not Found
   */
  public SimpleServlet getServlet(String url) {
    if("/hello".equals(url)) {
      return new Hello();
    } else if ("/service.Hello".equals(url)) {
      return new com.payco.was.servlet.service.Hello();
    } else if ("/time".equals(url)) {
      return new CurrentTimeServlet();
    }
    return null;
  }
}
