package com.minlia.modules.http;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.utils.ApiAssert;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Get 请求参数处理类
 */
public class GetParamter {

    private static final String url_splice_question = "?";

    private static final String url_splice_equal = "=";

    private static final String url_splice_and = "&";

    /**
     * 使用 NameValuePair 来拼接URI
     * @param url
     * @param bean
     * @return
     * @throws IOException
     */
    public static String getUrl(String url,Object bean) {
        List<NameValuePair> params = Lists.newArrayList();
        String str = "";
        try {
            Field[] field = bean.getClass().getDeclaredFields();
            for (Field f : field) {
                Object o = getMethodValue(bean.getClass(), bean, f.getName());
                if (o != null && !"".equals(o.toString())) {
                    params.add(new BasicNameValuePair(f.getName(), o.toString()));
                }
            }
            //转换为键值对
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            ApiAssert.state(false, e.getMessage());
        }
        return url + "?" + str;
    }

    /**
     * 通过反射拼接对象参数工具
     * 不进行编码
     * @param bean
     * @return
     * @throws Exception
     */
    public static String getUrl(Object bean) {
        Class<?> clz = bean.getClass();
  
        StringBuffer result = new StringBuffer(512);  
        Field[] field = clz.getDeclaredFields();  
  
        result.append("?");
        for (Field f : field) {
            Object o = null;
            try {
                o = getMethodValue(clz, bean, f.getName());
            } catch (Exception e) {
                e.printStackTrace();
                ApiAssert.state(false, e.getMessage());
            }
            if (o != null && !"".equals(o.toString())) {  
                result.append(o + "&");  
            }  
        }
        result.delete(result.length() - 1, result.length());  
        return result.toString();  
    }

    public static String getUrl(String url, Map<String,Object> map) {
        StringBuffer result = new StringBuffer(512);
        result.append(url).append(url_splice_question);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (null != entry.getValue()) {
                result.append(entry.getKey()).append(url_splice_equal).append(entry.getValue()).append(url_splice_and);
            }
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    public static String getUrl1(String url,Object bean) {
        try {
//            Map map = BeanUtils.describe(ro);
            url = getUrl(url,beanToMap(bean));
        } catch (Exception e) {
            e.printStackTrace();
            ApiAssert.state(false, e.getMessage());
        }
        return url;
    }

    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (null != beanMap.get(key)) {
                    map.put(key + "", beanMap.get(key));
                }
            }
        }
        return map;
    }



    /** 
     * 根据字段获取值 
     *  
     * @param clz 
     * @param o 
     * @param field 
     * @return 
     */  
    @SuppressWarnings("rawtypes")  
    public static Object getMethodValue(Class clz, Object o, String field)  
            throws NoSuchMethodException, SecurityException,  
            IllegalAccessException, IllegalArgumentException,  
            InvocationTargetException {  
  
        Character charss = field.charAt(0);  
        field = field.replaceFirst(charss.toString(),  
                Character.toUpperCase(charss) + "");  
  
        // 获取值  
        @SuppressWarnings("unchecked")  
        Method getMethod = clz.getDeclaredMethod("get" + field, new Class[] {});  
  
        Object value = getMethod.invoke(o, new Object[] {});  
  
        return value;  
    }  
  

}  