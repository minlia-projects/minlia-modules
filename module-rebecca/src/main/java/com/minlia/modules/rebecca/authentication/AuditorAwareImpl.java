package com.minlia.modules.rebecca.authentication;

import com.minlia.modules.rebecca.context.SecurityContextHolder;
import lombok.Data;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by will on 8/14/17.
 * Global User Identity
 * Get from UserContext
 * <p>
 * For auditing only
 */
@Data
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getCurrentGuid());
    }

}
