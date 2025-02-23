package com.payco.was.server.processor;

import com.payco.was.model.HeaderModel.HeaderDto;
import com.payco.was.server.handler.DefaultHandler;
import com.payco.was.server.handler.RequestHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestProcessor implements Runnable{

  private final static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
  private final static DefaultHandler defaultHandler = new DefaultHandler();

  private final Map<String, RequestHandler> virtualHosts;
  private final Socket connection;
  private final HeaderDto headerDto;

  public RequestProcessor(Socket connection, Map<String, RequestHandler> virtualHosts, HeaderDto headerDto) {
    this.connection = connection;
    this.virtualHosts = virtualHosts;
    this.headerDto = headerDto;
  }

  @Override
  public void run() {
    try {
      OutputStream out = connection.getOutputStream();
      String host = headerDto.getHost();
      logger.info("Processing request - Host: {}, Path: {}", host, headerDto.getPath());

      if(host != null && virtualHosts.containsKey(host)) {
        virtualHosts.get(host).handleRequest(headerDto, out);
      } else {
        defaultHandler.handleRequest(headerDto, out);
      }
    } catch (IOException e) {
      logger.error("Error processing request: {}", e.getMessage());
    } finally {
      try {
        connection.close();
      } catch (IOException e) {
        logger.error("Failed to close connection: {}", e.getMessage());
      }
    }
  }
}
