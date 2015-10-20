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

import br.com.brm.scp.api.dto.request.DfuRequestDTO;
import br.com.brm.scp.api.exceptions.DfuExistenteException;
import br.com.brm.scp.api.exceptions.DfuNotFoundException;
import br.com.brm.scp.api.service.DfuService;
import br.com.brm.scp.api.service.repositories.DfuRepository;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class DfuServiceTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	DfuService service;
	@Autowired
	DfuRepository repository;
	
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
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider="novaDfu")
	public void testCreate(DfuRequestDTO request) throws DfuExistenteException, DfuNotFoundException  {	
		Assert.assertNotNull(request);
		service.create(request);
		ULTIMO_ID = repository.findAll().get(0).getId();
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", expectedExceptions=DfuExistenteException.class ,priority = 2, dataProvider="novaDfu")
	public void testCreateDfuExistenteException(DfuRequestDTO request) throws DfuExistenteException, DfuNotFoundException  {	
		Assert.assertNotNull(request);
		request.setId(ULTIMO_ID);
		service.create(request);
	}
		
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoDfuAlterado")
	public void update(DfuRequestDTO request) throws DfuNotFoundException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, expectedExceptions=DfuNotFoundException.class ,groups = "CRUD", priority = 4, dataProvider = "novoDfuAlterado")
	public void updateDfuNotFoundException(DfuRequestDTO request) throws DfuNotFoundException{
		assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.update(request);
	}
	
	
	@org.testng.annotations.Test(enabled = TEST_CRUD && DELETE_TEST, groups = "CRUD", priority = 5, dataProvider = "novoDfuAlterado")
	public void delete(DfuRequestDTO request) throws DfuNotFoundException {
		assertNotNull(request);
		service.delete(request.getId());		
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD",expectedExceptions=DfuNotFoundException.class, priority = 6, dataProvider = "novoDfuAlterado")
	public void deleteDfuNotFoundException(DfuRequestDTO request) throws DfuNotFoundException {
		assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.delete(request.getId());		
	}

	
	@DataProvider(name = "novaDfu")
	public Object[][] providerDfu() {
		DfuRequestDTO request = new DfuRequestDTO();
		request.setDdv(1);
		
		return new Object[][] { new Object[] { request } };
	}
	
	@DataProvider(name = "novoDfuAlterado")
	public Object[][] providerDfuAlterada() {
		DfuRequestDTO request = new DfuRequestDTO();
		request.setId(ULTIMO_ID);
		request.setDdv(2);
		
		return new Object[][] { new Object[] { request } };
	}

}
