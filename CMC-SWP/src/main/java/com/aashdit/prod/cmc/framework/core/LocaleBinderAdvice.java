package com.aashdit.prod.cmc.framework.core;

import java.util.Locale;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LocaleBinderAdvice {

	@ModelAttribute
	private void setLocale(Locale locale, Model model)
	{
		model.addAttribute("lang", locale.toLanguageTag());
	}
}
