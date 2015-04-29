package br.com.brm.scp.api.service.test;

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

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.request.UsuarioRequestDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioExistentException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.test.util.TestCallback;
import br.com.brm.scp.api.service.test.util.TestUtils;
import br.com.brm.scp.mock.api.mockdata.MockData;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class SkuServiceMockTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private SkuService service;
	
	@Autowired
	private MockData mockDb;

	private static final boolean ENABLED = true;

	@BeforeClass
	public void setup() throws Exception {
		clearMemory();
	}

	private void clearMemory() {
	
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	@org.testng.annotations.Test(enabled = ENABLED, groups = "SKU", priority = 1)
	public void create() throws Exception {
		
		//TODO SELECAO DO ITEM
		
		//TODO ADIÇÃO DAS TAGS - CLICA EM OK
		
		//TODO JAH EXISTE ESSA SKU?
		
		//TODO SINALIZADOR?
		
		//TODO PREENCHE OS CAMPOS
		
		//TODO SELECIONA O MODELO DE PLANEJAMENTO
		
		//TODO SELECIONA FREQUENCIA DE ANALISE

	}
	
	@org.testng.annotations.Test(enabled = ENABLED, groups = "SKU", priority = 2)
	public void validarSku() throws Exception {
		
		//TODO FASE DE VIDA?
	
		//TODO ESTOQUE MAXIMO?
			
		//TODO ESTOQUE MINIMO?
			
		//TODO ESTOQUE ATUAL?
	}
		
	@org.testng.annotations.Test(enabled = ENABLED, groups = "SKU", priority = 3)
	public void selecionar() throws Exception {
		
		//TODO SELECIONA AS ORIGENS DESSA SKU
		
		//TODO SELECIONA UMA ORIGEM DEFAULT
		
	}
		
	@org.testng.annotations.Test(enabled = ENABLED, groups = "SKU", priority = 4)
	public void createPedido() throws Exception {
		
		//TODO CRIAR PEDIDO
		
		//TODO LISTAR PEDIDO
		
		//TODO APROVAR PEDIDO
		
		//TODO REPROVAR PEDIDO
		
		//TODO FATURADO PEDIDO
		
		//TODO EM TRANSITO
		
		//TODO RECEBIDO
		
	}
	
	@org.testng.annotations.Test(enabled = ENABLED, groups = "SKU", priority = 5)
	public void validarDatas() throws Exception {
		
		//TODO DESCONTINUADA?
		
		//TODO "INFINITO"
		
		//TODO TEMPO DE REANALISE?
		
	}
	
	@org.testng.annotations.Test(enabled = ENABLED, groups = "SKU", priority = 6)
	public void historico() throws Exception {
		
		//TODO PARA GRAFICOS (BOLAR)
		
	}
	
	
	@DataProvider(name = "createSkuRequest")
	public Object[][] criaSkuRequest() {
		SkuRequestDTO request = doSkuValido();
		UsuarioRequestDTO requestInvalido = doSkuInvalido();
		return new Object[][] { new Object[] { request, requestInvalido } };
	}

	private UsuarioRequestDTO doSkuInvalido() {
		// TODO Auto-generated method stub
		return null;
	}

	private SkuRequestDTO doSkuValido() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
