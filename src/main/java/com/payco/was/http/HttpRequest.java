package com.payco.was.http;

public class HttpRequest {
  private String method;
  private String host;
  private String path;
  private String parameters;

  public HttpRequest(String method, String path, String parameters) {
    this.method = method;
    this.path = path;
    this.parameters = parameters;
  }

  public String getHost() { return host; }
  public String getMethod() { return method; }
  public String getPath() { return path; }
  public String getParameters() { return parameters; }
}
