package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;
import br.com.brm.scp.mock.api.service.status.PlanejamentoSku;
import br.com.brm.scp.mock.api.service.status.StatusReposicaoEnum;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class SkuServiceMockTest extends AbstractTestNGSpringContextTests {

	private static final long USUARIO_LOGADO_FAKE = UUID.randomUUID().getMostSignificantBits();

	@Autowired
	private SkuService service;

	@Autowired
	private UsuarioService usuarioService;

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
		resetMemory();
		skuRequestSuccess = new SkuRequestDTO();
	}

	private void resetMemory() {
		mockDb.getUsuarioCollection().put(USUARIO_LOGADO_FAKE,
				new UsuarioDocument(USUARIO_LOGADO_FAKE, "Usuario Teste 01", "4Test", "test@test.com.br"));
	}

	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	@Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 1)
	public void criarSkuPart1() {
		// TODO SELECAO DO ITEM
		Collection<ItemResponseDTO> items = sItem.all();
		skuRequestSuccess.setProduto(new ArrayList<ItemResponseDTO>(items).get(0));
		assertNotNull(skuRequestSuccess.getProduto());

		// TODO ADIÇÃO DAS TAGS - CLICA EM OK
		Collection<TagResponseDTO> selecionadas = new ArrayList<TagResponseDTO>();
		selecionadas = sTag.selecionar(selecionadas); // Nivel 1
		selecionadas = sTag.selecionar(selecionadas); // Nivel 2
		selecionadas = sTag.selecionar(selecionadas); // Nivel 3
		skuRequestSuccess.setTags(selecionadas);
		assertNotNull(skuRequestSuccess.getTags());
		
		SkuResponseDTO response = service.create(skuRequestSuccess);
		
		assertNotNull(response);

	}
	@Test( expectedExceptions=SkuExistenteException.class )
	public void skuExistenteException(){
		
		SkuResponseDTO response = service.create(skuRequestSuccess);
		
		assertNotNull(response);
		
	}

	@Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 4)
	public void createPreencherAndCriarCampos() throws UsuarioNotFoundException {
		// TODO PREENCHE OS CAMPOS
		skuRequestSuccess.setAutomatica(Boolean.TRUE);
		skuRequestSuccess.setCustoUnitario(BigDecimal.ZERO);
		// skuRequestSuccess.setDataAlteracao(Calendar.getInstance());
		skuRequestSuccess.setDataDescontinuacao(Calendar.getInstance());
		skuRequestSuccess.setDataMaturidade(Calendar.getInstance());
		skuRequestSuccess.setDescricao("Descricao da sku de teste!");
		skuRequestSuccess.setEstoqueAtual(0);
		skuRequestSuccess.setEstoqueIdeal(0);
		skuRequestSuccess.setEstoqueMaximo(0);
		skuRequestSuccess.setEstoqueMinimo(0);
		skuRequestSuccess.setFrequenciaAnalise(new Integer[] { Calendar.DAY_OF_WEEK });
		// skuRequestSuccess.setId(id);
		skuRequestSuccess.setLoteReposicao(100);
		skuRequestSuccess.setLoteReposicaoHistorico(0);
		skuRequestSuccess.setPedidos(new ArrayList<PedidoResponseDTO>());
		skuRequestSuccess.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		skuRequestSuccess.setModelo(PlanejamentoSku.ESTOQUE);

		skuRequestSuccess.setDataCriacao(Calendar.getInstance());
		skuRequestSuccess.setUsuarioCriacao(usuarioService.find(USUARIO_LOGADO_FAKE));

	}

	@Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 5)
	public void create() throws Exception {
		// TODO SELECIONA AS ORIGENS DESSA SKU
		// TODO SELECIONA UMA ORIGEM DEFAULT
	}

	@Test(enabled = VALIDATION_SKU, groups = "SKU_VALIDACAO", priority = 3)
	public void validarSku() throws Exception {

		// TODO FASE DE VIDA?

		// TODO ESTOQUE MAXIMO?

		// TODO ESTOQUE MINIMO?

		// TODO ESTOQUE ATUAL?
	}

	@Test(enabled = VALIDATION_SKU, groups = "SKU_VALIDACAO", priority = 4)
	public void validarDatas() throws Exception {

		// TODO DESCONTINUADA?

		// TODO "INFINITO"

		// TODO TEMPO DE REANALISE?

	}

	@Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 5)
	public void createPedido() throws Exception {

		// TODO CRIAR PEDIDO
	}

	@Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 6)
	public void listarPedidoPorSku() throws Exception {

		// TODO LISTAR PEDIDO

	}

	@Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 7)
	public void aprovarPedido() throws Exception {

		// TODO APROVAR PEDIDO

	}

	@Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 8)
	public void reprovarPedido() throws Exception {

		// TODO REPROVAR PEDIDO

	}

	@Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 9)
	public void faturarPedido() throws Exception {

		// TODO FATURADO PEDIDO

	}

	@Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 10)
	public void emTransitoPedido() throws Exception {

		// TODO EM TRANSITO

	}

	@Test(enabled = GERAR_PEDIDOS, groups = "SKU_PEDIDOS", priority = 11)
	public void recebidoPedido() throws Exception {

		// TODO RECEBIDO

	}

	@Test(enabled = HISTORICO, groups = "SKU_RELATORIOS", priority = 12)
	public void historico() throws Exception {

		// TODO PARA GRAFICOS (BOLAR)

	}

}
