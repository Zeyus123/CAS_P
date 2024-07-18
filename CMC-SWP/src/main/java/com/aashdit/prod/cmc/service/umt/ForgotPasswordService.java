package com.aashdit.prod.cmc.service.umt;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;

public interface ForgotPasswordService {

	ServiceOutcome<Boolean> forgotPassword(String username);

}
