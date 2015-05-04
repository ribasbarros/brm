package br.com.brm.scp.api.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import br.com.brm.scp.api.service.CalculoService;
import br.com.brm.scp.mock.api.mockdata.MockData;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class CalculoServiceMockTest extends AbstractTestNGSpringContextTests {

	private static final boolean ESTOQUE_SEGURO = true;

	private static final boolean PREVISAO_VENDAS = true;

	@Autowired
	private CalculoService service;
	
	@Autowired
	private MockData mockDb;

	@BeforeClass
	public void setup() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	@org.testng.annotations.Test(enabled = ESTOQUE_SEGURO, groups = "Calculo", priority = 1)
	public void EstoqueSeguro() throws Exception {
		
	}
	
	@org.testng.annotations.Test(enabled = PREVISAO_VENDAS, groups = "Calculo", priority = 2)
	public void PrevisaoVendas() throws Exception {
		
	}
	
}
