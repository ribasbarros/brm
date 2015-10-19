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

import br.com.brm.scp.api.dto.request.CategoriaRequestDTO;
import br.com.brm.scp.api.exceptions.CategoriaExistenteException;
import br.com.brm.scp.api.exceptions.CategoriaNotFoundException;
import br.com.brm.scp.api.service.CategoriaService;
import br.com.brm.scp.api.service.repositories.CategoriaRepository;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class CategoriaServiceTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	CategoriaService service;
	
	@Autowired
	CategoriaRepository repository;
	
	private static final boolean TEST_CRUD = true;
	private static final boolean DELETE_TEST = false;
	
	private static String ULTIMO_ID = "";
	private static String ID_INVALIDO = "XXXXXXX";
	
		
	@BeforeClass
	public void setup() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
		
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider="novoCategoria")
	public void testCreate(CategoriaRequestDTO request) throws CategoriaExistenteException, CategoriaNotFoundException  {	
		Assert.assertNotNull(request);
		service.create(request);
		ULTIMO_ID = repository.findAll().get(0).getId();
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", expectedExceptions=CategoriaExistenteException.class ,priority = 2, dataProvider="novoCategoria")
	public void testCreateCategoriaExistenteException(CategoriaRequestDTO request) throws CategoriaExistenteException, CategoriaNotFoundException  {	
		Assert.assertNotNull(request);
		service.create(request);
	}
		
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoCategoriaAlterado")
	public void update(CategoriaRequestDTO request) throws CategoriaNotFoundException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, expectedExceptions=CategoriaNotFoundException.class ,groups = "CRUD", priority = 4, dataProvider = "novoCategoriaAlterado")
	public void updateCategoriaNotFoundException(CategoriaRequestDTO request) throws CategoriaNotFoundException{
		assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.update(request);
	}
	
	
	@org.testng.annotations.Test(enabled = TEST_CRUD && DELETE_TEST, groups = "CRUD", priority = 5, dataProvider = "novoCategoriaAlterado")
	public void delete(CategoriaRequestDTO request) throws CategoriaNotFoundException {
		assertNotNull(request);
		service.delete(request.getId());		
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD",expectedExceptions=CategoriaNotFoundException.class, priority = 6, dataProvider = "novoCategoriaAlterado")
	public void deleteCategoriaNotFoundException(CategoriaRequestDTO request) throws CategoriaNotFoundException {
		assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.delete(request.getId());		
	}
	
	
	@DataProvider(name = "novoCategoria")
	public Object[][] providerUsuario() {
		CategoriaRequestDTO request = doCategoria();
		
		return new Object[][] { new Object[] { request } };
	}

	private CategoriaRequestDTO doCategoria() {
		CategoriaRequestDTO dto = new CategoriaRequestDTO();
		dto.setNome("Categoria01");
		return dto;
	}
	
	@DataProvider(name = "novoCategoriaAlterado")
	public Object[][] providerUsuarioAlterado() {
		CategoriaRequestDTO request = doCategoriaAlterado();
		
		return new Object[][] { new Object[] { request } };
	}

	private CategoriaRequestDTO doCategoriaAlterado() {
		CategoriaRequestDTO dto = new CategoriaRequestDTO();
		dto.setId(ULTIMO_ID);
		dto.setNome("novaCategoria");
		return dto;
	}
	
	
	
}
