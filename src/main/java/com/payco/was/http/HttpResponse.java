package com.payco.was.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
  private final OutputStream outputStream;
  private String statusCode = "200 OK";
  private final Map<String, String> headers = new HashMap<>();
  private byte[] body = new byte[0];

  public HttpResponse(OutputStream outputStream) {
    this.outputStream = outputStream;
    headers.put("Content-Type", "text/html; charset=UTF-8");
  }

  public OutputStream getOutputStream() {
    return outputStream;
  }
  public void setStatus(String statusCode) {
    this.statusCode = statusCode;
  }

  public void setHeader(String key, String value) {
    headers.put(key, value);
  }

  public void writeBody(byte[] bodyByte) {
    this.body = bodyByte;
    setHeader("Content-Length", String.valueOf(body.length));
  }

  public void sendResponse() throws IOException {
    StringBuilder response = new StringBuilder();
    response.append("HTTP/1.1 ").append(statusCode).append("\r\n");

    // header 세팅
    for (Map.Entry<String, String> entry : headers.entrySet()) {
      response.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
    }
    response.append("\r\n");

    // header 전송
    outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
    // body 전송
    outputStream.write(body);
    outputStream.flush();
  }
}
