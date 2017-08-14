package com.minlia.modules.rbac.domain;

import lombok.Data;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by will on 8/14/17.
 * Global User Identity
 * Get from UserContext
 */
@Data
@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    private String guid;

    @Override
    public String getCurrentAuditor() {
        return getGuid();
    }


}
