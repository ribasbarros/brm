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

import br.com.brm.scp.api.dto.request.ModeloPlanejamentoRequestDTO;
import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.ModeloPlanejamentoService;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.api.service.test.util.TestCallback;
import br.com.brm.scp.api.service.test.util.TestUtils;
import br.com.brm.scp.mock.api.mockdata.MockData;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class ModeloPlanejamentoSeviceMockTest extends AbstractTestNGSpringContextTests {

	private static final boolean TEST_CRUD = true;

	@Autowired
	private MockData mockDb;
	
	@Autowired
	private ModeloPlanejamentoService service;
	
	@BeforeClass
	public void setup() throws Exception {
		clearMemory();
	}

	private void clearMemory() {
	}

	@AfterClass
	public void tearDown() throws Exception {
	}

	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoModelo")
	public void create(final ModeloPlanejamentoRequestDTO request) throws Exception {
		
	}
	
	@DataProvider(name = "novoModelo")
	public Object[][] criaModelo4Test(){
		
		long random = UUID.randomUUID().getMostSignificantBits();
		ModeloPlanejamentoRequestDTO request = new ModeloPlanejamentoRequestDTO();

		//TODO
		
		return new Object[][] { new Object[] { request } };
	}
	
}
