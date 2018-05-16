//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.module.unified.payment.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestIpUtils {
  public RequestIpUtils() {
  }

  public static String getIpAddr() {
    ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    if(sra != null) {
      HttpServletRequest req = sra.getRequest();
      return getIpAddr(req);
    } else {
      return getIpAddr((HttpServletRequest)null);
    }
  }

  public static String getIpAddr(HttpServletRequest request) {
    if(request == null) {
      return "127.0.0.1";
    } else {
      String ip = request.getHeader("x-forwarded-for");
      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }

      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }

      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }

      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("http_client_ip");
      }

      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }

      if(ip != null && ip.indexOf(",") != -1) {
        ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
      }

      if("0:0:0:0:0:0:0:1".equals(ip)) {
        ip = "127.0.0.1";
      }

      return ip;
    }
  }
}
