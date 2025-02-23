package com.payco.was.server;

import com.payco.was.model.ConfigModel.Host;
import com.payco.was.model.HeaderModel.HeaderDto;
import com.payco.was.server.handler.DefaultHandler;
import com.payco.was.server.handler.RequestHandler;
import com.payco.was.server.processor.RequestProcessor;
import com.payco.was.utils.ConfigUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer {
  private static final Map<String, RequestHandler> virtualHosts = new HashMap<>();
  private static final int NUM_THREADS = 50;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final int port;

  public HttpServer(int port) throws IOException {
    this.port = port;
  }

  public void start() throws IOException {
    initVirtualHosts();
    ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

    try (ServerSocket server = new ServerSocket(port)) {
      logger.info("Was Server Started, Accepting connections on port {}", server.getLocalPort());
      while (true) {
        try {
          Socket request = server.accept();
          HeaderDto host = readHostHeader(request.getInputStream());
          Runnable r = new RequestProcessor(request, virtualHosts, host);
          pool.submit(r);
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
    Map<String, Host> hostMap = ConfigUtils.getHostMap();
    hostMap.forEach((hostName, host) -> {
      try {
        String handlerClassName = host.getHandlerName();
        Class<?> handlerClass = Class.forName(handlerClassName);
        Object handlerInstance = handlerClass.getDeclaredConstructor().newInstance();

        virtualHosts.put(hostName, (RequestHandler) handlerInstance);
        logger.info("Virtual host '{}' is mapped to '{}'", hostName, handlerClassName);
      } catch (Exception e) {
        logger.error("Error initializing virtual host", e);
        // Exception 발생시 기본 Handler 매핑
        virtualHosts.put(hostName, new DefaultHandler());
      }
    });
  }

  /**
   * Host Header 가져오기
   * InputStream 은 한번 읽은 후 다시 읽을 수 없기 때문에, HeaderDto 로 변환하여 사용
   */
  private HeaderDto readHostHeader(InputStream request) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(request));
    String line;
    String host = null;
    String path = null;
    String method = null;

    while ((line = reader.readLine()) != null && !line.isEmpty()) {
      String lowerCaseLine = line.toLowerCase();
      if (lowerCaseLine.startsWith("host:")) {
        host = line.substring(5).trim();
      } else if(line.startsWith("GET") || line.startsWith("POST") || line.startsWith("PUT")
          || line.startsWith("DELETE") || line.startsWith("HEAD") || line.startsWith("OPTIONS")) {
        method = line.split(" ")[0];
        path = line.split(" ")[1];
      }
    }

    // fixme 요청이 두번 들어오는 경우 처리해야함
    if(host == null || path == null || method == null) {
      logger.error("Invalid request Header");
    }
    return new HeaderDto(host, path, method);
  }
}
