package com.minlia.modules.rbac.authentication;

import com.minlia.modules.rbac.context.SecurityContextHolder;
import java.util.Optional;
import lombok.Data;
import org.springframework.data.domain.AuditorAware;
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

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getCurrentUser().getGuid());
    }

}
