package com.payco.was.server.handler;

import com.payco.was.model.ConfigModel.Host;
import com.payco.was.model.HeaderModel.HeaderDto;
import java.io.OutputStream;

public class DefaultHandler extends BaseHandler {

  public DefaultHandler(Host host) {
    super(host);
  }

  @Override
  public void handleRequest(HeaderDto headerDto, OutputStream out) {
    super.handleRequest(headerDto, out);
  }
}
