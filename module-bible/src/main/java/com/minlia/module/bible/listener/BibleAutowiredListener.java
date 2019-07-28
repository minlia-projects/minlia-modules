package com.minlia.module.bible.listener;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.minlia.module.bible.annotation.BibleAutowired;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.bible.util.BibleMapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cqqianyi on 2017/8/14.
 */
@Component
//@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class BibleAutowiredListener implements BeanPostProcessor {

    @Autowired
    private BibleItemService bibleItemService;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        BibleAutowired bibleAutowired = AnnotationUtils.findAnnotation(bean.getClass(), BibleAutowired.class);
        if (null != bibleAutowired) {
            String bibleCode = StringUtils.isNotBlank(bibleAutowired.type()) ? bibleAutowired.type() : CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, bean.getClass().getSimpleName());
            Map<String, String> map = bibleItemService.queryValueMap(bibleCode);
            bean = BibleMapUtils.mapToBean(map, bean.getClass());
        }
        return bean;
    }

}
