package com.minlia.module.common.util;

import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestIpUtils {

    public final static String LOCALHOST_IP_V6 = "0:0:0:0:0:0:0:1";

    public final static String LOCALHOST_IP_V4 = "127.0.0.1";

    public static String getClientIP() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            HttpServletRequest req = sra.getRequest();
            return getClientIP(req);
        } else {
            return getClientIP(null);
        }
    }

    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        if (request == null) {
            return LOCALHOST_IP_V4;
        } else {
            String ip = ServletUtil.getClientIP(request, otherHeaderNames);
            return ip.equals(LOCALHOST_IP_V6) ? LOCALHOST_IP_V4 : ip;
        }
    }

//    public static String getIpAddr(HttpServletRequest request) {
//        if (request == null) {
//            return "127.0.0.1";
//        } else {
//            String ip = request.getHeader("x-forwarded-for");
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("Proxy-Client-IP");
//            }
//
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("WL-Proxy-Client-IP");
//            }
//
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getRemoteAddr();
//            }
//
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("http_client_ip");
//            }
//
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//            }
//
//            if (ip != null && ip.indexOf(",") != -1) {
//                ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
//            }
//
//            if ("0:0:0:0:0:0:0:1".equals(ip)) {
//                ip = "127.0.0.1";
//            }
//
//            return ip;
//        }
//    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws IOException
     */
//    public final static String getIpAddress(HttpServletRequest request) {
//        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
//
//        String ip = request.getHeader("X-Forwarded-For");
//        if (logger.isInfoEnabled()) {
//            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
//        }
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("Proxy-Client-IP");
//                if (logger.isInfoEnabled()) {
//                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
//                }
//            }
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("WL-Proxy-Client-IP");
//                if (logger.isInfoEnabled()) {
//                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
//                }
//            }
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("HTTP_CLIENT_IP");
//                if (logger.isInfoEnabled()) {
//                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
//                }
//            }
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//                if (logger.isInfoEnabled()) {
//                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
//                }
//            }
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getRemoteAddr();
//                if (logger.isInfoEnabled()) {
//                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
//                }
//            }
//        } else if (ip.length() > 15) {
//            String[] ips = ip.split(",");
//            for (int index = 0; index < ips.length; index++) {
//                String strIp = (String) ips[index];
//                if (!("unknown".equalsIgnoreCase(strIp))) {
//                    ip = strIp;
//                    break;
//                }
//            }
//        }
//        return ip;
//    }

}
