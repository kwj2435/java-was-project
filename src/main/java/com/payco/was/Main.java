package com.payco.was;

import com.payco.was.http.SimpleHttpServer;
import com.payco.was.http.handler.DefaultHandler;
import com.payco.was.http.handler.GlobalHandler;
import com.payco.was.http.handler.NhnHandler;
import com.payco.was.http.handler.PaycoHandler;
import com.payco.was.utils.ConfigUtils;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private static final Map<String, HttpHandler> virtualHosts = new HashMap<>();

  public static void main(String[] args) {
    try {
      int serverPort = ConfigUtils.getPort();
      SimpleHttpServer httpServer = new SimpleHttpServer(serverPort);

      virtualHosts.put("payco.com", new PaycoHandler());
      virtualHosts.put("nhn.com", new NhnHandler());

      httpServer.createContext("/", new GlobalHandler(virtualHosts, new DefaultHandler()));

      httpServer.start();
      logger.info("HTTP server started on port 9001");
    } catch (IOException e) {
      logger.error("HTTP server start failed");
      e.printStackTrace();
    }
  }
}