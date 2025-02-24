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

  private final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
  private final Map<String, Host> hostMap = new HashMap<>();
  private int port;
  private List<String> disallowedExtensions;

  public ConfigUtils() {
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

  public int getPort() {
    return port;
  }

  public Map<String, Host> getHostMap() {
    return hostMap;
  }
  public Host getHost(String hostName) {
    return hostMap.get(hostName);
  }

  public List<String> getDisallowedExtensions() {
    return disallowedExtensions;
  }
}
