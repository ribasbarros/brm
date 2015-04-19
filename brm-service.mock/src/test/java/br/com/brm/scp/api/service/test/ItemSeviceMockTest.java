package br.com.brm.scp.api.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import br.com.brm.scp.api.service.ItemService;

//@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class ItemSeviceMockTest {

	@Autowired
	private ItemService itemService;
	
	private static final boolean TEST_CRUD = true;
	
	@BeforeClass
	public void setup() throws Exception {
		//criar massa de dados para o teste
	}

	@AfterClass
	public void tearDown() throws Exception {
		//rollback da massa de dados do teste
	}

}
