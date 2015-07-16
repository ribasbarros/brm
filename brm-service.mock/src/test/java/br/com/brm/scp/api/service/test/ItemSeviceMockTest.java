package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
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
	public void create(ItemRequestDTO request) throws FornecedorExistenteException, ItemExistenteException {
		assertNotNull(request);
		try {
			service.create(request);
		} catch (ItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "novoItem")
	public void update(ItemRequestDTO request) throws ItemNotFoundException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoItem")
	public void delete(ItemRequestDTO request) throws ItemNotFoundException {
		assertNotNull(request);
		service.delete(request);		
	}
	
	@DataProvider(name = "novoItem")
	public Object[][] criaItemRequest() {
		ItemRequestDTO request = doItem() ;
		return new Object[][] { new Object[] { request } };
	}
	
	public ItemRequestDTO doItem(){
		ItemRequestDTO request = new ItemRequestDTO();
		request.setId(Long.valueOf("1"));
		request.setNome("Item1");
		request.setNomeReduzido("I1");
		request.setValorUnitario(BigDecimal.ONE);
		request.setFornecedor(doFornecedor());
		request.setCategoria(doCategoria());
		return request;
	}


	private CategoriaResponseDTO doCategoria() {
		// TODO Auto-generated method stub
		return new CategoriaResponseDTO();
	}

	private FornecedorResponseDTO doFornecedor() {
		// TODO Auto-generated method stub
		return new FornecedorResponseDTO();
	}
	
	
}
