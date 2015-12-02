package br.com.brm.scp.batch.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.brm.scp.security.config.RepositoryConfigurationTest;

@Configuration
@ComponentScan({"br.com.brm.scp.api.service.impl"})
@Import({ RepositoryConfigurationBatchTest.class })
public class AppConfigurationBatchTest {
	
}
