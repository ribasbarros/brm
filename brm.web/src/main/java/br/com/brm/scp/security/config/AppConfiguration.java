package br.com.brm.scp.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({ "br.com.brm.scp.controller", "br.com.brm.scp.security.*", "br.com.brm.scp.api.service.impl", "br.com.brm.scp.api.service.auth"})
@Import({ MvcConfiguration.class, SecurityConfiguration.class, RepositoryConfiguration.class  })
public class AppConfiguration {
	
}
