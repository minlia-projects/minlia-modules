package com.minlia.modules.http;

import com.google.common.collect.Lists;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
            ApiPreconditions.is(true, ApiCode.BASED_ON,e.getMessage());
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
    public static String getUrl(Object bean) throws Exception {
        Class<?> clz = bean.getClass();
  
        StringBuffer result = new StringBuffer(512);  
        Field[] field = clz.getDeclaredFields();  
  
        result.append("?");
        for (Field f : field) {  
            Object o = getMethodValue(clz, bean, f.getName());
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