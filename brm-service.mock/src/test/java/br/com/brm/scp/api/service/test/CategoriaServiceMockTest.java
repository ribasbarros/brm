package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistenteException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.service.CategoriaService;
import br.com.brm.scp.fw.helper.objects.RandomHelper;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class CategoriaServiceMockTest extends AbstractTestNGSpringContextTests {
	private static final boolean TEST_CRUD = true;
	Long id = RandomHelper.UUID();
	@Autowired
	private CategoriaService service;

	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novaCategoria")
	public void create(CategoriaRequestDTO request) throws CategoriaExistenteException, CategoriaNotFoundException {
		assertNotNull(request);
		service.create(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "novaCategoriaAlterada")
	public void update(CategoriaRequestDTO request) throws CategoriaNotFoundException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novaCategoria")
	public void delete(CategoriaRequestDTO request) throws CategoriaNotFoundException {
		assertNotNull(request);
		service.delete(request);		
	}
			
	@DataProvider(name = "novaCategoria")
	public Object[][] criaFornecedorRequest() {
		CategoriaRequestDTO request = new CategoriaRequestDTO(id,"Brinquedos");
		return new Object[][] { new Object[] { request } };
	}
	
	@DataProvider(name = "novaCategoriaAlterada")
	public Object[][] criaFornecedorRequest2() {
		CategoriaRequestDTO request = new CategoriaRequestDTO(id,"Outros");
		return new Object[][] { new Object[] { request } };
	}
	
}