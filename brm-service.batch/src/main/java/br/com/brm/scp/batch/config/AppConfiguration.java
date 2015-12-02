package br.com.brm.scp.batch.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({"br.com.brm.scp.batch.service", "br.com.brm.scp.api.service.impl"})
@Import({ RepositoryConfiguration.class  })
public class AppConfiguration {
	
}
