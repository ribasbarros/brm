package br.com.brm.scp.api.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import br.com.brm.scp.api.service.PerfilService;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class PerilSeviceMockTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private PerfilService service;

	private static final boolean TEST_CRUD = true;

	@BeforeClass
	public void setup() throws Exception {
		
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1)
	public void create() throws Exception {
		
	}
	
}
