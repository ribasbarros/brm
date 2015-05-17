package br.com.brm.scp.fw.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertFor {

	Class<?> value();

}
