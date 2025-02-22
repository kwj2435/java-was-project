package com.payco.was;

import com.payco.was.http.SimpleHttpServer;
import com.payco.was.http.handler.DefaultHandler;
import com.payco.was.http.handler.GlobalHandler;
import com.payco.was.http.handler.PaycoHandler;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

  private static Map<String, HttpHandler> virtualHosts = new HashMap<>();

  public static void main(String[] args) {
    try {
      SimpleHttpServer httpServer = new SimpleHttpServer(9001);

      virtualHosts.put("payco.com", new PaycoHandler());
      virtualHosts.put("nhn.com", new PaycoHandler());

      httpServer.createContext("/", new GlobalHandler(virtualHosts, new DefaultHandler()));

      httpServer.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}