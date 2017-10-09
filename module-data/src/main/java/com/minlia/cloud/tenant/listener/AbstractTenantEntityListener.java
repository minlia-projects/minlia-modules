//package com.minlia.cloud.tenant.listener;
//
//import com.minlia.cloud.tenant.context.TenantContext;
//import com.minlia.cloud.tenant.context.TenantContextHolder;
//import com.minlia.cloud.tenant.entity.AbstractTenantEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import javax.persistence.PrePersist;
//
//@Slf4j
//public class AbstractTenantEntityListener {
////    public AbstractTenantEntityListener() {
////    }
////
////    @PrePersist
////    public void abstractTenantEntityPrePersist(AbstractTenantEntity entity) {
////            if (StringUtils.isEmpty(entity.getTenantId())) {
////                TenantContext context = TenantContextHolder.getContext();
////                if(null!=context) {
////                    if(null!=context.getTenant()) {
////                        entity.setTenantId(context.getTenant().getTenantId());
////                    }
////                }
////                log.debug("abstractTenantEntityPrePersist with data tenantId created {}", entity.getTenantId());
////            }
////
////    }
//}
