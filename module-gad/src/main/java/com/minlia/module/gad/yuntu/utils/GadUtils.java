package com.minlia.module.gad.yuntu.utils;

import com.google.common.collect.Maps;
import com.minlia.cloud.body.ApiRequestBody;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * Created by garen on 2017/12/28.
 */
public class GadUtils {

    public static String singMd5(ApiRequestBody body, String key) {
        Map<String ,Object> map = beanToMap(body);
        map.remove("sig");
        map.remove("key");
        String sing = map.toString().substring(1,map.toString().length()-1).replace(", ","&") + key;
        return DigestUtils.md5Hex(sing);
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newTreeMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    public static <T> MultiValueMap<String, Object> beanToMap(T bean, MultiValueMap map) {
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.add(key+"", beanMap.get(key)+"");
            }
        }
        return map;
    }

}
