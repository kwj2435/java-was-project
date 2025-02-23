package com.payco.was.utils;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import com.payco.was.model.HeaderModel.HeaderDto;
import java.io.OutputStream;

/**
 * Http Request, Response 변환 Utils
 */
public class HttpUtils {
  public static HttpRequest convertToHttpRequest(HeaderDto headerDto) {
    return new HttpRequest(
        headerDto.getMethod(),
        headerDto.getHost(),
        headerDto.getPath()
    );
  }

  public static HttpResponse convertToHttpResponse(OutputStream out) {
    return new HttpResponse(out);
  }
}
