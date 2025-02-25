package com.payco.was.model;

public class HeaderModel {

  public static class HeaderDto {
    private String host;
    private String path;
    private String method;

    public HeaderDto(String host, String path, String method) {
      this.host = host;
      this.path = path;
      this.method = method;
    }

    public String getHost() { return host; }
    public String getPath() { return path; }
    public String getMethod() { return method; }
  }
}
