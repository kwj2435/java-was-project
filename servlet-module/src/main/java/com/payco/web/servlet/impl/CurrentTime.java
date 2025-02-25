package com.payco.web.servlet.impl;

import com.payco.web.http.HttpRequest;
import com.payco.web.http.HttpResponse;
import com.payco.web.servlet.SimpleServlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 과제 7번 현재 시각 출력 Servlet
 */
public class CurrentTime implements SimpleServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
    String formattedDate = now.format(formatter);

    InputStream responsePage =
        getClass().getClassLoader().getResourceAsStream("default/CurrentTime.html");
    if (responsePage == null) {
      httpResponse.setStatus("404");
      httpResponse.writeBody("Error: HTML template not found.".getBytes());
      httpResponse.sendResponse();
      return;
    }

    String htmlContent = new String(responsePage.readAllBytes(), StandardCharsets.UTF_8);

    htmlContent = htmlContent.replace("{CURRENT_TIME}", formattedDate);

    httpResponse.writeBody(htmlContent.getBytes(StandardCharsets.UTF_8));
    httpResponse.sendResponse();
  }
}
