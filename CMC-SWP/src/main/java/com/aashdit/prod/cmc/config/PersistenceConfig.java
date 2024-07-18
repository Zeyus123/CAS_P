package com.aashdit.prod.cmc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import com.aashdit.prod.cmc.model.umt.User;

@Component
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

	@Bean
	AuditorAware<User> auditorProvider() {
		return new AuditorAwareImpl();
	}
}
