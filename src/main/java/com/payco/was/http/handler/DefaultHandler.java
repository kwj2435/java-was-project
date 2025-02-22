package com.payco.was.http.handler;

import com.payco.was.model.ConfigModel.Host;
import com.payco.was.utils.ConfigUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public class DefaultHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Host host = ConfigUtils.getHost("default");
    String httpRoot = host.getHttpRoot();
    String url = exchange.getRequestURI().getPath();

    String requestPath = Paths.get(httpRoot, url).normalize().toString();
    String filePath = requestPath.startsWith(httpRoot)
        ? "/200_ok.html" :
        httpRoot + "/" + host.getErrorPage().getForbidden403();

    InputStream fileStream = getClass().getResourceAsStream(filePath);

    if (fileStream == null) {
      String errorPagePath = httpRoot + "/" + host.getErrorPage().getNotFound404();
      fileStream = getClass().getResourceAsStream(errorPagePath);
      exchange.sendResponseHeaders(404, fileStream.readAllBytes().length);
      exchange.getResponseBody().write(fileStream.readAllBytes());
    } else {
      exchange.sendResponseHeaders(200, fileStream.readAllBytes().length);
      exchange.getResponseBody().write(fileStream.readAllBytes());
    }

    exchange.getResponseBody().close();
  }
}
