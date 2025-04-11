package com.uijin.web.http;

public class HttpRequest {
  private String method;
  private String host;
  private String path;

  public HttpRequest(String method, String host, String path) {
    this.method = method;
    this.host = host;
    this.path = path;
  }

  public String getHost() { return host; }
  public String getMethod() { return method; }
  public String getPath() { return path; }
}
