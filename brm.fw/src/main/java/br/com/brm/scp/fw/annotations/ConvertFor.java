package br.com.brm.scp.fw.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import br.com.brm.scp.fw.test.dto.ClassTestD;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertFor {

	Class<ClassTestD> value();

}
