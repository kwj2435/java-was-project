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

public class SimpleHttpServer {
    private static final Map<String, HttpHandler> virtualHosts = new HashMap<>();
    private final HttpServer httpServer;

    public SimpleHttpServer(int port) throws IOException {
      httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void start() {
      virtualHosts.put("payco.com", new PaycoHandler());
      virtualHosts.put("nhn.com", new NhnHandler());

      httpServer.createContext("/", new GlobalHandler(virtualHosts, new DefaultHandler()));
      httpServer.start();
    }

    public void stop() {
      httpServer.stop(0);
    }
}
