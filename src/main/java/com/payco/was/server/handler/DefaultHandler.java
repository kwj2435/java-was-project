package com.payco.was.server.handler;

import com.payco.was.model.HeaderModel.HeaderDto;
import com.payco.was.utils.ConfigUtils;
import java.io.OutputStream;

public class DefaultHandler extends BaseHandler {

  public DefaultHandler() {
    super(ConfigUtils.getHost("default"));
  }

  @Override
  public void handleRequest(HeaderDto headerDto, OutputStream out) {
    super.handleRequest(headerDto, out);
  }
}
