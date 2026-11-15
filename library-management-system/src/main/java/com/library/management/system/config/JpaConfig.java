package com.library.management.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for JPA auditing
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
    // Auto-enable @CreatedDate and @LastModifiedDate
}