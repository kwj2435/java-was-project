package com.payco.was;

import com.payco.was.server.HttpServer;
import com.payco.was.utils.ConfigUtils;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    try {
      HttpServer webserver = new HttpServer(ConfigUtils.getPort());
      webserver.start();
    } catch (IOException ex) {
      logger.error("Server could not start", ex);
    }
  }
}