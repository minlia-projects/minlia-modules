//package com.minlia.bmp.confiig;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.google.common.base.CaseFormat;
//import com.google.common.collect.Maps;
//import com.minlia.cloud.holder.ContextHolder;
//import com.minlia.module.bible.annotation.BibleAutowired;
//import com.minlia.module.bible.service.BibleItemService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Map;
//
//@Slf4j
//@Component
//public class BibleSeedDataInitializeListener implements ApplicationListener<ContextRefreshedEvent> {
//
//    private boolean alreadySetup = false;
//
//    @Autowired
//    private BibleItemService bibleItemService;
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(final ContextRefreshedEvent event) {
//        log.debug("Starting Initialize Bible Seed Data ");
//        if (alreadySetup) {
//            return;
//        }
//
//        //获取所有带有 BibleAutowired 注解的类
//        Map<String, Object> beansWithAnnotationMap = ContextHolder.getContext().getBeansWithAnnotation(BibleAutowired.class);
//        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
//
//            String bibleCode;
//
//            //获取类注解
//            BibleAutowired bibleAutowired = AnnotationUtils.findAnnotation(entry.getClass(), BibleAutowired.class);
//            if (null != bibleAutowired.type()) {
//                bibleCode = bibleAutowired.type();
//            } else {
//                bibleCode = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entry.getKey());
//            }
//
//            Map<String, String> map = bibleItemService.queryValueMap(bibleCode);
//            entry.setValue(mapToBean(map, entry.getValue().getClass()));
//        }
//
//        alreadySetup = true;
//    }
//
//    public static <T> T mapToBean(Map<String, ?> map, Class<T> beanClass) {
//        Map<String, Object> result = toLowerCamel(map);
//        return BeanUtil.mapToBean(result, beanClass, false);
//    }
//
//    public static Map<String, Object> toLowerCamel(Map<String, ?> map) {
//        Map<String, Object> result = Maps.newHashMap();
//        map.entrySet().stream().forEach(entry -> {
//            String key = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry.getKey());
//            result.put(key, entry.getValue());
//        });
//        return result;
//    }
//
//
//}