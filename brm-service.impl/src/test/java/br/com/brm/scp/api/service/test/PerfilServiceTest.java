package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.PerfilRequestDTO;
import br.com.brm.scp.api.exceptions.PerfilExistenteException;
import br.com.brm.scp.api.exceptions.PerfilNotFoundException;
import br.com.brm.scp.api.service.PerfilService;
import br.com.brm.scp.api.service.repositories.PerfilRepository;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)

public class PerfilServiceTest extends AbstractTestNGSpringContextTests{
	@Autowired
	PerfilService service;
	@Autowired
	PerfilRepository repository;
	
	private static final boolean TEST_CRUD = true;
	private static String ULTIMO_ID = "";
	private static String ID_INVALIDO = "XXXXXXX";
	
		
	@BeforeClass
	public void setup() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {	
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider="novoPerfil")
	public void testCreate(PerfilRequestDTO request) throws PerfilExistenteException, PerfilNotFoundException  {	
		Assert.assertNotNull(request);
		service.create(request);
		ULTIMO_ID = repository.findAll().get(0).getId();
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", expectedExceptions=PerfilExistenteException.class ,priority = 2, dataProvider="novoPerfil")
	public void testCreatePerfilExistenteException(PerfilRequestDTO request) throws PerfilExistenteException, PerfilNotFoundException  {	
		Assert.assertNotNull(request);
		service.create(request);
	}
		
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoPerfilAlterado")
	public void update(PerfilRequestDTO request) throws PerfilNotFoundException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, expectedExceptions=PerfilNotFoundException.class ,groups = "CRUD", priority = 4, dataProvider = "novoPerfilAlterado")
	public void updatePerfilNotFoundException(PerfilRequestDTO request) throws PerfilNotFoundException{
		assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.update(request);
	}
	
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 5, dataProvider = "novoPerfilAlterado")
	public void delete(PerfilRequestDTO request) throws PerfilNotFoundException {
		assertNotNull(request);
		service.delete(request.getId());		
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD",expectedExceptions=PerfilNotFoundException.class, priority = 6, dataProvider = "novoPerfilAlterado")
	public void deletePerfilNotFoundException(PerfilRequestDTO request) throws PerfilNotFoundException {
		assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.delete(request.getId());		
	}
	
	
	@DataProvider(name = "novoPerfil")
	public Object[][] providerUsuario() {
		PerfilRequestDTO request = doPerfil();
		
		return new Object[][] { new Object[] { request } };
	}

	private PerfilRequestDTO doPerfil() {
		PerfilRequestDTO dto = new PerfilRequestDTO(null, "Permissao1");
		return dto;
	}
	
	@DataProvider(name = "novoPerfilAlterado")
	public Object[][] providerUsuarioAlterado() {
		PerfilRequestDTO request = doPerfilAlterado();
		
		return new Object[][] { new Object[] { request } };
	}

	private PerfilRequestDTO doPerfilAlterado() {
		PerfilRequestDTO dto = new PerfilRequestDTO(ULTIMO_ID, "PermissaoNOVA");
		return dto;
	}
}
