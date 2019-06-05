//package com.minlia.module.data.interceptor;
//
//import com.minlia.module.data.annotation.CryptField;
//import com.minlia.module.data.crypt.CryptContext;
//import com.minlia.module.data.crypt.CryptLoader;
//import com.minlia.module.data.discoverer.MyBatisParameterNameDiscoverer;
//import org.apache.ibatis.binding.MapperMethod;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.apache.ibatis.session.defaults.DefaultSqlSession;
//import org.springframework.core.ParameterNameDiscoverer;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 基于Mybatis拦截器的脱敏实现
// *
// * @author wangzhuhua
// * @date 2018/09/04 下午7:10
// **/
//@Component
//@Intercepts(value = {
//        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
//public class CryptInterceptor implements Interceptor {
//
//    /**
//     * 参数注解缓存
//     */
//    private static final ConcurrentHashMap<String, Map<String, CryptField>> PARAM_ANNOTATIONS_MAP = new ConcurrentHashMap<>();
//
//    /**
//     * 返回值注解缓存
//     */
//    private static final ConcurrentHashMap<String, CryptField> RETURN_ANNOTATIONS_MAP = new ConcurrentHashMap<>();
//
//    /**
//     * 参数名解析器
//     */
//    private final ParameterNameDiscoverer parameterNameDiscoverer = new MyBatisParameterNameDiscoverer();
//
//    public CryptInterceptor() {
//        (new CryptLoader()).loadCrypt();
//    }
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();   // 获取参数集合
//        Object parameter = args[1];             // 入参
//        MappedStatement statement = (MappedStatement) args[0];
//        // 判断是否需要解析
//        if (!isNotCrypt(parameter)) {
//            Map<String, CryptField> cryptFieldMap = getParameterAnnotations(statement);
//            // 单参数 string
//            if (parameter instanceof String && !cryptFieldMap.isEmpty()) {
//                args[1] = stringEncrypt(cryptFieldMap.keySet().iterator().next(), (String) parameter,
//                        getParameterAnnotations(statement));
//                // 单参数 list
//            } else if (parameter instanceof DefaultSqlSession.StrictMap) {
//                DefaultSqlSession.StrictMap<Object> strictMap = (DefaultSqlSession.StrictMap<Object>) parameter;
//                for (Map.Entry<String, Object> entry : strictMap.entrySet()) {
//                    if (entry.getKey().contains("collection")) {
//                        continue;
//                    }
//                    if (entry.getKey().contains("list")) {
//                        listEncrypt((List) entry.getValue(), cryptFieldMap.get(entry.getKey()));
//                    }
//                }
//                // 多参数
//            } else if (parameter instanceof MapperMethod.ParamMap) {
//                MapperMethod.ParamMap<Object> paramMap = (MapperMethod.ParamMap<Object>) parameter;
//                // 解析每一个参数
//                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
//                    // 判断不需要解析的类型 不解析map
//                    if (isNotCrypt(entry.getValue()) || entry.getValue() instanceof Map
//                            || entry.getKey().contains("param")) {
//                        continue;
//                    }
//                    // 如果string
//                    if (entry.getValue() instanceof String) {
//                        entry.setValue(stringEncrypt(entry.getKey(), (String) entry.getValue(), cryptFieldMap));
//                        continue;
//                    }
//                    // 如果 list
//                    if (entry.getValue() instanceof List) {
//                        listEncrypt((List) entry.getValue(), cryptFieldMap.get(entry.getKey()));
//                        continue;
//                    }
//                    beanEncrypt(entry.getValue());
//                }
//                // bean
//            } else {
//                beanEncrypt(parameter);
//            }
//        }
//
//        // 获得出参
//        Object returnValue = invocation.proceed();
//
//        // 出参解密
//        if (isNotCrypt(returnValue)) {
//            return returnValue;
//        }
//
//        // 获得方法注解(针对返回值)
//        CryptField cryptField = getMethodAnnotations(statement);
//        if (returnValue instanceof String) {
//            return stringDecrypt((String) returnValue, cryptField);
//        }
//        if (returnValue instanceof List) {
//            listDecrypt((List) returnValue, cryptField);
//            return returnValue;
//        }
//
//        return returnValue;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//    /**
//     * 获取 方法上的注解
//     *
//     * @param statement MappedStatement
//     * @return 方法上的加密注解 {@link CryptField}
//     * @throws ClassNotFoundException
//     */
//    private CryptField getMethodAnnotations(MappedStatement statement) throws ClassNotFoundException {
//        String id = statement.getId();
//
//        CryptField cryptField = RETURN_ANNOTATIONS_MAP.get(id);
//        if (cryptField != null) {
//            return cryptField;
//        }
//
//        // 获取执行方法
//        Method method = null;
//        final Class clazz = Class.forName(id.substring(0, id.lastIndexOf(".")));
//        for (Method _method : clazz.getDeclaredMethods()) {
//            if (_method.getName().equals(id.substring(id.lastIndexOf(".") + 1))) {
//                method = _method;
//                break;
//            }
//        }
//        if (method == null) {
//            return null;
//        }
//
//        return method.getAnnotation(CryptField.class);
//    }
//
//    /**
//     * 获取 方法参数上的注解
//     *
//     * @param statement MappedStatement
//     * @return 参数名与其对应加密注解
//     * @throws ClassNotFoundException
//     */
//    private Map<String, CryptField> getParameterAnnotations(MappedStatement statement) throws ClassNotFoundException {
//        // 执行ID
//        final String id = statement.getId();
//
//        Map<String, CryptField> cryptFieldMap = PARAM_ANNOTATIONS_MAP.get(id);
//        if (cryptFieldMap != null) {
//            return cryptFieldMap;
//        } else {
//            cryptFieldMap = new HashMap<>();
//        }
//
//        // 获取执行方法
//        Method method = null;
//        final Class clazz = Class.forName(id.substring(0, id.lastIndexOf(".")));
//        for (Method _method : clazz.getDeclaredMethods()) {
//            if (_method.getName().equals(id.substring(id.lastIndexOf(".") + 1))) {
//                method = _method;
//                break;
//            }
//        }
//        if (method == null) {
//            return cryptFieldMap;
//        }
//
//        // 获取参数名称
//        String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
//        // 获取方法参数注解列表
//        final Annotation[][] paramAnnotations = method.getParameterAnnotations();
//        // 填充参数注解
//        for (int i = 0; i < paramAnnotations.length; i++) {
//            Annotation[] paramAnnotation = paramAnnotations[i];
//            for (Annotation annotation : paramAnnotation) {
//                if (annotation instanceof CryptField) {
//                    cryptFieldMap.put(paramNames[i], (CryptField) annotation);
//                    break;
//                }
//            }
//        }
//
//        // 存入缓存
//        PARAM_ANNOTATIONS_MAP.put(id, cryptFieldMap);
//
//        return cryptFieldMap;
//    }
//
//    /**
//     * 判断是否需要加解密
//     *
//     * @param obj 待加密对象
//     * @return 是否需要加密
//     */
//    private boolean isNotCrypt(Object obj) {
//        return obj == null || obj instanceof Double || obj instanceof Integer || obj instanceof Long
//                || obj instanceof Boolean;
//    }
//
//    /**
//     * String 加密
//     *
//     * @param name             参数名称
//     * @param plain            参数明文
//     * @param paramAnnotations 加密注解
//     * @return 密文
//     */
//    private String stringEncrypt(String name, String plain, Map<String, CryptField> paramAnnotations) {
//        return stringEncrypt(plain, paramAnnotations.get(name));
//    }
//
//    /**
//     * String 加密
//     *
//     * @param plain      参数明文
//     * @param cryptField 加密注解
//     * @return 密文
//     */
//    private String stringEncrypt(String plain, CryptField cryptField) {
//        if (StringUtils.isEmpty(plain) || cryptField == null) {
//            return plain;
//        }
//
//        return CryptContext.getCrypt(cryptField.value()).encrypt(plain);
//    }
//
//    /**
//     * String 解密
//     *
//     * @param cipher     参数密文
//     * @param cryptField 加密注解
//     * @return 明文
//     */
//    private String stringDecrypt(String cipher, CryptField cryptField) {
//        if (StringUtils.isEmpty(cipher) || cryptField == null) {
//            return cipher;
//        }
//
//        return CryptContext.getCrypt(cryptField.value()).decrypt(cipher);
//    }
//
//    /**
//     * list 加密
//     *
//     * @param plainList  明文列表
//     * @param cryptField 加密方式注解
//     * @return 密文列表
//     * @throws IllegalAccessException
//     */
//    private List listEncrypt(List plainList, CryptField cryptField) throws IllegalAccessException {
//        for (int i = 0; i < plainList.size(); i++) {
//            Object plain = plainList.get(i);
//            // 判断不需要解析的类型
//            if (isNotCrypt(plain) || plain instanceof Map) {
//                break;
//            }
//            if (plain instanceof String) {
//                plainList.set(i, stringEncrypt((String) plain, cryptField));
//                continue;
//            }
//            beanEncrypt(plain);
//        }
//
//        return plainList;
//    }
//
//    /**
//     * list 解密
//     *
//     * @param cipherList 密文列表
//     * @param cryptField 加密方式注解
//     * @return 明文列表
//     * @throws IllegalAccessException
//     */
//    private List listDecrypt(List cipherList, CryptField cryptField) throws IllegalAccessException {
//        for (int i = 0; i < cipherList.size(); i++) {
//            Object cipher = cipherList.get(i);
//            // 判断不需要解析的类型
//            if (isNotCrypt(cipher) || cipher instanceof Map) {
//                break;
//            }
//            if (cipher instanceof String) {
//                cipherList.set(i, stringDecrypt((String) cipher, cryptField));
//                continue;
//            }
//            beanDecrypt(cipher);
//        }
//
//        return cipherList;
//    }
//
//    /**
//     * bean 加密
//     *
//     * @param plainObject 明文对象
//     * @throws IllegalAccessException
//     */
//    private void beanEncrypt(Object plainObject) throws IllegalAccessException {
//        Class objClazz = plainObject.getClass();
//        Field[] objFields = objClazz.getDeclaredFields();
//        for (Field field : objFields) {
//            CryptField cryptField = field.getAnnotation(CryptField.class);
//            if (cryptField != null) {
//                field.setAccessible(true);
//                Object plain = field.get(plainObject);
//                if (plain == null) {
//                    continue;
//                }
//                if (field.getType().equals(String.class)) {
//                    field.set(plainObject, stringEncrypt((String) plain, cryptField));
//                    continue;
//                }
//                if (field.getType().equals(List.class)) {
//                    field.set(plainObject, listEncrypt((List) plain, cryptField));
//                    continue;
//                }
//                field.setAccessible(false);
//            }
//        }
//    }
//
//    /**
//     * bean 解密
//     *
//     * @param cipherObject 密文对象
//     * @throws IllegalAccessException
//     */
//    private void beanDecrypt(Object cipherObject) throws IllegalAccessException {
//        Class objClazz = cipherObject.getClass();
//        Field[] objFields = objClazz.getDeclaredFields();
//        for (Field field : objFields) {
//            CryptField cryptField = field.getAnnotation(CryptField.class);
//            if (cryptField != null) {
//                field.setAccessible(true);
//                Object cipher = field.get(cipherObject);
//                if (cipher == null) {
//                    continue;
//                }
//                if (field.getType().equals(String.class)) {
//                    field.set(cipherObject, stringDecrypt((String) cipher, cryptField));
//                    continue;
//                }
//                if (field.getType().equals(List.class)) {
//                    field.set(cipherObject, listDecrypt((List) cipher, cryptField));
//                    continue;
//                }
//            }
//        }
//    }
//
//}