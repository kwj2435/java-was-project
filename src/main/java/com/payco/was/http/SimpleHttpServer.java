package com.payco.was.http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    private final HttpServer httpServer;

    public SimpleHttpServer(int port) throws IOException {
      httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void createContext(String path, HttpHandler handler) {
      httpServer.createContext(path, handler);
    }

    public void start() {
      httpServer.start();
    }

    public void stop() {
      httpServer.stop(0);
    }
}
