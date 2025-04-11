package com.uijin.was.server.processor;

import com.uijin.was.server.handler.DefaultHandler;
import com.uijin.was.server.handler.KakaoHandler;
import com.uijin.was.server.handler.RequestHandler;
import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class RequestProcessorUnitTest {

  private RequestProcessor requestProcessor;
  private Socket mockSocket;
  private Map<String, RequestHandler> mockVirtualHosts = new HashMap<>();
  private DefaultHandler defaultHandler;
  private ByteArrayOutputStream out = new ByteArrayOutputStream();

  private HttpRequest httpRequest;
  private HttpResponse httpResponse;

  @Before
  public void init() {
    RequestHandler nhnHandler = mock(KakaoHandler.class);
    defaultHandler = mock(DefaultHandler.class);

    mockVirtualHosts.put("test.com", nhnHandler);
    mockSocket = mock(Socket.class);
    httpRequest = mock(HttpRequest.class);
    httpResponse = mock(HttpResponse.class);
    when(httpResponse.getOutputStream()).thenReturn(out);

    requestProcessor = new RequestProcessor(
            httpRequest, httpResponse, mockVirtualHosts, defaultHandler);
  }

  // Host Header 존재시 지정된 Handler 호출
  @Test
  public void testVirtualHostWithHost() throws IOException {
    // given
    String mockHost = "test.com";
    when(httpRequest.getHost()).thenReturn(mockHost);
    when(httpRequest.getPath()).thenReturn("/test-path");
    when(mockSocket.getOutputStream()).thenReturn(out);

    // when
    requestProcessor.run();

    // then
    verify(mockVirtualHosts.get(mockHost)).handleRequest(httpRequest, httpResponse);
  }

  // Host Header 미존재시 기본 Handler 호출
  @Test
  public void testVirtualHostWithNotHst() throws IOException {
    // given
    String mockHost = "localhost.com";
    when(httpRequest.getHost()).thenReturn(mockHost);
    when(httpRequest.getPath()).thenReturn("/test-path");
    when(mockSocket.getOutputStream()).thenReturn(out);

    // when
    requestProcessor.run();

    // then
    verify(defaultHandler).handleRequest(httpRequest, httpResponse);
  }
}
