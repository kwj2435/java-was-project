package com.payco.was.enums;

public enum HttpStatus {
  OK(200),
  FORBIDDEN(403),
  NOT_FOUND(404),
  INTERNAL_SERVER_ERROR(500);

  private final int code;

  HttpStatus(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
