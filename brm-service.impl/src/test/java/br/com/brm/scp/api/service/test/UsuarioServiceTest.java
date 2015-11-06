package br.com.brm.scp.api.service.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.document.GrupoDocument;
import br.com.brm.scp.api.service.repositories.GrupoRepository;
import br.com.brm.scp.api.service.repositories.UsuarioRepository;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)

public class UsuarioServiceTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	UsuarioService service;
	@Autowired
	UsuarioRepository repository;
	@Autowired
	GrupoRepository grupoRepository;
	
	private static final boolean TEST_CRUD = true;
	private static String ULTIMO_ID = "";
	private static String ID_INVALIDO = "XXXXXXX";
	
	
	@BeforeClass
	public void setup() throws Exception {
		grupoRepository.save(new GrupoDocument());
	}

	@AfterClass
	public void tearDown() throws Exception {
		grupoRepository.delete(grupoRepository.findAll().get(0).getId());
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider="novoUsuario")
	public void testCreate(UsuarioRequestDTO request) throws UsuarioExistentException   {	
		Assert.assertNotNull(request);
		service.create(request);
		ULTIMO_ID = repository.findAll().get(0).getId();
	}	
	

	@Test(enabled = TEST_CRUD, groups = "CRUD",expectedExceptions=UsuarioExistentException.class, priority = 2, dataProvider="novoUsuario")
	public void testCreateUsuarioExistenteException(UsuarioRequestDTO request) throws UsuarioExistentException   {	
		Assert.assertNotNull(request);
		service.create(request);
	}
	
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider="novoUsuarioAlterado")
	public void testUpdate(UsuarioRequestDTO request) throws UsuarioNotFoundException    {	
		Assert.assertNotNull(request);
		service.update(request);
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", expectedExceptions=UsuarioNotFoundException.class, priority = 4, dataProvider="novoUsuarioAlterado")
	public void testUpdateUsuarioNotFoundException(UsuarioRequestDTO request) throws UsuarioNotFoundException    {	
		Assert.assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.update(request);
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 5, dataProvider="novoUsuarioAlterado")
	public void testDelete(UsuarioRequestDTO request) throws UsuarioNotFoundException{	
		Assert.assertNotNull(request);
		service.delete(request.getId());
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", expectedExceptions=UsuarioNotFoundException.class,priority = 6, dataProvider="novoUsuarioAlterado")
	public void testDeleteUsuarioNotFoundException(UsuarioRequestDTO request) throws UsuarioNotFoundException    {	
		Assert.assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.delete(request.getId());
	}
		
	@DataProvider(name = "novoUsuario")
	public Object[][] providerUsuario() {
		UsuarioRequestDTO request = doUsuario();
		
		return new Object[][] { new Object[] { request } };
	}

	@DataProvider(name = "novoUsuarioAlterado")
	public Object[][] providerUsuarioAlterado() {
		UsuarioRequestDTO request = doUsuarioAlterado();
		
		return new Object[][] { new Object[] { request } };
	}
	
	
	private UsuarioRequestDTO doUsuarioAlterado() {
		List<GrupoResponseDTO> list = new ArrayList<GrupoResponseDTO>();
		UsuarioRequestDTO request = new UsuarioRequestDTO();
		request.setId(ULTIMO_ID);
		request.setNome("LEO");
		request.setCargo("Analistalolo");
		request.setEmail("jose.sila@gmail.com");
		request.setGrupos(list);
		return request;
	}

	private UsuarioRequestDTO doUsuario() {
		List<GrupoResponseDTO> list = new ArrayList<GrupoResponseDTO>();
		UsuarioRequestDTO request = new UsuarioRequestDTO();
		request.setLogin("leon");
		request.setSenha("leon");
		request.setNome("Jose");
		request.setCargo("Analista BCP");
		request.setEmail("jose.sila@gmail.com");
		request.setGrupos(list);
		return request;
	}
	
}
