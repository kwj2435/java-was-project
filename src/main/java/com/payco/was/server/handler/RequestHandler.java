package com.payco.was.server.handler;

import com.payco.was.model.HeaderModel.HeaderDto;
import java.io.OutputStream;

public interface RequestHandler {
  void handleRequest(HeaderDto headerDto, OutputStream outputStream);
}
