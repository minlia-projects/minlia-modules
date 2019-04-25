//package com.minlia.modules.http;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ResponseHandler;
//import org.apache.poi.ss.formula.functions.T;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//
///**
// * 使用Mapper进行写入
// */
//@Slf4j
//public class ObjectResponseHandler<CLASS extends Class<?>, RESPONSE extends HttpResponse> implements ResponseHandler<T> {
//
//    protected final CLASS clazz;
//    protected T clazzObject;
//
//    public ObjectResponseHandler(final CLASS clazz) {
//        this.clazz = clazz;
//    }
//
//    public T handleResponse(final HttpResponse response) throws IOException {
//        final int statusCode = response.getStatusLine().getStatusCode();
//
//        if (statusCode >= 300) {
//            throw new FileNotFoundException("Source not found, response code " + statusCode);
//        }
//
//        final HttpEntity entity = response.getEntity();
//        return writeStreamToObject(entity.getContent());
//    }
//
//
//    protected T writeStreamToObject(final InputStream source) throws IOException {
//        String text = IOUtils.toString(source, StandardCharsets.UTF_8.name());
//        log.debug("TARGET CLASS {}", clazz);
//
////        try {
////            clazz.newInstance();
////        } catch (InstantiationException e) {
////            e.printStackTrace();
////        } catch (IllegalAccessException e) {
////            e.printStackTrace();
////        }
////        ObjectMapper mapper = new ObjectMapper();
////        Object x = mapper.readValue(text, clazz.getClass());
//        //TODO convert Responsed String ro Special Object
//        //JSON, XML, WSDL etc.
//
//
////        JavaType type = mapper.getTypeFactory().constructParametricType(Data.class, clazz);
//
//
//        clazzObject=JSON.parseObject(text,new TypeReference<T>(clazz.getClass()) {});
//        return clazzObject;
//    }
//
//}
