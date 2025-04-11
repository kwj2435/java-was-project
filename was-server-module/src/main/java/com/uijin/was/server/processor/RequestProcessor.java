package com.uijin.was.server.processor;

import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;
import com.uijin.was.server.handler.DefaultHandler;
import com.uijin.was.server.handler.RequestHandler;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 사용자의 요청을 전달받아 Virtual Host 기반 처리
 * - virtualHosts Map에 등록된 HostHandler 존재 할 경우 해당 핸들러 호출
 * - virtualHosts Map에 등록된 핸들러가 없을 경우 기본(Default) 핸들러 호출
 */
public class RequestProcessor implements Runnable{

  private final static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
  private final DefaultHandler defaultHandler;
  private final Map<String, RequestHandler> virtualHosts;
  private final HttpRequest httpRequest;
  private final HttpResponse httpResponse;

  public RequestProcessor(
          HttpRequest httpRequest,
          HttpResponse httpResponse,
          Map<String, RequestHandler> virtualHosts,
          DefaultHandler defaultHandler) {
    this.httpRequest = httpRequest;
    this.virtualHosts = virtualHosts;
    this.httpResponse = httpResponse;
    this.defaultHandler = defaultHandler;
  }

  @Override
  public void run() {
    logger.info("Processing request - Host: {}, Path: {}", httpRequest.getHost(), httpRequest.getPath());

    String host = httpRequest.getHost();
    if(host != null && virtualHosts.containsKey(host)) {
      virtualHosts.get(host).handleRequest(httpRequest, httpResponse);
    } else {
      defaultHandler.handleRequest(httpRequest, httpResponse);
    }
  }
}
