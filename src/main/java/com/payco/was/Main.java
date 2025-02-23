package com.payco.was;

import com.payco.was.server.SimpleHttpServer;
import com.payco.was.utils.ConfigUtils;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    try {
      int serverPort = ConfigUtils.getPort();

      SimpleHttpServer httpServer = new SimpleHttpServer(serverPort);
      httpServer.start();

      logger.info("HTTP server started on port {}", serverPort);
    } catch (IOException e) {
      logger.error("HTTP server start failed", e);
    }
  }
}