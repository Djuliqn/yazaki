package com.yazaki.yazaki.domain.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceWrapper {

	private final MessageSource messageSource;
	
	@Autowired
	MessageSourceWrapper(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public final String getMessage(final String code) {
		return messageSource.getMessage(code, null, null);
	}
}
