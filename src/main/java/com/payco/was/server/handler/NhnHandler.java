package com.payco.was.server.handler;

import com.payco.was.model.ConfigModel;
import com.payco.was.model.HeaderModel.HeaderDto;

import java.io.OutputStream;

public class NhnHandler extends BaseHandler{

  public NhnHandler(ConfigModel.Host host) {
    super(host);
  }

  @Override
  public void handleRequest(HeaderDto headerDto, OutputStream out) {
    super.handleRequest(headerDto, out);
  }
}
