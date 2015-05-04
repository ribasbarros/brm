package br.com.brm.scp.api.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import br.com.brm.scp.api.service.DfuService;
import br.com.brm.scp.mock.api.mockdata.MockData;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class DfuServiceMockTest extends AbstractTestNGSpringContextTests {

	private static final boolean CREATION_DFU = true;

	@Autowired
	private DfuService service;
	
	@Autowired
	private MockData mockDb;

	@BeforeClass
	public void setup() throws Exception {
		clearMemory();
	}

	private void clearMemory() {
	
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	@org.testng.annotations.Test(enabled = CREATION_DFU, groups = "DFU", priority = 1)
	public void create() throws Exception {
		
		//TODO SELECAO DO ITEM
		
		//TODO ADIÇÃO DAS TAGS - CLICA EM OK
		
		//TODO JAH EXISTE ESSA DFU?
		
		//TODO PREENCHE OS CAMPOS
		
		//TODO SELECIONA O MODELO DE PLANEJAMENTO
		
	}
	
	//TODO DFU MADURA?
	
	//TODO SE SIM, MOSTRAR GRAFICOS HISTORICOS
	
	//TODO SE SIM, FAZER MOSTRAR PREVISOES DE VENDAS
	
	
}
