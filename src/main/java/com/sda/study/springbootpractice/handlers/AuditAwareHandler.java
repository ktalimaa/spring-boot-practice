package com.sda.study.springbootpractice.handlers;

import com.sda.study.springbootpractice.utils.Constants;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Custom handler to implement AuditAware
 *
 */

// Handler is not a component, but it is more like bean
public class AuditAwareHandler implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(Constants.Audit.DEFAULT_AUDITOR);
    }
}
