package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.test.util.TestCallback;
import br.com.brm.scp.api.service.test.util.TestUtils;
import br.com.brm.scp.fw.helper.objects.RandomHelper;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class UsuarioSeviceMockTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UsuarioService service;

	private static final boolean TEST_CRUD = true;

	private UsuarioRequestDTO usuarioSuccess;

	@BeforeClass
	public void setup() throws Exception {
		
		clearMemory();
		
		loadTest();
	}

	private void clearMemory() {
		service.clearMemory();
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoUsuarioRequest")
	public void create(final UsuarioRequestDTO request, final UsuarioRequestDTO requestInvalid) throws Exception {

		assertNotNull(request);
		assertNotNull(requestInvalid);
		assertEquals("a", "v");
		
		UsuarioResponseDTO response = service.create(request);

		assertNotNull(response);
		assertEquals(response.getId() != null && response.getId() != 0, true);

		UsuarioResponseDTO response01 = service.find(response.getId());
		assertNotNull(response01);

		
		TestUtils.expectException(IllegalArgumentException.class, new TestCallback() {
			@Override
			public void doTest() throws Exception {
				service.create(requestInvalid);
			}
		});

		TestUtils.expectException(UsuarioExistentException.class, new TestCallback() {
			@Override
			public void doTest() throws Exception {
				service.create(request);
			}
		});
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "updateAndFindUsuarioNotFound")
	public void update(final UsuarioRequestDTO usuarioNotFound) throws Exception {
		
		String emailOld = usuarioSuccess.getEmail();
		String emailNew = "joaquim.silva.test@brm.com";

		usuarioSuccess.setEmail(emailNew);
		
		UsuarioResponseDTO response = service.update(usuarioSuccess);
		
		assertNotNull(response);
		assertTrue(response.getEmail() != emailOld);
		assertTrue(response.getEmail() == emailNew);
		
		
		TestUtils.expectException(UsuarioNotFoundException.class, new TestCallback() {
			@Override
			public void doTest() throws Exception {
				service.update(usuarioNotFound);
			}
		});
		
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "updateAndFindUsuarioNotFound")
	public void find(final UsuarioRequestDTO usuarioNotFound) throws Exception {
		
		UsuarioResponseDTO response = service.find(usuarioSuccess.getId());
		
		assertNotNull(response);
		assertNotNull(response.getEmail());
		
		Collection<UsuarioResponseDTO> all = service.all();
		
		assertNotNull(all);
		assertTrue(!all.isEmpty());
		assertTrue(all.size()>0);
		
		TestUtils.expectException(UsuarioNotFoundException.class, new TestCallback() {
			@Override
			public void doTest() throws Exception {
				service.clearMemory();
				service.all();
			}
		});
		
		loadTest();
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 4, dataProvider = "updateAndFindUsuarioNotFound")
	public void delete(final UsuarioRequestDTO usuarioNotFound) throws Exception {

		
	}

	private void loadTest() throws UsuarioExistentException {
		usuarioSuccess = criarUsuarioSuccess();
	}

	@DataProvider(name = "novoUsuarioRequest")
	public Object[][] criaUsuarioRequest() {
		UsuarioRequestDTO request = doUsuarioValido();
		UsuarioRequestDTO requestInvalido = dUsuarioInvalido();
		return new Object[][] { new Object[] { request, requestInvalido } };
	}
	
	@DataProvider(name = "updateAndFindUsuarioNotFound")
	public Object[][] criaUsuario4TestNotFound(){
		
		long random = RandomHelper.UUID();
		
		UsuarioRequestDTO requestNotFound = new UsuarioRequestDTO();
		requestNotFound.setId(random);
		requestNotFound.setEmail("notfound@notfound.com.br");
		requestNotFound.setCargo("notfound");
		requestNotFound.setNome("notfound");
		
		return new Object[][] { new Object[] { requestNotFound } };
	}
	

	private UsuarioRequestDTO dUsuarioInvalido() {
		UsuarioRequestDTO requestInvalido = new UsuarioRequestDTO();
		return requestInvalido;
	}

	private UsuarioRequestDTO doUsuarioValido() {

		long random = RandomHelper.UUID();
		
		UsuarioRequestDTO request = new UsuarioRequestDTO();
		request.setId(null);
		request.setCargo("Analista de Sistemas Java");
		request.setNome("Joaquim da Silva - " + random);
		request.setEmail("joaquim"+random+"@brm.com.br");
		return request;
	}

	private UsuarioRequestDTO criarUsuarioSuccess() throws UsuarioExistentException {
		UsuarioRequestDTO request = doUsuarioValido();
		UsuarioResponseDTO response = service.create(request);
		request.setId(response.getId());
		return request;
	}
	
}
