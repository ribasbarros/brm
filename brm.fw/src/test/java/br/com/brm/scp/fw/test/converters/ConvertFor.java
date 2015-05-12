package br.com.brm.scp.fw.test.converters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertFor {

	Class<ClassTestD> value();

}
