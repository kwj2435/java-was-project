package com.payco.api;

import com.payco.api.http.SimpleHttpServer;
import com.payco.api.http.handler.HelloHandler;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    try {
      SimpleHttpServer httpServer = new SimpleHttpServer(9001);

      httpServer.createContext("/hello", new HelloHandler());

      httpServer.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}