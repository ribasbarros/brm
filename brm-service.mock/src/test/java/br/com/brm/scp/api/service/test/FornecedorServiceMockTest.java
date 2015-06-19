package br.com.brm.scp.api.service.test;


import static org.testng.AssertJUnit.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.service.FornecedorService;
import br.com.brm.scp.mock.api.mockdata.MockData;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class FornecedorServiceMockTest extends AbstractTestNGSpringContextTests {
	private static final boolean TEST_CRUD = true;
	
	@Autowired
	private MockData data;
	@Autowired
	private FornecedorService service;

	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoFornecedor")
	public void create(FornecedorRequestDTO request) throws FornecedorExistenteException {
		assertNotNull(request);
		service.create(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "novoFornecedor")
	public void delete(FornecedorRequestDTO request) {
		assertNotNull(request);
		service.delete(request);		
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoFornecedor")
	public void update(FornecedorRequestDTO request){
		assertNotNull(request);
		service.update(request);
	}
	
	@DataProvider(name = "novoFornecedor")
	public Object[][] criaFornecedorRequest() {
		FornecedorRequestDTO request = new FornecedorRequestDTO(null, "Fornecedor", "Fornecedor", "90098343123", null, null);
		return new Object[][] { new Object[] { request } };
	}
	
	
}
