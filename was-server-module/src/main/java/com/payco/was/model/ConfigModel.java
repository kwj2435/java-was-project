package com.payco.was.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Json 서버 설정 파일 Read DTO
 */
public class ConfigModel {

  public static class ConfigDto {

    private int port;
    private List<String> disallowedExtensions;
    private List<Host> hosts = new ArrayList<>();

    // NoArgsConstructor
    public ConfigDto() {}

    // Getter
    public int getPort() { return port; }
    public List<String> getDisallowedExtensions() { return disallowedExtensions; }
    public List<Host> getHosts() { return hosts; }
  }

  public static class Host {

    private String hostName;
    private String handlerName;
    private String host;
    private String httpRoot;
    private ErrorPage errorPage;

    // NoArgsConstructor
    public Host() {}

    // Getter
    public String getHostName() { return hostName; }
    public String getHandlerName() { return handlerName; }
    public String getHost() { return host; }
    public String getHttpRoot() { return httpRoot; }
    public ErrorPage getErrorPage() { return errorPage; }
  }

  public static class ErrorPage {

    private String forbidden403;
    private String notFound404;
    private String internalServerError500;

    //NoArgsConstructor
    public ErrorPage() {}

    // Getter
    public String getForbidden403() { return forbidden403; }
    public String getNotFound404() {return notFound404;}
    public String getInternalServerError500() {return internalServerError500;}
  }
}
