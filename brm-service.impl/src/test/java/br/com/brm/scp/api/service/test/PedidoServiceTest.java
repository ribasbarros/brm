package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.exceptions.PedidoOrigemNotFoundException;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.document.OrigemSkuDocument;
import br.com.brm.scp.api.service.document.PedidoDocument;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.repositories.PedidoRepository;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.api.service.status.ClasseEnum;
import br.com.brm.scp.api.service.status.OrigemTipoEnum;
import br.com.brm.scp.api.service.status.PlanejamentoSku;
import br.com.brm.scp.api.service.status.StatusReposicaoEnum;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class PedidoServiceTest extends CargaTestSku{

	private static final boolean ENABLE_TEST = true;

	private static final boolean EXCLUIR_TESTS = true;

	private static final Date DATE_TEST = new Date();

	private static final Double PRECO_UNITARIO = 102.99;

	@Autowired
	private PedidoService service;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private SkuRepository skuRepository;
	
	private String idSkuSuccess1;
	private String idSkuSuccess2;
	private String idSkuSuccess3;


	@BeforeClass
	public void setup() throws Exception {
		createItem4Test();
		createTags4Test();
		createFornecedor4Test();
		createSkuCD4Test();
		createSkuONE4Test();
		createSkuTWO4Test();
	}

	@AfterClass
	public void tearDown() throws Exception {
		if(EXCLUIR_TESTS)
			deleteAllMassTests();
	}

	@Test(enabled = ENABLE_TEST, groups = "CRUD", priority = 1, dataProvider = "CreateSimpleRequest")
	public void testCreate(String sku, int quantidade, String descricao) throws PedidoOrigemNotFoundException{
		
		SkuDocument skuDocument = skuRepository.findOne(sku);
		
		PedidoResponseDTO response = service.request(skuDocument.getId(), quantidade, descricao);
		assertNotNull(response);
		assertNotNull(response.getId());
		
		PedidoDocument document = pedidoRepository.findOne(response.getId());
		assertNotNull(document);
	}
	
	
	@DataProvider(name = "CreateSimpleRequest")
	public Object[][] providerItemRequestNotCategoria() {
		return new Object[][] { new Object[] { idSkuSuccess1, 100, "Descricao do pedido" } };
	}

	private void deleteAllMassTests() {
		try {
			itemRepository.delete(item.getId());
		} catch (Exception ex) {
		}
		try {
			categoriaRepository.delete(item.getCategoria().getId());
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
	
	private void createSkuCD4Test() {
		SkuDocument document = new SkuDocument();

		document.setItem(item);
		document.setTags(new ArrayList<>(Arrays.asList(tag1)));
		document.setDataMaturidade(DATE_TEST);
		document.setDataDescontinuacao(DATE_TEST);
		document.setModelo(PlanejamentoSku.ESTOQUE);
		document.setFrequenciaAnalise(new Integer[] { Calendar.MONDAY });
		document.setAutomatica(Boolean.TRUE);
		document.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		document.setDescricao("Sku 0 teste de modelo de dados (CD)");
		document.setEstoqueMaximo(0);
		document.setEstoqueMinimo(0);
		document.setEstoqueIdeal(0);
		document.setEstoqueAtual(0);
		document.setCustoUnitario(new BigDecimal(PRECO_UNITARIO));
		document.setClasse(ClasseEnum.A);
		
		document.setDataCriacao(DATE_TEST);
		document.setDataAlteracao(DATE_TEST);

		OrigemSkuDocument origem = createOrigem4Test(OrigemTipoEnum.FORNECEDOR, fornecedor.getId());

		document.setOrigens(new ArrayList<>(Arrays.asList(origem)));
		
		document = skuRepository.save(document);
		idSkuSuccess1 = document.getId();
		
	}
	
	private void createSkuONE4Test() {
		SkuDocument document = new SkuDocument();

		document.setItem(item);
		document.setTags(new ArrayList<>(Arrays.asList(tag1)));
		document.setDataMaturidade(DATE_TEST);
		document.setDataDescontinuacao(DATE_TEST);
		document.setModelo(PlanejamentoSku.ESTOQUE);
		document.setFrequenciaAnalise(new Integer[] { Calendar.MONDAY });
		document.setAutomatica(Boolean.TRUE);
		document.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		document.setDescricao("Sku 1 teste de modelo de dados (SP)");
		document.setEstoqueMaximo(0);
		document.setEstoqueMinimo(0);
		document.setEstoqueIdeal(0);
		document.setEstoqueAtual(0);
		document.setCustoUnitario(new BigDecimal(PRECO_UNITARIO));
		document.setClasse(ClasseEnum.A);
		
		document.setDataCriacao(DATE_TEST);
		document.setDataAlteracao(DATE_TEST);

		OrigemSkuDocument origem = createOrigem4Test(OrigemTipoEnum.SKU, idSkuSuccess1);

		document.setOrigens(new ArrayList<>(Arrays.asList(origem)));
		
		document = skuRepository.save(document);
		idSkuSuccess2 = document.getId();
		
	}
	
	private void createSkuTWO4Test() {
		SkuDocument document = new SkuDocument();

		document.setItem(item);
		document.setTags(new ArrayList<>(Arrays.asList(tag1)));
		document.setDataMaturidade(DATE_TEST);
		document.setDataDescontinuacao(DATE_TEST);
		document.setModelo(PlanejamentoSku.ESTOQUE);
		document.setFrequenciaAnalise(new Integer[] { Calendar.MONDAY });
		document.setAutomatica(Boolean.TRUE);
		document.setStatus(StatusReposicaoEnum.DESBLOQUEADA);
		document.setDescricao("Sku 2 teste de modelo de dados (SP)");
		document.setEstoqueMaximo(0);
		document.setEstoqueMinimo(0);
		document.setEstoqueIdeal(0);
		document.setEstoqueAtual(0);
		document.setCustoUnitario(new BigDecimal(PRECO_UNITARIO));
		document.setClasse(ClasseEnum.A);
		
		document.setDataCriacao(DATE_TEST);
		document.setDataAlteracao(DATE_TEST);

		OrigemSkuDocument origem = createOrigem4Test(OrigemTipoEnum.SKU, idSkuSuccess2);

		document.setOrigens(new ArrayList<>(Arrays.asList(origem)));
		
		document = skuRepository.save(document);
		idSkuSuccess3 = document.getId();
		
	}
	
	private OrigemSkuDocument createOrigem4Test(OrigemTipoEnum tipo, String id) {
		OrigemSkuDocument origem = new OrigemSkuDocument();
		origem.setTipo(tipo);
		origem.setId(id);
		origem.setPadrao(Boolean.TRUE);
		return origem;
	}
	
}
