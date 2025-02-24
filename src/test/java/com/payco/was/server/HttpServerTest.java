package com.payco.was.server;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class HttpServerTest {
  private static HttpServer httpServer;
  private static int port = 9001;

  @BeforeClass
  public static void setUp() throws Exception {
    httpServer = new HttpServer(port);
    new Thread(() -> {
      try {
        httpServer.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();

    // HttpServer 실행 위해 0.1초 대기
    Thread.sleep(200);
  }

  // Default 400 Error Page 정상 노출 테스트
  @Test
  public void testDefault404ErrorResponse() throws Exception {
    testErrorResponse("/abc", "default/default_404_error.html", "localhost");
  }

  // Default 403 Error Page 정상 노출 테스트
  @Test
  public void testDefault403ErrorResponse() throws Exception {
    testErrorResponse("../../../../abc", "default/default_403_error.html", "localhost");
  }

  // Payco 400 Error Page 정상 노출 테스트
  @Test
  public void testPayco404ErrorResponse() throws Exception {
    testErrorResponse("/abc", "payco/payco_404_error.html", "payco.com");
  }

  // Payco 403 Error Page 정상 노출 테스트
  @Test
  public void testPayco403ErrorResponse() throws Exception {
    testErrorResponse("../../../../abc", "payco/payco_403_error.html", "payco.com");
  }

  // NHN 400 Error Page 정상 노출 테스트
  @Test
  public void testNhn404ErrorResponse() throws Exception {
    testErrorResponse("/abc", "nhn/nhn_404_error.html", "nhn.com");
  }

  // NHN 403 Error Page 정상 노출 테스트
  @Test
  public void testNhn403ErrorResponse() throws Exception {
    testErrorResponse("../../../../abc", "nhn/nhn_403_error.html", "nhn.com");
  }

//  // Hello Servlet 정상 매핑 테스트
//  @Test
//  public void testHelloServletResponse() throws Exception {
//    testErrorResponse("/hello", "nhn/nhn_403_error.html", "localhost");
//  }
//
//  // service.Hello Servlet 정상 매핑 확인
//  @Test
//  public void testServiceHelloServletResponse() throws Exception {
//    testErrorResponse("/service.Hello", "nhn/nhn_403_error.html", "localhost");
//  }
//
//  // Current Time Servlet 정상 매핑 확인
//  @Test
//  public void testCurrentTimeServletResponse() throws Exception {
//    testErrorResponse("/time", "nhn/nhn_403_error.html", "localhost");
//  }


  // host 별 error 페이지 테스트 공통 메서드
  private void testErrorResponse(String requestPath, String expectedFilePath, String host) throws Exception {
    // given
    String expectedResponse = loadFileContent(expectedFilePath);

    // when
    String response = removeResponseHeader(sendHttpRequest(host, requestPath));

    // 응답 및 예상 응답에서 공백을 제거
    expectedResponse = expectedResponse.replaceAll("\\s+", "");
    response = response.replaceAll("\\s+", "");

    // then
    assertEquals(expectedResponse, response);
  }

  private String removeResponseHeader(String response) {
    int bodyStartIndex = response.indexOf("\n\n");

    if (bodyStartIndex != -1) {
      return response.substring(bodyStartIndex + 2); // 헤더를 제외한 부분 반환
    }

    return response;
  }

  private String sendHttpRequest(String host, String requestPath) throws IOException {
    try (Socket socket = new Socket("localhost", port);
         OutputStream os = socket.getOutputStream();
         InputStream is = socket.getInputStream()) {

      String request = "GET " + requestPath + " HTTP/1.1\r\n" +
              "Host: " + host + "\r\n\r\n";

      os.write(request.getBytes());
      os.flush();

      // 응답 읽기
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder response = new StringBuilder();
      for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        response.append(line).append("\n");
      }

      return response.toString();
    }
  }

  private String loadFileContent(String filePath) throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
      if (inputStream == null) {
        throw new FileNotFoundException("Resource not found: " + filePath);
      }
      return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
  }
}
