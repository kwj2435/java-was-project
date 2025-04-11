package com.uijin.was.server.handler;

import com.uijin.was.enums.HttpStatus;
import com.uijin.web.http.HttpRequest;
import com.uijin.web.http.HttpResponse;
import com.uijin.was.model.ConfigModel;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseHandlerUnitTest {

  private BaseHandler baseHandler;
  private OutputStream out = new ByteArrayOutputStream();;
  private ConfigModel.Host host;
  private ConfigModel.ErrorPage errorPage;
  private HttpRequest httpRequest;
  private HttpResponse httpResponse;

  @Before
  public void init() {
    httpRequest = mock(HttpRequest.class);
    httpResponse = mock(HttpResponse.class);
    host = mock(ConfigModel.Host.class);
    errorPage = mock(ConfigModel.ErrorPage.class);

    when(httpResponse.getOutputStream()).thenReturn(out);
    when(host.getHost()).thenReturn("localhost");
    when(host.getHttpRoot()).thenReturn("/default");
    when(host.getErrorPage()).thenReturn(errorPage);

    baseHandler = new DefaultHandler(host);
  }

  // 경로 탐색 공격 방지
  @Test
  public void test_DirectoryTraversal_Forbidden() {
    // given
    when(errorPage.getForbidden403()).thenReturn("forbidden.html");
    when(httpRequest.getPath()).thenReturn("../../../../abc");

    // when
    baseHandler.handleRequest(httpRequest, httpResponse);
    String response = out.toString();

    // then
    assertTrue(response.contains(HttpStatus.FORBIDDEN.getCode()));
  }

  // 허용되지 않은 확장자 요청 방지
  @Test
  public void test_disallowedExtensions_Forbidden() {
    // given
    when(errorPage.getForbidden403()).thenReturn("forbidden.html");
    when(httpRequest.getPath()).thenReturn("/abc.exe");

    // when
    baseHandler.handleRequest(httpRequest, httpResponse);
    String response = out.toString();

    // then
    assertTrue(response.contains(HttpStatus.FORBIDDEN.getCode()));
  }

  // Handler 500 에러 테스트
  @Test
  public void test_500ErrorPage_withIOException() {
    // given
    when(errorPage.getInternalServerError500()).thenReturn("default_500_error.html");
    when(httpRequest.getPath()).thenReturn("/error");
    when(host.getHost()).thenReturn(null);

    // when
    baseHandler.handleRequest(httpRequest, httpResponse);
    String response = out.toString();

    // then
    assertTrue(response.contains(HttpStatus.INTERNAL_SERVER_ERROR.getCode()));
  }
}
