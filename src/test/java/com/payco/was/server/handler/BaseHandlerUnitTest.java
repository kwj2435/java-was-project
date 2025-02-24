package com.payco.was.server.handler;

import com.payco.was.enums.HttpStatus;
import com.payco.was.model.ConfigModel;
import com.payco.was.model.HeaderModel;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseHandlerUnitTest {

  private BaseHandler baseHandler;
  private OutputStream out;
  private HeaderModel.HeaderDto headerDto;
  private ConfigModel.Host host;
  private ConfigModel.ErrorPage errorPage;

  @Before
  public void init() {
    out = new ByteArrayOutputStream();
    headerDto = mock(HeaderModel.HeaderDto.class);
    host = mock(ConfigModel.Host.class);
    errorPage = mock(ConfigModel.ErrorPage.class);

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
    when(headerDto.getPath()).thenReturn("../../../../abc");

    // when
    baseHandler.handleRequest(headerDto, out);
    String response = out.toString();

    // then
    assertTrue(response.contains(HttpStatus.FORBIDDEN.getCode()));
  }

  // 허용되지 않은 확장자 요청 방지
  @Test
  public void test_disallowedExtensions_Forbidden() {
    // given
    when(errorPage.getForbidden403()).thenReturn("forbidden.html");
    when(headerDto.getPath()).thenReturn("/abc");

    // when
    baseHandler.handleRequest(headerDto, out);
    String response = out.toString();

    // then
    assertTrue(response.contains(HttpStatus.FORBIDDEN.getCode()));
  }
}
