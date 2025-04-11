package com.uijin.was.enums;

public enum HttpStatus {
  OK("HTTP/1.1 200 OK"),
  FORBIDDEN("HTTP/1.1 403 Forbidden"),
  NOT_FOUND("HTTP/1.1 404 Not Found"),
  INTERNAL_SERVER_ERROR("HTTP/1.1 500 Internal Server Error");

  private final String code;

  HttpStatus(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
