package com.payco.was;

import com.payco.was.server.HttpServer;
import com.payco.was.utils.ConfigUtils;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    try {
      createLogDirectory();
      HttpServer webserver = new HttpServer(new ConfigUtils().getPort());
      webserver.start();
    } catch (IOException ex) {
      logger.error("Server could not start", ex);
    }
  }

  // 로그 폴더 생성
  private static void createLogDirectory() {
    File logDir = new File("logs");
    if (!logDir.exists() && logDir.mkdirs()) {
      logger.info("Log directory created: {}", logDir.getAbsolutePath());
    }
  }
}