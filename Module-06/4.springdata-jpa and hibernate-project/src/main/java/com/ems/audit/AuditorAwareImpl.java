package com.ems.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Exercise 7: AuditorAware implementation.
 * In a real app this would pull the authenticated user from SecurityContextHolder.
 * Here we return a static system user for demonstration.
 */
@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // In production: SecurityContextHolder.getContext().getAuthentication().getName()
        return Optional.of("system");
    }
}
