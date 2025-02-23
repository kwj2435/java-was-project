package com.payco.was.server;

import com.payco.was.server.handler.DefaultHandler;
import com.payco.was.server.handler.GlobalHandler;
import com.payco.was.server.handler.NhnHandler;
import com.payco.was.server.handler.PaycoHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHttpServer {
  private final Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);
  private static final Map<String, HttpHandler> virtualHosts = new HashMap<>();
  private final HttpServer httpServer;

  public SimpleHttpServer(int port) throws IOException {
    httpServer = HttpServer.create(new InetSocketAddress(port), 0);
  }

  public void start() {
    // virtualHost 도메인 및 Host 등록
    virtualHosts.put("payco.com", new PaycoHandler());
    virtualHosts.put("nhn.com", new NhnHandler());

    httpServer.createContext("/", new GlobalHandler(virtualHosts, new DefaultHandler()));
    httpServer.start();

    logger.info("HTTP server started on port {}", httpServer.getAddress().getPort());
  }

  public void stop() {  //fixme 사용하자
    httpServer.stop(0);
  }
}
