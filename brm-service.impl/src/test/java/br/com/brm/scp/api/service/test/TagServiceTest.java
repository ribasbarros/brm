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

import br.com.brm.scp.api.dto.request.TagRequestDTO;
import br.com.brm.scp.api.exceptions.TagExistenteException;
import br.com.brm.scp.api.exceptions.TagIsUsedException;
import br.com.brm.scp.api.exceptions.TagNotFoundException;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.api.service.repositories.TagRepository;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)


public class TagServiceTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private TagRepository repository;
	@Autowired
	private TagService service;

	private static final boolean TEST_CRUD = true;
	private static String ULTIMO_ID = "";
	private static String ID_INVALIDO = "XXXXXXX";

	@BeforeClass
	public void setup() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
		repository.delete(ULTIMO_ID);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoTag")
	public void testCreate(TagRequestDTO request)
			throws TagExistenteException, TagNotFoundException {
		Assert.assertNotNull(request);
		service.create(request);
		ULTIMO_ID = repository.findAll().get(0).getId();
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", expectedExceptions=TagExistenteException.class ,priority = 2, dataProvider="novoTag")
	public void testCreateTagExistenteException(TagRequestDTO request) throws TagExistenteException, TagNotFoundException  {	
		Assert.assertNotNull(request);
		service.create(request);
	}
		
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoTagAlterado")
	public void update(TagRequestDTO request) throws TagNotFoundException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, expectedExceptions=TagNotFoundException.class ,groups = "CRUD", priority = 4, dataProvider = "novoTagAlterado")
	public void updateTagNotFoundException(TagRequestDTO request) throws TagNotFoundException{
		assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.update(request);
	}
	
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 5, dataProvider = "novoTagAlterado")
	public void delete(TagRequestDTO request) throws TagNotFoundException, TagIsUsedException {
		assertNotNull(request);
		service.delete(request.getId());		
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD",expectedExceptions=TagNotFoundException.class, priority = 6, dataProvider = "novoTagAlterado")
	public void deleteTagNotFoundException(TagRequestDTO request) throws TagNotFoundException, TagIsUsedException {
		assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.delete(request.getId());		
	}

	
	@DataProvider(name = "novoTag")
	public Object[][] providerTag() {
		TagRequestDTO request = new TagRequestDTO();
		request.setNivel(1);
		request.setNome("primeiraTag");
		
		return new Object[][] { new Object[] { request } };
	}
	
	@DataProvider(name = "novoTagAlterado")
	public Object[][] providerTagAlterado() {
		TagRequestDTO request = new TagRequestDTO();
		request.setId(ULTIMO_ID);
		request.setNivel(2);
		request.setNome("segundaTag");
		
		return new Object[][] { new Object[] { request } };
	}

}
