package com.enotes.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.enotes.utils.CommonUtil;

public class AuditAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() { 
		return Optional.of(CommonUtil.getLoggedInUser().getId());
	}

}
