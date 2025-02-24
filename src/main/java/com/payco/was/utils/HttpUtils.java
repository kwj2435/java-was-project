package com.payco.was.utils;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
  private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

  public HttpRequest convertToHttpRequest(InputStream request) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(request));
    String line;
    String host = null;
    String path = null;
    String method = null;

    while ((line = reader.readLine()) != null && !line.isEmpty()) {
      String lowerCaseLine = line.toUpperCase();
      if (lowerCaseLine.startsWith("HOST:")) {
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
      throw new IOException("Missing host or method in request");
    }
    return new HttpRequest(method, host, path);
  }

  public HttpResponse convertToHttpResponse(OutputStream request) throws IOException {
    return new HttpResponse(request);
  }
}
