package com.minlia.module.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestConfiguration {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private static boolean romePresent = ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", RestTemplate.class.getClassLoader());

    private static final boolean jaxb2Present = ClassUtils.isPresent("javax.xml.bind.Binder", RestTemplate.class.getClassLoader());

    private static final boolean jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", RestTemplate.class.getClassLoader()) && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", RestTemplate.class.getClassLoader());

    private static final boolean jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", RestTemplate.class.getClassLoader());

    private static final boolean gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", RestTemplate.class.getClassLoader());


    @Bean
    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = restTemplateBuilder.setReadTimeout(5000).setConnectTimeout(5000).build();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        requestFactory.setConnectTimeout(10000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        //转化器顺序有变动可能导致 Can not deserialize instance of java.lang.String out of START_ARRAY token
        //Could not read document: Can not deserialize instance of java.lang.String out of START_OBJECT token
        //可以根据mediaType指定每个转化器需要的类型
        //部分转化器默认匹配全部的MediaType  详见restTemplate的doWithRequest（）方法和转化器的 canRead（）/canWrite（）方法
        converters.add(new ByteArrayHttpMessageConverter());
        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为 "ISO-8859-1"）
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        if (romePresent) {
            converters.add(new AtomFeedHttpMessageConverter());
            converters.add(new RssChannelHttpMessageConverter());
        }
        if (jackson2XmlPresent) {
            converters.add(new MappingJackson2XmlHttpMessageConverter());
        }
        else if (jaxb2Present) {
            converters.add(new Jaxb2RootElementHttpMessageConverter());
        }
        if (jackson2Present) {
            converters.add(new MappingJackson2HttpMessageConverter());
        }
        else if (gsonPresent) {
            converters.add(new GsonHttpMessageConverter());
        }
//        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);

//        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
//        while (iterator.hasNext()) {
//            HttpMessageConverter<?> converter = iterator.next();
//            if (converter instanceof StringHttpMessageConverter) {
//                iterator.remove();
//            }
//        }
//        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

//    @Bean
//    @ConditionalOnMissingBean({RestOperations.class, RestTemplate.class})
//    public RestOperations restOperations() {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setReadTimeout(10000);
//        requestFactory.setConnectTimeout(10000);
//
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为 "ISO-8859-1"）
//        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
//        while (iterator.hasNext()) {
//            HttpMessageConverter<?> converter = iterator.next();
//            if (converter instanceof StringHttpMessageConverter) {
//                iterator.remove();
//            }
//        }
//        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//
//        return restTemplate;
//    }

}