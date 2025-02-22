package com.payco.was.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payco.was.model.ConfigModel.ConfigDto;
import com.payco.was.model.ConfigModel.Host;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 서버 설정 유틸
 */
public class ConfigUtils {

  private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
  private static Map<String, Host> hostMap = new HashMap<>();
  private static int port;

  static {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ConfigDto configDto = objectMapper.readValue(
          new File("src/main/resources/config.json"),
          ConfigDto.class
      );
      port = configDto.getPort();
      for (Host host : configDto.getHosts()) {
        hostMap.put(host.getHostName(), host); // host 이름을 키로 저장
      }

    } catch (IOException e) {
      logger.error("Server Config 설정 오류");
      e.printStackTrace();
    }
  }

  public static int getPort() {
    return port;
  }

  public static Host getHost(String hostName) {
    return hostMap.get(hostName);
  }
}
