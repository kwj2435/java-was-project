package com.payco.was.server.handler;

import com.payco.was.model.HeaderModel.HeaderDto;
import com.payco.was.utils.ConfigUtils;
import java.io.OutputStream;

public class NhnHandler extends BaseHandler{

  public NhnHandler() {
    super(configUtils.getHost("nhn"));
  }

  @Override
  public void handleRequest(HeaderDto headerDto, OutputStream out) {
    super.handleRequest(headerDto, out);
  }
}
