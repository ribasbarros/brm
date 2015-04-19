package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.test.util.TestCallback;
import br.com.brm.scp.api.service.test.util.TestUtils;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class UsuarioSeviceMockTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UsuarioService service;

	private static final boolean TEST_CRUD = true;

	private UsuarioRequestDTO usuarioSuccess;

	@BeforeClass
	public void setup() throws Exception {
		usuarioSuccess = criarUsuarioSuccess();
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoUsuarioRequest")
	public void create(final UsuarioRequestDTO request, final UsuarioRequestDTO requestInvalid) throws Exception {

		assertNotNull(request);
		assertNotNull(request);

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
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2)
	public void update() throws Exception {
		
		String emailOld = usuarioSuccess.getEmail();
		String emailNew = "joaquim.silva@brm.com";

		usuarioSuccess.setEmail(emailNew);
		
		UsuarioResponseDTO response = service.update(usuarioSuccess);
		
	}

	@DataProvider(name = "novoUsuarioRequest")
	public Object[][] criaUsuarioRequest() {
		UsuarioRequestDTO request = doUsuarioValido();
		UsuarioRequestDTO requestInvalido = dUsuarioInvalido();
		return new Object[][] { new Object[] { request, requestInvalido } };
	}

	private UsuarioRequestDTO dUsuarioInvalido() {
		UsuarioRequestDTO requestInvalido = new UsuarioRequestDTO();
		return requestInvalido;
	}

	private UsuarioRequestDTO doUsuarioValido() {
		UsuarioRequestDTO request = new UsuarioRequestDTO();
		request.setId(null);
		request.setCargo("Analista de Sistemas Java");
		request.setNome("Joaquim da Silva");
		request.setEmail("joaquim@brm.com.br");
		return request;
	}

	private UsuarioRequestDTO criarUsuarioSuccess() throws UsuarioExistentException {
		UsuarioRequestDTO request = doUsuarioValido();
		UsuarioResponseDTO response = service.create(request);
		request.setId(response.getId());
		return request;
	}
	
}
