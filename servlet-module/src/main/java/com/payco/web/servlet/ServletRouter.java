package com.payco.web.servlet;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletRouter {

  private static final Logger logger = LoggerFactory.getLogger(ServletRouter.class);
  private static final Map<String, String> servletMapping = new HashMap<>();
  private static final String basePackage = "com.payco.web.servlet.impl.";  //추후 설정 파일로 동적 할당

  // 추후 설정 파일로 동적 할당
  public ServletRouter() {
    servletMapping.put("/Hello", "Hello");
    servletMapping.put("/service.Hello", "service.Hello");
    servletMapping.put("/Time", "CurrentTime");
  }
  /**
   * URL 별 Servlet 라우팅
   * URL 에 매핑되는 Servlet 을 찾지 못한 경우 return null -> 404 Not Found
   */
  public SimpleServlet getServlet(String url) {
    StringBuilder servletClassPath = new StringBuilder(basePackage);

    String className = servletMapping.get(url);
    if(className == null) {
      return null;
    }

    servletClassPath.append(className);

    try {
      Class<?> clazz = Class.forName(servletClassPath.toString());
      return (SimpleServlet) clazz.getDeclaredConstructor().newInstance();
    } catch (ClassNotFoundException e) {
      return null;
    } catch (Exception e) {
      logger.error("Occurred Exception While Find Servlet", e);
      throw new RuntimeException();
    }
  }
}
