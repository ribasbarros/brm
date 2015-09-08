package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class UsuarioSeviceMockTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UsuarioService service;

	private static final boolean TEST_CRUD = true;

	@BeforeClass
	public void setup() throws Exception {		
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoUsuario")
	public void create(UsuarioRequestDTO request) throws UsuarioExistentException {
		assertNotNull(request);
		service.create(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "novoAlterado")
	public void update(UsuarioRequestDTO request) throws UsuarioNotFoundException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoAlterado")
	public void delete(UsuarioRequestDTO request) throws UsuarioNotFoundException {
		assertNotNull(request);
		service.delete(request.getId());		
	}	

	@DataProvider(name = "novoUsuario")
	public Object[][] criaUsuarioRequest() {
		UsuarioRequestDTO request = doUsuarioValido();
		return new Object[][] { new Object[] { request } };
	}
	
	@DataProvider(name = "novoAlterado")
	public Object[][] criaUsuarioAlterado() {
		UsuarioRequestDTO request = doUsuarioAlterado();
		return new Object[][] { new Object[] { request } };
	}
	
	private UsuarioRequestDTO doUsuarioAlterado() {
		UsuarioRequestDTO request = new UsuarioRequestDTO();
		request.setId("1");
		request.setCargo("Juninho");
		request.setNome("Leon da Silva - ");
		request.setEmail("leon@brm.com.br");
		return request;
	}

	private UsuarioRequestDTO doUsuarioValido(){
		UsuarioRequestDTO request = new UsuarioRequestDTO();
		request.setId(null);
		request.setCargo("Analista de Sistemas Java");
		request.setNome("Joaquim da Silva - ");
		request.setEmail("joaquim@brm.com.br");
		return request;
	}
}
