package br.com.scp.brm.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({ "br.com.scp.brm.*"})
@Import({ MvcConfiguration.class, SecurityConfiguration.class })
public class AppConfiguration {
	
}
