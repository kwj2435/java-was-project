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
 * config.json 설정 정보 Utils
 */
public class ConfigUtils {

  private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
  private static final Map<String, Host> hostMap = new HashMap<>();
  private static int port;
  private static List<String> disallowedExtensions;

  static {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ConfigDto configDto = objectMapper.readValue(
          new File("src/main/resources/config.json"),
          ConfigDto.class
      );
      port = configDto.getPort();
      disallowedExtensions = configDto.getDisallowedExtensions();
      for (Host host : configDto.getHosts()) {
        hostMap.put(host.getHostName(), host); // host 이름을 키로 저장
      }

    } catch (IOException e) {
      logger.error("Error during server config setup", e);
    }
  }

  public static int getPort() {
    return port;
  }

  public static Map<String, Host> getHostMap() {
    return hostMap;
  }
  public static Host getHost(String hostName) {
    return hostMap.get(hostName);
  }

  public static List<String> getDisallowedExtensions() {
    return disallowedExtensions;
  }
}
