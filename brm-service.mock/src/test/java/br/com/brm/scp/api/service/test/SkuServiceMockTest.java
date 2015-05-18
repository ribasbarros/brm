package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.strategies.Sku;
import br.com.brm.scp.mock.api.service.strategies.impl.SkuCreateStrategyImpl;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class SkuServiceMockTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private SkuService service;
	
	@Autowired
	private MockData mockDb;

	@Autowired
	private ItemService sItem;

	@Autowired
	private TagService sTag;

	private static final boolean CREATION_SKU = true;

	private static final boolean VALIDATION_SKU = true;

	private static final boolean GERAR_PEDIDOS = true;

	private static final boolean HISTORICO = true;

	private SkuRequestDTO skuRequestSuccess;
	
	@BeforeClass
	public void setup() throws Exception {
		clearMemory();
		skuRequestSuccess = new SkuRequestDTO();
	}

	private void clearMemory() {
	
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}
	
	@org.testng.annotations.Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 1)
	public void createSelecionarItem(){
		//TODO SELECAO DO ITEM
		Collection<ItemResponseDTO> items = sItem.all();
		skuRequestSuccess.setProduto(new ArrayList<ItemResponseDTO>(items).get(0));
		assertNotNull(skuRequestSuccess.getProduto());
	}
	
	@org.testng.annotations.Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 2)
	public void createSelecionarTags(){
		//TODO ADIÇÃO DAS TAGS - CLICA EM OK
		Collection<TagResponseDTO> selecionadas = new ArrayList<TagResponseDTO>();
		selecionadas = sTag.selecionar(selecionadas); //Nivel 1
		selecionadas = sTag.selecionar(selecionadas); //Nivel 2
		selecionadas = sTag.selecionar(selecionadas); //Nivel 3
		skuRequestSuccess.setTags(selecionadas);
		assertNotNull(skuRequestSuccess.getTags());
	}
	
	@org.testng.annotations.Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 3)
	public void createHasSku(){
		//TODO JAH EXISTE ESSA SKU?
		boolean hasSku = service.hasSku(skuRequestSuccess);
		assertFalse(hasSku);
	}

	@org.testng.annotations.Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 4)
	public void createPreencherAndCriarCampos(){
		//TODO PREENCHE OS CAMPOS
		//skuRequestSuccess.set
		//TODO SELECIONA FREQUENCIA DE ANALISE
		
	}
	

	@org.testng.annotations.Test(enabled = CREATION_SKU, groups = "SKU", priority = 1)
	public void create() throws Exception {
		
		//TODO SELECIONA O MODELO DE PLANEJAMENTO

	}
	
	@org.testng.annotations.Test(enabled = CREATION_SKU, groups = "SKU", priority = 2)
	public void selecionar() throws Exception {
		
		//TODO SELECIONA AS ORIGENS DESSA SKU
		
		//TODO SELECIONA UMA ORIGEM DEFAULT
		
	}
	
	@org.testng.annotations.Test(enabled = VALIDATION_SKU, groups = "SKU_VALIDACAO", priority = 3)
	public void validarSku() throws Exception {
		
		//TODO FASE DE VIDA?
	
		//TODO ESTOQUE MAXIMO?
			
		//TODO ESTOQUE MINIMO?
			
		//TODO ESTOQUE ATUAL?
	}
		
	@org.testng.annotations.Test(enabled = VALIDATION_SKU, groups = "SKU_VALIDACAO", priority = 4)
	public void validarDatas() throws Exception {
		
		//TODO DESCONTINUADA?
		
		//TODO "INFINITO"
		
		//TODO TEMPO DE REANALISE?
		
	}
		
	@org.testng.annotations.Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 5)
	public void createPedido() throws Exception {
		
		//TODO CRIAR PEDIDO
	}
	
	@org.testng.annotations.Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 6)
	public void listarPedidoPorSku() throws Exception {

		//TODO LISTAR PEDIDO
		
	}
	
	@org.testng.annotations.Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 7)
	public void aprovarPedido() throws Exception {

		//TODO APROVAR PEDIDO
		
	}
	
	@org.testng.annotations.Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 8)
	public void reprovarPedido() throws Exception {
		
		//TODO REPROVAR PEDIDO
		
	}
	
	@org.testng.annotations.Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 9)
	public void faturarPedido() throws Exception {
		
		//TODO FATURADO PEDIDO
		
	}
	
	@org.testng.annotations.Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 10)
	public void emTransitoPedido() throws Exception {
		
		//TODO EM TRANSITO
		
	}
	
	@org.testng.annotations.Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 11)
	public void recebidoPedido() throws Exception {
		
		//TODO RECEBIDO
		
	}
	
	
	@org.testng.annotations.Test(enabled = HISTORICO, groups = "SKU_RELATORIOS", priority = 12)
	public void historico() throws Exception {
		
		//TODO PARA GRAFICOS (BOLAR)
		
	}
	
}
