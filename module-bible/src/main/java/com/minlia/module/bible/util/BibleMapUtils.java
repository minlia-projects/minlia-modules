package com.minlia.module.bible.util;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/6/18 12:12 PM
 */
public class BibleMapUtils {

    public static <T> T mapToBean(Map<String, ?> map, Class<T> beanClass) {
        Map<String, Object> result = toLowerCamel(map);
        return BeanUtil.mapToBean(result, beanClass, false);
    }

    public static Map<String, Object> toLowerCamel(Map<String, ?> map) {
        Map<String, Object> result = Maps.newHashMap();
        map.entrySet().stream().forEach(entry ->{
            String key = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry.getKey());
            result.put(key, entry.getValue());
        });
        return result;
    }

}
