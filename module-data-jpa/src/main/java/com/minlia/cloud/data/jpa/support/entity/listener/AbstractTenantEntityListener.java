package com.minlia.cloud.data.jpa.support.entity.listener;

import com.minlia.cloud.data.jpa.support.entity.AbstractTenantEntity;
import com.minlia.cloud.data.jpa.support.tenant.context.TenantContext;
import com.minlia.cloud.data.jpa.support.tenant.context.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.PrePersist;

@Slf4j
public class AbstractTenantEntityListener {
    public AbstractTenantEntityListener() {
    }

    @PrePersist
    public void abstractTenantEntityPrePersist(AbstractTenantEntity entity) {
            if (StringUtils.isEmpty(entity.getTenantId())) {
                TenantContext context = TenantContextHolder.getContext();
                if(null!=context) {
                    if(null!=context.getTenant()) {
                        entity.setTenantId(context.getTenant().getTenantId());
                    }
                }
                log.debug("abstractTenantEntityPrePersist with data tenantId created {}", entity.getTenantId());
            }

    }
}
