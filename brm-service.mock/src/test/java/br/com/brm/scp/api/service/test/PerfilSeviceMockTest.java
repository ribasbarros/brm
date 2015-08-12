package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.service.PerfilService;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class PerfilSeviceMockTest extends AbstractTestNGSpringContextTests {

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

	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoPerfil")
	public void create(PerfilRequestDTO request) throws PerfilExistenteException, PerfilNotFoundException {
		assertNotNull(request);
		service.create(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "novoPerfilAlterado")
	public void update(PerfilRequestDTO request) throws PerfilNotFoundException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoPerfilAlterado")
	public void delete(PerfilRequestDTO request) throws PerfilNotFoundException {
		assertNotNull(request);
		service.delete(request);		
	}
			
	@DataProvider(name = "novoPerfil")
	public Object[][] criaFornecedorRequest() {
		PerfilRequestDTO request = new PerfilRequestDTO(null,"Admin",null);
		return new Object[][] { new Object[] { request } };
	}
	
	@DataProvider(name = "novoPerfilAlterado")
	public Object[][] criaFornecedorRequest2() {
		PerfilRequestDTO request = new PerfilRequestDTO(1L,"Perfil2",null);
		return new Object[][] { new Object[] { request } };
	}
	
}
