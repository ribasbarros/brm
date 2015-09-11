package br.com.brm.scp.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({ "br.com.brm.scp.*"})
@Import({ MvcConfiguration.class, SecurityConfiguration.class })
public class AppConfiguration {
	
}
