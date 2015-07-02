package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.TagService;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.helper.objects.RandomHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.CategoriaDocument;
import br.com.brm.scp.mock.api.service.document.ContatoDocument;
import br.com.brm.scp.mock.api.service.document.FornecedorDocument;
import br.com.brm.scp.mock.api.service.document.ItemDocument;
import br.com.brm.scp.mock.api.service.document.TagDocument;
import br.com.brm.scp.mock.api.service.document.TelefoneDocument;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;
import br.com.brm.scp.mock.api.service.status.PlanejamentoSku;
import br.com.brm.scp.mock.api.service.status.StatusProduto;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class SkuServiceMockTest extends AbstractTestNGSpringContextTests {

	private static final long USUARIO_LOGADO_FAKE = RandomHelper.UUID();
	
	private static Logger logger = Logger.getLogger(SkuServiceMockTest.class);

	@Autowired
	private SkuService service;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private MockData mockDb;

	@Autowired
	private TagService sTag;

	private static final boolean EM_DESENVOLVIMENTO = true;

	private static final boolean CREATION_SKU = true;

	private static final boolean SELECAO_ORIGENS = false;

	private static final boolean SKU_ANALITICA = false;

	private static final boolean GERAR_PEDIDOS = false;

	private static final boolean HISTORICO = false;

	private SkuRequestDTO skuRequestSuccess;

	/**
	 * Startup inicial do teste, carregamento da massa de teste
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public void setup() throws Exception {
		skuRequestSuccess = new SkuRequestDTO();

		doCreateUsuario4MassTest();
		doCreateTag4MassTest();
		doCreateItems4MassTest();
	}

	/**
	 * Excecucao do final do teste
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public void tearDown() throws Exception {
		// rollback da massa de dados do teste
	}

	/**
	 * Criacao inicial da SKU
	 * 
	 * @param item
	 * @param tags
	 * @throws SkuExistenteException
	 */
	@Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 1, dataProvider = "ITEM_TAGS_RANDOM")
	public void criarSkuPart1(ItemResponseDTO item, Collection<TagResponseDTO> tags) throws SkuExistenteException {
		// SELECAO DO ITEM
		assertNotNull(item);
		assertNotNull(tags);

		skuRequestSuccess.setItem(item);
		skuRequestSuccess.setTags(tags);

		SkuResponseDTO response = service.create(skuRequestSuccess);

		skuRequestSuccess.setId(response.getId());
		
		assertNotNull(response);
		assertTrue(response.getId() != 0 && response.getId() != null);
		
	}

	/**
	 * Apos a criacao da sku os campos tornam desabilitados para o preenchimento
	 * 
	 * @throws SkuNotFoundException
	 * @throws UsuarioNotFoundException
	 * @throws SkuExistenteException 
	 */
	@Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 2)
	public void criarSkuPart2() throws SkuNotFoundException, UsuarioNotFoundException, SkuExistenteException {
		// PREENCHE OS CAMPOS
		skuRequestSuccess.setAutomatica(Boolean.TRUE);
		skuRequestSuccess.setCustoUnitario(BigDecimal.ZERO);
		skuRequestSuccess.setDataDescontinuacao(Calendar.getInstance());
		skuRequestSuccess.setDataMaturidade(Calendar.getInstance());
		skuRequestSuccess.setDescricao("Descricao da sku de teste!");
		skuRequestSuccess.setEstoqueAtual(0);
		skuRequestSuccess.setEstoqueIdeal(0);
		skuRequestSuccess.setEstoqueMaximo(0);
		skuRequestSuccess.setEstoqueMinimo(0);
		skuRequestSuccess.setFrequenciaAnalise(new Integer[] { Calendar.MONDAY, Calendar.WEDNESDAY });
		skuRequestSuccess.setLoteReposicao(100);
		skuRequestSuccess.setLoteReposicaoHistorico(0);
		skuRequestSuccess.setPedidos(new ArrayList<PedidoResponseDTO>());
		skuRequestSuccess.setModelo(PlanejamentoSku.ESTOQUE);

		skuRequestSuccess.setDataCriacao(Calendar.getInstance());
		skuRequestSuccess.setUsuarioCriacao(usuarioService.find(USUARIO_LOGADO_FAKE));

		SkuResponseDTO response = service.ativar(skuRequestSuccess);

		assertNotNull(response);
		
		logger.info("******* STATUS DO BANCO *******");
		logger.info(mockDb.getSkuCollection());
		logger.info("******* ** *******");

	}

	/**
	 * Teste para a excecao SkuNotFoundException
	 * 
	 * @param notFound
	 * @throws SkuNotFoundException
	 * @throws SkuExistenteException 
	 */
	@Test(enabled = CREATION_SKU && false, expectedExceptions = SkuExistenteException.class, groups = "CRIACAO_SKU", priority = 3, dataProvider = "skuNotFound")
	public void skuAtivarSkuNotFoundException(SkuRequestDTO notFound) throws SkuNotFoundException, SkuExistenteException {
		service.ativar(notFound);
	}

	/**
	 * Teste para a excecao SkuExistenteException
	 * 
	 * @throws SkuExistenteException
	 */
	@Test(enabled = CREATION_SKU, expectedExceptions = SkuExistenteException.class, groups = "CRIACAO_SKU", priority = 4)
	public void skuExistenteException() throws SkuExistenteException {
		SkuResponseDTO response = service.create(skuRequestSuccess);
		assertNotNull(response);

	}

	@Test(enabled = SELECAO_ORIGENS, groups = "SELECIONAR_ORIGENS", priority = 5)
	public void selecionarOrigens() throws Exception {
		// TODO SELECIONA AS ORIGENS DESSA SKU
		// TODO SELECIONA UMA ORIGEM DEFAULT
	}

	@Test(enabled = SKU_ANALITICA, groups = "SKU_VALIDACAO", priority = 3)
	public void validarSku() throws Exception {

		// TODO FASE DE VIDA?

		// TODO ESTOQUE MAXIMO?

		// TODO ESTOQUE MINIMO?

		// TODO ESTOQUE ATUAL?
	}

	@Test(enabled = SKU_ANALITICA, groups = "SKU_VALIDACAO", priority = 4)
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

	/**
	 * Seleccao de Item e Tag automatizado
	 * 
	 * @return
	 * @throws UsuarioNotFoundException
	 */
	@DataProvider(name = "ITEM_TAGS_RANDOM")
	public Object[][] criaItemTagsRandom() throws UsuarioNotFoundException {

		// SELECAO DO ITEM
		Collection<ItemResponseDTO> items = ConverterHelper.convert(mockDb.getItemCollection().values(),
				ItemResponseDTO.class);
		int selecionarRandomicamente = 0 + (int) (Math.random() * items.size());

		// ADIÇÃO DAS TAGS - CLICA EM OK
		Collection<TagResponseDTO> selecionadas = new ArrayList<TagResponseDTO>();
		selecionadas = selecionarRandomTagByNivel(1, selecionadas);
		selecionadas = selecionarRandomTagByNivel(2, selecionadas);
		selecionadas = selecionarRandomTagByNivel(3, selecionadas);

		return new Object[][] {
				new Object[] { new ArrayList<ItemResponseDTO>(items).get(selecionarRandomicamente), selecionadas } };
	}

	/**
	 * Selecao de Tag por nivel
	 * 
	 * @param nivel
	 * @param selecionadas
	 * @return
	 */
	private Collection<TagResponseDTO> selecionarRandomTagByNivel(int nivel, Collection<TagResponseDTO> selecionadas) {

		Collection<TagDocument> all = mockDb.getTagCollection().values();

		Collection<TagDocument> byNivelParam = new ArrayList<TagDocument>();
		for (TagDocument doc : all) {
			if (doc.getNivel().intValue() == nivel) {
				byNivelParam.add(doc);
			}
		}

		int selecionarRandomicamente = 0 + (int) (Math.random() * byNivelParam.size());
		TagDocument selecionado = new ArrayList<TagDocument>(byNivelParam).get(selecionarRandomicamente);

		selecionadas.add((TagResponseDTO) ConverterHelper.convert(selecionado, TagResponseDTO.class));

		return selecionadas;
	}

	/**
	 * Dados para teste de sku not found
	 * 
	 * @return
	 * @throws UsuarioNotFoundException
	 */
	@DataProvider(name = "skuNotFound")
	public Object[][] criaUsuarioRequest() throws UsuarioNotFoundException {
		SkuRequestDTO notFound = doSkuNotFound();
		return new Object[][] { new Object[] { notFound } };
	}

	/**
	 * Gerador de objeto de sku not found
	 * 
	 * @return
	 * @throws UsuarioNotFoundException
	 */
	private SkuRequestDTO doSkuNotFound() throws UsuarioNotFoundException {

		SkuRequestDTO notFound = new SkuRequestDTO();

		notFound.setId(RandomHelper.UUID());

		Collection<ItemResponseDTO> items = ConverterHelper.convert(mockDb.getItemCollection().values(),
				ItemResponseDTO.class);
		notFound.setItem(new ArrayList<ItemResponseDTO>(items).get(0));

		Collection<TagResponseDTO> selecionadas = new ArrayList<TagResponseDTO>();
		selecionarRandomTagByNivel(1, selecionadas);
		selecionarRandomTagByNivel(2, selecionadas);
		selecionarRandomTagByNivel(3, selecionadas);
		notFound.setTags(selecionadas);

		notFound.setAutomatica(Boolean.TRUE);
		notFound.setCustoUnitario(BigDecimal.ZERO);
		notFound.setDataDescontinuacao(Calendar.getInstance());
		notFound.setDataMaturidade(Calendar.getInstance());
		notFound.setDescricao("NOT FOUND");
		notFound.setEstoqueAtual(99);
		notFound.setEstoqueIdeal(99);
		notFound.setEstoqueMaximo(99);
		notFound.setEstoqueMinimo(99);
		notFound.setFrequenciaAnalise(new Integer[] { Calendar.DAY_OF_WEEK });
		notFound.setLoteReposicao(99);
		notFound.setLoteReposicaoHistorico(0);
		notFound.setPedidos(new ArrayList<PedidoResponseDTO>());
		notFound.setModelo(PlanejamentoSku.ESTOQUE);

		notFound.setDataCriacao(Calendar.getInstance());
		notFound.setUsuarioCriacao(usuarioService.find(USUARIO_LOGADO_FAKE));

		return notFound;

	}

	/**
	 * Gerador de objetos de fornecedor (1) e items (n)
	 */
	private void doCreateItems4MassTest() {

		long uuidFornecedor = RandomHelper.UUID();

		FornecedorDocument fornecedor = new FornecedorDocument();
		fornecedor.setCnpj("999.999.999/9999-99");

		Collection<ContatoDocument> contato = doContatoFake();

		fornecedor.setContato(contato);
		fornecedor.setDescricao("FORNECEDOR PARA TESTE");
		fornecedor.setId(uuidFornecedor);
		fornecedor.setInscricaoEstadual("999.999.999.999");
		fornecedor.setNomeFantasia("NOME FANTASIA FAKE");
		fornecedor.setRazaoSocial("RAZAO SOCIAL FAKE");

		mockDb.getFornecedorCollection().put(uuidFornecedor, fornecedor);

		for (int i = 0; i < 100; i++) {

			ItemDocument item = new ItemDocument();

			long uuid = RandomHelper.UUID();

			item.setCategoria(new CategoriaDocument(RandomHelper.UUID(), "CATEGORIA_TESTE"));
			item.setFornecedor(fornecedor);
			item.setId(uuid);

			String nomeProduto = RandomHelper.randomString(5);

			item.setNome(String.format("TST_NOME_PRODUTO_%s:%s", i, nomeProduto));
			item.setNomeReduzido(String.format("TST_%s:%s", i, nomeProduto));
			item.setStatus(StatusProduto.ATIVO);
			item.setValorUnitario(new BigDecimal(Math.random() * 1000));

			mockDb.getItemCollection().put(uuid, item);

		}
	}

	/**
	 * Gerador de contatos fake
	 * 
	 * @return Collection<ContatoDocument>
	 */
	private Collection<ContatoDocument> doContatoFake() {

		Collection<ContatoDocument> contatos = new ArrayList<ContatoDocument>();
		for (int i = 0; i < 3; i++) {

			long uuid = RandomHelper.UUID();

			ContatoDocument contato = new ContatoDocument();
			contato.setNome(String.format("NM_%s", RandomHelper.randomString(5)));
			contato.setCep(String.format("03344-00%s", i));
			contato.setCidade("São Paulo");
			contato.setEndereco(String.format("Rua %s", i));
			contato.setId(uuid);
			Collection<TelefoneDocument> telefone = doTelefoneFake();
			contato.setTelefone(telefone);
			contato.setUf("SP");

			contatos.add(contato);
		}

		return contatos;

	}

	/**
	 * Gerador de Telefones fake
	 * 
	 * @return
	 */
	private Collection<TelefoneDocument> doTelefoneFake() {

		Collection<TelefoneDocument> telefones = new ArrayList<TelefoneDocument>();

		for (int i = 0; i < 2; i++) {
			TelefoneDocument telefone = new TelefoneDocument();

			telefone.setCelular(Boolean.FALSE);
			telefone.setNumero(new BigInteger(String.format("1194455315%s", i)));
			telefone.setRamal("");

			telefones.add(telefone);
		}

		return telefones;

	}

	/**
	 * Gerador de massa de tag para teste
	 */
	private void doCreateTag4MassTest() {
		for (int i = 0; i < 5; i++) { // Numero de Niveis
			int randomNum = 2 + (int) (Math.random() * 10); // Gera numero de
															// niveis
															// randomicamente
			for (int x = 0; x < randomNum; x++) {
				long uuid = RandomHelper.UUID();
				TagDocument tag = new TagDocument();
				tag.setId(uuid);
				tag.setNivel(i + 1);
				tag.setNome(String.format("TST_NVL_%s:%s", i, RandomHelper.randomString(5)));
				mockDb.getTagCollection().put(uuid, tag);
			}
		}
	}

	/**
	 * Gerador de Usuario Fake
	 */
	private void doCreateUsuario4MassTest() {
		mockDb.getUsuarioCollection().put(USUARIO_LOGADO_FAKE,
				new UsuarioDocument(USUARIO_LOGADO_FAKE, "Usuario Teste 01", "4Test", "test@test.com.br"));
	}

}
