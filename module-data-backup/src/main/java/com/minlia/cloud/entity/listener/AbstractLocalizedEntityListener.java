package com.minlia.cloud.entity.listener;

import com.minlia.cloud.entity.AbstractLocalizedEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.PrePersist;
import java.util.Locale;

@Slf4j
public class AbstractLocalizedEntityListener {
//    public AbstractLocalizedEntityListener() {
//    }
//
//    @PrePersist
//    public void AbstractEntityPrePersist(AbstractLocalizedEntity entity){
//        if(StringUtils.isEmpty(entity.getLocale())){
//            Locale i18n= LocaleContextHolder.getLocale();
//            entity.setLocale(i18n.toString());
//            log.debug("AbstractEntityPrePersist with data i18n created {}",entity.getLocale());
//        }
//    }
}
