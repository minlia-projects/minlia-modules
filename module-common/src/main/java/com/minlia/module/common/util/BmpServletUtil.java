package com.minlia.module.common.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/11/22 3:41 PM
 */
public class BmpServletUtil extends ServletUtil {

    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        String[] headers = new String[]{"X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Forwarded-For"};
        if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
            headers = ArrayUtil.addAll(new String[][]{headers, otherHeaderNames});
        }

        String[] arr$ = headers;
        int len$ = headers.length;

        String ip;
        for(int i$ = 0; i$ < len$; ++i$) {
            String header = arr$[i$];
            ip = request.getHeader(header);
            if (!isUnknow(ip)) {
                return getMultistageReverseProxyIp(ip);
            }
        }

        ip = request.getRemoteAddr();
        return getMultistageReverseProxyIp(ip);
    }


    private static String getMultistageReverseProxyIp(String ip) {
        if (ip != null && ip.indexOf(",") > 0) {
            String[] ips = ip.trim().split(",");
            String[] arr$ = ips;
            int len$ = ips.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String subIp = arr$[i$];
                if (!isUnknow(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }

        return ip;
    }

    private static boolean isUnknow(String checkString) {
        return StrUtil.isBlank(checkString) || "unknown".equalsIgnoreCase(checkString);
    }

}
