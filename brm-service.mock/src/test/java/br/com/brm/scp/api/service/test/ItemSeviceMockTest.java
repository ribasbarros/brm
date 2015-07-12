package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFound;
import br.com.brm.scp.api.service.ItemService;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class ItemSeviceMockTest extends AbstractTestNGSpringContextTests{

	@Autowired
	private ItemService service;
	
	private static final boolean TEST_CRUD = true;
	
	@BeforeClass
	public void setup() throws Exception {
		//criar massa de dados para o teste
	}

	@AfterClass
	public void tearDown() throws Exception {
		//rollback da massa de dados do teste
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoItem")
	public void create(ItemRequestDTO request) throws FornecedorExistenteException {
		assertNotNull(request);
		try {
			service.create(request);
		} catch (ItemNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "novoItem")
	public void delete(ItemRequestDTO request) {
		assertNotNull(request);
		service.delete(request);		
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoItem")
	public void update(ItemRequestDTO request){
		assertNotNull(request);
		service.update(request);
	}
	
	@DataProvider(name = "novoItem")
	public Object[][] criaItemRequest() {
		ItemRequestDTO request = null;
		return new Object[][] { new Object[] { request } };
	}
}
