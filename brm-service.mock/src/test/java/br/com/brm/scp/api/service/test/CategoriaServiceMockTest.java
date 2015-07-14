package br.com.brm.scp.api.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import br.com.brm.scp.api.service.CategoriaService;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class CategoriaServiceMockTest extends AbstractTestNGSpringContextTests {
	private static final boolean TEST_CRUD = true;
	
	@Autowired
	private CategoriaService service;

}
