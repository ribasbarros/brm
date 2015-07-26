package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.SkuException;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.document.PedidoDocument;
import br.com.brm.scp.mock.api.service.status.PlanejamentoSku;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class SkuServiceMockTest extends SkuDadosFake {

	@Autowired
	private SkuService service;

	private static final boolean CREATION_SKU = true;

	private static final boolean SELECAO_ORIGENS = true;
	
	private static final boolean REABASTECIMENTO = true;

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
	public void criarSkuPart1(ItemResponseDTO item, Collection<TagResponseDTO> tags) throws SkuException {
		// SELECAO DO ITEM
		assertNotNull(item);
		assertNotNull(tags);

		skuRequestSuccess.setItem(item);
		skuRequestSuccess.setTags(tags);

		SkuResponseDTO response = service.create(skuRequestSuccess);

		Long idSaved = response.getId();
		
		assertNotNull(response);
		assertTrue(idSaved != 0 && idSaved != null);
		
		skuRequestSuccess.setId(idSaved);
		skuRequestSuccess.setStatus(response.getStatus());
		
	}

	/**
	 * Apos a criacao da sku os campos tornam desabilitados para o preenchimento
	 * 
	 * @throws SkuNotFoundException
	 * @throws UsuarioNotFoundException
	 * @throws SkuExistenteException 
	 */
	@Test(enabled = CREATION_SKU, groups = "CRIACAO_SKU", priority = 2)
	public void criarSkuPart2() throws SkuException, UsuarioNotFoundException {
		// PREENCHE OS CAMPOS
		String descricao = "Descricao da sku de teste!";
		
		skuRequestSuccess.setAutomatica(Boolean.TRUE);
		skuRequestSuccess.setCustoUnitario(BigDecimal.ZERO);
		skuRequestSuccess.setDataDescontinuacao(Calendar.getInstance());
		skuRequestSuccess.setDataMaturidade(Calendar.getInstance());
		skuRequestSuccess.setDescricao(descricao);
		skuRequestSuccess.setEstoqueAtual(0);
		skuRequestSuccess.setEstoqueIdeal(0);
		skuRequestSuccess.setEstoqueMaximo(0);
		skuRequestSuccess.setEstoqueMinimo(0);
		skuRequestSuccess.setFrequenciaAnalise(new Integer[] { Calendar.MONDAY, Calendar.WEDNESDAY });
		skuRequestSuccess.setLoteReposicao(100);
		skuRequestSuccess.setLoteReposicaoHistorico(0);
		skuRequestSuccess.setModelo(PlanejamentoSku.ESTOQUE);

		skuRequestSuccess.setDataCriacao(Calendar.getInstance());
		skuRequestSuccess.setUsuarioCriacao(usuarioService.findById(USUARIO_LOGADO_FAKE));

		SkuResponseDTO response = service.ativar(skuRequestSuccess);

		assertNotNull(response);
		assertTrue(descricao.equals(mockDb.getSkuCollection().get(skuRequestSuccess.getId()).getDescricao()));
		
		skuRequestSuccess.setId(response.getId());
		skuRequestSuccess.setStatus(response.getStatus());
		
	}

	/**
	 * Teste para a excecao SkuNotFoundException
	 * 
	 * @param notFound
	 * @throws SkuException 
	 */
	@Test(enabled = CREATION_SKU, expectedExceptions = SkuNotFoundException.class, groups = "CRIACAO_SKU", priority = 3, dataProvider = "skuNotFound")
	public void skuAtivarSkuNotFoundException(SkuRequestDTO notFound) throws SkuException {
		service.ativar(notFound);
	}

	/**n
	 * Teste para a excecao SkuExistenteException
	 * @throws SkuException 
	 */
	@Test(enabled = CREATION_SKU, expectedExceptions = SkuExistenteException.class, groups = "CRIACAO_SKU", priority = 4)
	public void skuExistenteException() throws SkuException {
		SkuResponseDTO response = service.create(skuRequestSuccess);
		assertNotNull(response);

	}

	@Test(enabled = SELECAO_ORIGENS, groups = "SELECIONAR_ORIGENS", dataProvider="TAGS_RANDOM", priority = 5)
	public void selecionarOrigens(Collection<TagResponseDTO> tags) throws Exception {
		
		// TODO SELECIONA AS ORIGENS DESSA SKU
		Collection<SkuResponseDTO> selecaoSkus = service.findForOrigin(skuRequestSuccess.getId());
		skuRequestSuccess.setOrigins(selecaoSkus);
		
		// CRIANDO UM NOVA SKU PARA RELACIONAR A ORIGEM - STEP 1
		SkuResponseDTO response = service.alterar(skuRequestSuccess);
		assertNotNull(response);
		
		SkuRequestDTO children = new SkuRequestDTO();
		children.setItem(response.getItem());
		children.setTags(tags);

		SkuResponseDTO responseChildren = service.create(children);
		children.setId(responseChildren.getId());
		assertNotNull(responseChildren);
		assertTrue(responseChildren.getId() != 0 && responseChildren.getId() != null);
		
		// CRIANDO UM NOVA SKU PARA RELACIONAR A ORIGEM - STEP 2
		String descricao = "Descricao da sku de teste de FILHO (ORIGEM)!";
		
		children.setAutomatica(Boolean.TRUE);
		children.setCustoUnitario(BigDecimal.ZERO);
		children.setDataDescontinuacao(Calendar.getInstance());
		children.setDataMaturidade(Calendar.getInstance());
		children.setDescricao(descricao);
		children.setEstoqueAtual(0);
		children.setEstoqueIdeal(0);
		children.setEstoqueMaximo(0);
		children.setEstoqueMinimo(0);
		children.setFrequenciaAnalise(new Integer[] { Calendar.SATURDAY, Calendar.WEDNESDAY });
		children.setLoteReposicao(1220);
		children.setLoteReposicaoHistorico(0);
		children.setModelo(PlanejamentoSku.ESTOQUE);

		children.setDataCriacao(Calendar.getInstance());
		children.setUsuarioCriacao(usuarioService.findById(USUARIO_LOGADO_FAKE));
		selecaoSkus = service.findForOrigin(children.getId());
		children.setOrigins(selecaoSkus);
		
		// TODO SELECIONA UMA ORIGEM DEFAULT
		for(SkuResponseDTO r : selecaoSkus ){
			if( r.getId().equals(skuRequestSuccess.getId()) ){
				children.setOriginDefault(r);
			}
		}

		SkuResponseDTO responseStep2 = service.ativar(children);

		assertNotNull(responseStep2);
		assertTrue(descricao.equals(mockDb.getSkuCollection().get(children.getId()).getDescricao()));
		assertTrue(mockDb.getSkuCollection().get(children.getId()).getOriginDefault().getId().equals(skuRequestSuccess.getId()));
		assertNull(mockDb.getSkuCollection().get(skuRequestSuccess.getId()).getOriginDefault());
		
	}
	
	@Test(enabled = REABASTECIMENTO, groups = "REABASTECIMENTO", priority = 6)
	public void reabastecimento() throws Exception {
		service.reabastecimento(skuRequestSuccess, usuarioService.findById(USUARIO_LOGADO_FAKE));
		
		assertTrue(mockDb.getPedidoCollection().values().size() > 0);
		
		for(PedidoDocument document : mockDb.getPedidoCollection().values()){
			logger.info(String.format("Pedido encontrado para a SKU %s", document.getIdSku()));
		}
		
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
	 * Seleccao de Item e Tag automatizado
	 * 
	 * @return
	 * @throws UsuarioNotFoundException
	 */
	@DataProvider(name = "TAGS_RANDOM")
	public Object[][] criaTagsRandom(){

		// ADIÇÃO DAS TAGS - CLICA EM OK
		Collection<TagResponseDTO> selecionadas = new ArrayList<TagResponseDTO>();
		selecionadas = selecionarRandomTagByNivel(1, selecionadas);
		selecionadas = selecionarRandomTagByNivel(2, selecionadas);
		selecionadas = selecionarRandomTagByNivel(3, selecionadas);

		return new Object[][] {
				new Object[] { selecionadas } };
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

}
