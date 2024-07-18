package com.aashdit.prod.cmc.framework.core;

import java.lang.reflect.Field;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.i18n.LocaleContextHolder;

import com.aashdit.prod.cmc.framework.core.annotation.Sortable;

public class LocaleSpecificSorter<T> {

	private Field sortFeild = null;
	Locale locale = LocaleContextHolder.getLocale();
	
	public LocaleSpecificSorter(Class<T> typeParameterClass)
	{
		locale = LocaleContextHolder.getLocale();
		for (Field field : typeParameterClass.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Sortable.class)) {
				if (field.getAnnotation(Sortable.class).lang().equals(locale.getLanguage()))
				{
					sortFeild = field;
					break;
				}
			}
		}
		
	}
	
	public List<T> sort(List<T> list)
	{
		//If we did not find any @Sortable attribute return the original list
		if (sortFeild == null)
		{
			return list;
		}
		else
		{
			Collator collator = Collator.getInstance(locale);
			//collator.setStrength(Collator.IDENTICAL);
			
			 sortFeild.setAccessible(true);
			 return list.stream()
				        .sorted((first, second) -> {
				            try {
				                String a = (String) sortFeild.get(first);
				                String b = (String) sortFeild.get(second);
					                return collator.compare(a,b);
				            } catch (IllegalAccessException e) {
				                throw new RuntimeException("Error", e);
				            }
				        })
				        .collect(Collectors.toList());
		}
	}


}
