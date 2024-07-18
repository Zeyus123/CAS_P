package com.aashdit.prod.cmc.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

public class AuditorAwareImpl implements AuditorAware<User> {

	@Override
	public Optional<User> getCurrentAuditor() {
		return Optional.of(SecurityHelper.getCurrentUser());
	}

}
