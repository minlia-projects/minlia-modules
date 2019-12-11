package com.minlia.module.unified.payment.util;

import com.google.common.base.CaseFormat;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static final Map<String, String> getStringParams(HttpServletRequest request) {

        @SuppressWarnings("unchecked")
        Map<String, String[]> rawParam = request.getParameterMap();

        Map<String, String> underScoreKeyMap = new HashMap<String, String>();

        for (String key : rawParam.keySet()) {
            if (rawParam.get(key) != null) {

                String[] value = rawParam.get(key);
                if (value != null && value.length == 1) {
                    underScoreKeyMap.put(key, value[0]);
                }
            }
        }
        return underScoreKeyMap;
    }

    public static final Map<String, String> convertKeyToCamelCase(Map<String, String> map) {
        Map<String, String> camelCaseKeyMap = new HashMap<String, String>();
        for (String key : map.keySet()) {
            String camelKey = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
            camelCaseKeyMap.put(camelKey, map.get(key));
        }
        return camelCaseKeyMap;
    }

}