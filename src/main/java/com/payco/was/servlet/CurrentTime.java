package com.payco.was.servlet;

import com.payco.was.http.HttpRequest;
import com.payco.was.http.HttpResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 과제 7번 현재 시각 출력 Servlet
 */
public class CurrentTime implements SimpleServlet{

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
    String formattedDate = now.format(formatter);

    httpResponse.writeBody("<html><h1>" + formattedDate +"</h1></html>");
    httpResponse.sendResponse();
  }
}
