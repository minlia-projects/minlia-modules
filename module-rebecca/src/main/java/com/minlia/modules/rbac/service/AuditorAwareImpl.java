package com.minlia.modules.rbac.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mybatis.support.GuidHolder;
import org.springframework.stereotype.Component;

/**
 * Created by will on 8/14/17.
 * Global User Identity
 * Get from UserContext
 *
 * For auditing only
 */
@Data
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private GuidHolder holder;

    @Override
    public String getCurrentAuditor() {
        return holder.getGuid();
    }


}
