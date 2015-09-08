package br.com.brm.scp.fw.helper.validators;

import java.util.regex.Pattern;

public class EmailValidator {
	
	public static final String EMAIL_RFC_5322 = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	
	public static boolean isEmail(String email){
		Pattern pattern = Pattern.compile(EMAIL_RFC_5322);
		return pattern.matcher(email).matches();
	}
}
