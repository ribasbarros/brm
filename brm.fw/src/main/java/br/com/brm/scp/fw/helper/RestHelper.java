package br.com.brm.scp.fw.helper;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;

public class RestHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private MessageSource messageSource;
	
	protected String getLabel(String key){
		return messageSource.getMessage(key, null, Locale.getDefault());
	}
	
}
