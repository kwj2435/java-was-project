package com.payco.was.server.processor;

import com.payco.was.model.HeaderModel;
import com.payco.was.server.handler.DefaultHandler;
import com.payco.was.server.handler.NhnHandler;
import com.payco.was.server.handler.RequestHandler;
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
  private HeaderModel.HeaderDto mockHeaderDto;
  private DefaultHandler defaultHandler;
  private ByteArrayOutputStream out = new ByteArrayOutputStream();

  @Before
  public void init() {
    RequestHandler nhnHandler = mock(NhnHandler.class);
    defaultHandler = mock(DefaultHandler.class);

    mockVirtualHosts.put("test.com", nhnHandler);
    mockSocket = mock(Socket.class);
    mockHeaderDto = mock(HeaderModel.HeaderDto.class);

    requestProcessor = new RequestProcessor(
            mockSocket, mockVirtualHosts, mockHeaderDto, defaultHandler);
  }

  // Host Header 존재시 지정된 Handler 호출
  @Test
  public void testVirtualHostWithHost() throws IOException {
    // given
    String mockHost = "test.com";
    when(mockHeaderDto.getHost()).thenReturn(mockHost);
    when(mockHeaderDto.getPath()).thenReturn("/test-path");
    when(mockSocket.getOutputStream()).thenReturn(out);

    // when
    requestProcessor.run();

    // then
    verify(mockVirtualHosts.get(mockHost)).handleRequest(mockHeaderDto, out);
  }

  // Host Header 미존재시 기본 Handler 호출
  @Test
  public void testVirtualHostWithNotHst() throws IOException {
    // given
    String mockHost = "localhost.com";
    when(mockHeaderDto.getHost()).thenReturn(mockHost);
    when(mockHeaderDto.getPath()).thenReturn("/test-path");
    when(mockSocket.getOutputStream()).thenReturn(out);

    // when
    requestProcessor.run();

    // then
    verify(defaultHandler).handleRequest(mockHeaderDto, out);
  }
}
