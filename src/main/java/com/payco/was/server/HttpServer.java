package com.payco.was.server;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import com.payco.was.model.ConfigModel.Host;
import com.payco.was.server.handler.DefaultHandler;
import com.payco.was.server.handler.RequestHandler;
import com.payco.was.server.processor.RequestProcessor;
import com.payco.was.utils.ConfigUtils;
import com.payco.was.utils.HttpUtils;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 해당 클래스는 Http 요청을 수신 및 처리하는 HttpServer
 * - 이 클래스는 Socket 기반으로 동작하며 클라이언트의 요청을 받아, Request Processer로 전달하여 처리
 * - VirtualHost 기능을 지원하여, 정해진 각 호스트별 요청 처리
 */
public class HttpServer {
  private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
  private static final Map<String, RequestHandler> virtualHosts = new HashMap<>();
  private static final int NUM_THREADS = 50;
  private final ConfigUtils configUtils = new ConfigUtils();
  private final DefaultHandler defaultHandler;
  private final int port;

  public HttpServer(int port) throws IOException {
    this.port = port;
    this.defaultHandler = new DefaultHandler(configUtils.getHost("default"));
  }

  public void start() throws IOException {
    HttpUtils httpUtils = new HttpUtils();
    ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
    Socket request;

    initVirtualHosts();

    try (ServerSocket server = new ServerSocket(port)) {
      logger.info("Was Server Started, Accepting connections on port {}", server.getLocalPort());
      while (true) {
        try {
          request = server.accept();
          HttpRequest httpRequest = httpUtils.convertToHttpRequest(request.getInputStream());
          HttpResponse httpResponse = httpUtils.convertToHttpResponse(request.getOutputStream());
          if(httpRequest == null) {
            request.close();
          } else {
            Runnable r = new RequestProcessor(httpRequest, httpResponse, virtualHosts, defaultHandler);
            pool.submit(r);
          }
        } catch (IOException ex) {
          logger.error("Error accepting connection", ex);
        }
      }
    }
  }

  /**
   * 서버 구동시점 virtualHosts 세팅
   */
  private void initVirtualHosts() {
    Map<String, Host> hostMap = configUtils.getHostMap();
    hostMap.forEach((hostName, host) -> {
      try {
        String handlerClassName = host.getHandlerName();
        Class<?> handlerClass = Class.forName(handlerClassName);
        Object handlerInstance = handlerClass.getDeclaredConstructor(Host.class).newInstance(host);

        virtualHosts.put(host.getHost(), (RequestHandler) handlerInstance);
        logger.info("Virtual host '{}' is mapped to '{}'", hostName, handlerClassName);
      } catch (Exception e) {
        logger.error("Error initializing virtual host", e);
        // Exception 발생시 기본 Handler 매핑
        virtualHosts.put(host.getHost(), new DefaultHandler(configUtils.getHost("default")));
      }
    });
  }
}
