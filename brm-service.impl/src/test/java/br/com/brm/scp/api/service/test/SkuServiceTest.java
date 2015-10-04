package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.OrigemSkuResponseDTO;
import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.SkuExistenteException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.status.ClasseEnum;
import br.com.brm.scp.mock.api.service.status.OrigemTipoEnum;
import br.com.brm.scp.mock.api.service.status.PlanejamentoSku;
import br.com.brm.scp.mock.api.service.status.StatusReposicaoEnum;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class SkuServiceTest extends CargaTestSku {

	private static final double PRECO_UNITARIO = 9.6;

	private static final boolean TEST_CRUD = true;
	
	private static final boolean EXCLUIR_TESTS = true;

	private static final Date DATE_TEST = new Date();

	private String idSkuSuccess1;
	private String idSkuSuccess2;
	private String idSkuSuccess3;

	@Autowired
	private SkuRepository skuRepository;

	@Autowired
	private SkuService service;

	@BeforeClass
	public void setup() throws Exception {
		createItem4Test();
		createTags4Test();
		createFornecedor4Test();
	}

	@AfterClass
	public void tearDown() {
		if(EXCLUIR_TESTS)
			deleteAllMassTests();
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "Sku1RequestSuccess")
	public void testCreateCDBrasil(SkuRequestDTO request) throws SkuExistenteException {
		SkuResponseDTO response = service.create(request);
		idSkuSuccess1 = response.getId();

		assertNotNull(response);
		assertNotNull(idSkuSuccess1);

		SkuDocument document = skuRepository.findOne(idSkuSuccess1);

		assertNotNull(document);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "Sku2RequestSuccess")
	public void testCreateSkuSP(SkuRequestDTO request) throws SkuExistenteException {
		SkuResponseDTO response = service.create(request);
		idSkuSuccess2 = response.getId();

		assertNotNull(response);
		assertNotNull(idSkuSuccess2);

		SkuDocument document = skuRepository.findOne(idSkuSuccess2);

		assertNotNull(document);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "Sku3RequestSuccess")
	public void testCreateSkuRJ(SkuRequestDTO request) throws SkuExistenteException {
		SkuResponseDTO response = service.create(request);
		idSkuSuccess3 = response.getId();

		assertNotNull(response);
		assertNotNull(idSkuSuccess3);

		SkuDocument document = skuRepository.findOne(idSkuSuccess3);

		assertNotNull(document);
	}

	// TODO Desenvolver
	@Test(enabled = TEST_CRUD && false, groups = "CRUD", priority = 4)
	public void testFindPaginado() throws SkuNotFoundException {

	}

	//TODO Desenvolver
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 5)
	public void testFind() throws SkuNotFoundException {

		Collection<SkuResponseDTO> search = service.search(item.getNome());
		
		assertTrue(!search.isEmpty());

	}

	@DataProvider(name = "Sku1RequestSuccess")
	private Object[][] providerSku1() {

		SkuRequestDTO request = new SkuRequestDTO();

		request.setItem((ItemResponseDTO) ConverterHelper.convert(item, ItemResponseDTO.class));
		request.setTags(
				new ArrayList<>(Arrays.asList((TagResponseDTO) ConverterHelper.convert(tag1, TagResponseDTO.class))));
		request.setDataMaturidade(DATE_TEST);
		request.setDataDescontinuacao(DATE_TEST);
		request.setModelo(PlanejamentoSku.ESTOQUE);
		request.setFrequenciaAnalise(new Integer[] { Calendar.MONDAY });
		request.setAutomatica(Boolean.TRUE);
		request.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		request.setDescricao("Sku 1 teste de modelo de dados (CD)");
		request.setEstoqueMaximo(0);
		request.setEstoqueMinimo(0);
		request.setEstoqueIdeal(0);
		request.setEstoqueAtual(0);
		request.setCustoUnitario(new BigDecimal(PRECO_UNITARIO));
		request.setClasse(ClasseEnum.A);
		
		request.setDataCriacao(DATE_TEST);
		request.setDataAlteracao(DATE_TEST);

		OrigemSkuResponseDTO origem = createOrigem4Test(OrigemTipoEnum.FORNECEDOR, fornecedor.getId());

		request.setOrigens(new ArrayList<>(Arrays.asList(origem)));

		return new Object[][] { new Object[] { request } };
	}

	@DataProvider(name = "Sku2RequestSuccess")
	private Object[][] providerSku2() {

		SkuRequestDTO request = new SkuRequestDTO();

		request.setItem((ItemResponseDTO) ConverterHelper.convert(item, ItemResponseDTO.class));
		request.setTags(
				new ArrayList<>(Arrays.asList((TagResponseDTO) ConverterHelper.convert(tag1, TagResponseDTO.class),
						(TagResponseDTO) ConverterHelper.convert(tag2, TagResponseDTO.class))));
		request.setDataMaturidade(DATE_TEST);
		request.setDataDescontinuacao(DATE_TEST);
		request.setModelo(PlanejamentoSku.ESTOQUE);
		request.setFrequenciaAnalise(new Integer[] { Calendar.MONDAY });
		request.setAutomatica(Boolean.TRUE);
		request.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		request.setDescricao("Sku 2 teste de modelo de dados");
		request.setEstoqueMaximo(0);
		request.setEstoqueMinimo(0);
		request.setEstoqueIdeal(0);
		request.setEstoqueAtual(0);
		request.setCustoUnitario(new BigDecimal(PRECO_UNITARIO));
		request.setClasse(ClasseEnum.A);
		
		request.setDataCriacao(DATE_TEST);
		request.setDataAlteracao(DATE_TEST);

		OrigemSkuResponseDTO origem = createOrigem4Test(OrigemTipoEnum.SKU, idSkuSuccess1);

		request.setOrigens(new ArrayList<>(Arrays.asList(origem)));

		return new Object[][] { new Object[] { request } };
	}

	@DataProvider(name = "Sku3RequestSuccess")
	private Object[][] providerSku3() {

		SkuRequestDTO request = new SkuRequestDTO();

		request.setItem((ItemResponseDTO) ConverterHelper.convert(item, ItemResponseDTO.class));
		request.setTags(
				new ArrayList<>(Arrays.asList((TagResponseDTO) ConverterHelper.convert(tag1, TagResponseDTO.class),
						(TagResponseDTO) ConverterHelper.convert(tag3, TagResponseDTO.class))));
		request.setDataMaturidade(DATE_TEST);
		request.setDataDescontinuacao(DATE_TEST);
		request.setModelo(PlanejamentoSku.ESTOQUE);
		request.setFrequenciaAnalise(new Integer[] { Calendar.MONDAY });
		request.setAutomatica(Boolean.TRUE);
		request.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		request.setDescricao("Sku 3 teste de modelo de dados");
		request.setEstoqueMaximo(0);
		request.setEstoqueMinimo(0);
		request.setEstoqueIdeal(0);
		request.setEstoqueAtual(0);
		request.setCustoUnitario(new BigDecimal(PRECO_UNITARIO));
		request.setClasse(ClasseEnum.A);

		request.setDataCriacao(DATE_TEST);
		request.setDataAlteracao(DATE_TEST);

		OrigemSkuResponseDTO origem = createOrigem4Test(OrigemTipoEnum.SKU, idSkuSuccess1);

		request.setOrigens(new ArrayList<>(Arrays.asList(origem)));

		return new Object[][] { new Object[] { request } };
	}

	private OrigemSkuResponseDTO createOrigem4Test(OrigemTipoEnum tipo, String id) {
		OrigemSkuResponseDTO origem = new OrigemSkuResponseDTO();
		origem.setTipo(tipo);
		origem.setId(id);
		origem.setIsDefault(Boolean.TRUE);
		return origem;
	}

	private void deleteAllMassTests() {
		try {
			itemRepository.delete(item.getId());
		} catch (Exception ex) {
		}
		try {
			categoriaRepository.delete(item.getIdCategoria());
		} catch (Exception ex) {
		}
		try {
			tagRepository.delete(tag1);
		} catch (Exception ex) {
		}
		try {
			tagRepository.delete(tag2);
		} catch (Exception ex) {
		}
		try {
			tagRepository.delete(tag3);
		} catch (Exception ex) {
		}
		try {
			fornecedorRepository.delete(fornecedor.getId());
		} catch (Exception ex) {
		}
		try {
			skuRepository.delete(idSkuSuccess1);
		} catch (Exception ex) {
		}
		try {
			skuRepository.delete(idSkuSuccess2);
		} catch (Exception ex) {
		}
		try {
			skuRepository.delete(idSkuSuccess3);
		} catch (Exception ex) {
		}
	}

}
