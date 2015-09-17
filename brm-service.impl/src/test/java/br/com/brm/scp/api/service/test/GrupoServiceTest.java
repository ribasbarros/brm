package br.com.brm.scp.api.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.GrupoRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.GrupoExistenteException;
import br.com.brm.scp.api.exceptions.GrupoNotFoundException;
import br.com.brm.scp.api.service.GrupoService;
import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.repositories.GrupoRepository;
import br.com.brm.scp.api.service.repositories.PerfilRepository;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)

public class GrupoServiceTest extends AbstractTestNGSpringContextTests{
	@Autowired
	GrupoService service; 
	@Autowired
	GrupoRepository repository;
	@Autowired
	PerfilRepository perfilRepository;
	
	private static final boolean TEST_CRUD = true;
	private static String ULTIMO_ID = "";
	private static String ID_INVALIDO = "XXXXXXX";
	
	
	@BeforeClass
	public void setup() throws Exception {
		perfilRepository.save(new PerfilDocument());
	}

	@AfterClass
	public void tearDown() throws Exception {
		perfilRepository.delete(perfilRepository.findAll().get(0).getId());
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider="novoGrupo")
	public void testCreate(GrupoRequestDTO request) throws GrupoExistenteException   {	
		Assert.assertNotNull(request);
		service.create(request);
		ULTIMO_ID = repository.findAll().get(0).getId();
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD",expectedExceptions=GrupoExistenteException.class, priority = 2, dataProvider="novoGrupo")
	public void testCreateGrupoExistenteException(GrupoRequestDTO request) throws GrupoExistenteException   {	
		Assert.assertNotNull(request);
		service.create(request);
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider="novoGrupoAlterado")
	public void testUpdate(GrupoRequestDTO request) throws GrupoNotFoundException    {	
		Assert.assertNotNull(request);
		service.update(request);
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", expectedExceptions=GrupoNotFoundException.class, priority = 4, dataProvider="novoGrupoAlterado")
	public void testUpdateGrupoNotFoundException(GrupoRequestDTO request) throws GrupoNotFoundException    {	
		Assert.assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.update(request);
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 5, dataProvider="novoGrupoAlterado")
	public void testDelete(GrupoRequestDTO request) throws GrupoNotFoundException    {	
		Assert.assertNotNull(request);
		service.delete(request.getId());
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", expectedExceptions=GrupoNotFoundException.class,priority = 6, dataProvider="novoGrupoAlterado")
	public void testDeleteGrupoNotFoundException(GrupoRequestDTO request) throws GrupoNotFoundException    {	
		Assert.assertNotNull(request);
		request.setId(ID_INVALIDO);
		service.delete(request.getId());
	}
	
	@DataProvider(name = "novoGrupo")
	public Object[][] providerUsuario() {
		GrupoRequestDTO request = doPerfil();
		
		return new Object[][] { new Object[] { request } };
	}

	private GrupoRequestDTO doPerfil() {
	
		GrupoRequestDTO dto = new GrupoRequestDTO();
		dto.setNome("GRUPO01");
		dto.setPerfis(ConverterHelper.convert(perfilRepository.findAll(), PerfilResponseDTO.class));
		return dto;
	}
	
	@DataProvider(name = "novoGrupoAlterado")
	public Object[][] providerUsuarioAlterado() {
		GrupoRequestDTO request = doGrupoAlterado();
		return new Object[][] { new Object[] { request } };
	}

	private GrupoRequestDTO doGrupoAlterado() {
		GrupoRequestDTO dto = new GrupoRequestDTO();
		dto.setId(ULTIMO_ID);
		dto.setNome("ALTERADO");
		dto.setPerfis(null);
		return dto;
	}
}
