package com.payco.was.utils;

import com.payco.web.http.HttpRequest;
import com.payco.web.http.HttpResponse;
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

    // 클라이언트의 소켓 연결 이후 빈 요청이 들어올 경우 해당 요청을 무시하고, Socket 종료
    // - Connection: keep-alive
    // - 소켓 연결은 하였으나, 실제 데이터를 전송하지 않은 경우
    if(host == null || path == null || method == null) {
      return null;
    }
    logger.info("Parsed Http Request Success. Method: {}, Host: {}, Path: {}", method, host, path);
    return new HttpRequest(method, host, path);
  }

  public HttpResponse convertToHttpResponse(OutputStream request) throws IOException {
    return new HttpResponse(request);
  }
}
