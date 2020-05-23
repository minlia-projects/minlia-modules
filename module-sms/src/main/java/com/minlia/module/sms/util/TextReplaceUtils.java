package com.minlia.module.sms.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/6/4 5:03 PM
 */
public class TextReplaceUtils {

    public static String replace(String content, Map<String, ?> variables) {
        if (StringUtils.isNotBlank(content) && MapUtils.isNotEmpty(variables)) {
            for (Map.Entry<String, ?> entry : variables.entrySet()) {
                content = content.replace("${" + entry.getKey() + "}", entry.getValue().toString());
            }
        }
        return content;
    }

}
