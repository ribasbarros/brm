package br.com.brm.scp.api.service.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.service.FornecedorService;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class FornecedorServiceTest extends AbstractTestNGSpringContextTests {
	private static final boolean TEST_CRUD = true;
	
	@Autowired
	private FornecedorService service;

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1)
	public void test() throws FornecedorExistenteException {
		service.create(new FornecedorRequestDTO());
	}
	
	
}
