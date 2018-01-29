//package com.minlia.module.data.entity.listener;
//
//import com.minlia.module.data.entity.AbstractEntity;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.persistence.PrePersist;
//
///**
// * Listener - 创建日期、修改日期处理
// */
//@Slf4j
//public class AbstractEntityListener {
//    public AbstractEntityListener() {
//    }
//
//    @PrePersist
//    public void abstractEntityPrePersist(AbstractEntity entity) {
////        if (StringUtils.isEmpty(entity.getAppId())) {
////            //设置appId为当前应用
////            RelaxedPropertyResolver resolver = ContextHolder.getSystemProperty();
////            if (null != resolver) {
////                String appIdFound = ContextHolder.getSystemProperty().getProperty(SystemConstants.APP_ID);
////                if (!StringUtils.isEmpty(appIdFound)) {
//////                    appId = appIdFound;
////                    entity.setAppId(appIdFound);
////                } else {
////                    entity.setAppId("");
//////                    appId = "";
////                }
////            }
////        }
//    }
//
//}
