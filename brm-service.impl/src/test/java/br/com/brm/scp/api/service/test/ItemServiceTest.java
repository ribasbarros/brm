package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.dto.response.CategoriaResponseDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemCategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.document.CategoriaDocument;
import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.repositories.CategoriaRepository;
import br.com.brm.scp.api.service.repositories.ItemRepository;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.mock.api.service.status.ItemStatus;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class ItemServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ItemService service;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	private static final boolean TEST_CRUD = true;

	private static final String ID_CATEGORIA_NOTFOUND = "NOTFOUND";

	private CategoriaResponseDTO categoria4Test;

	private String idTesting;
	private String idDeleting;

	@BeforeClass
	public void setup() throws Exception {

		CategoriaDocument categoria01 = new CategoriaDocument();
		categoria01.setNome("Categoria001");

		categoria01 = categoriaRepository.save(categoria01);

		setCategoria4Test((CategoriaResponseDTO) ConverterHelper.convert(categoria01, CategoriaResponseDTO.class));

	}

	@AfterClass
	public void tearDown() throws Exception {
		categoriaRepository.delete(getCategoria4Test().getId());
		itemRepository.delete(idTesting);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "ItemRequestSuccess")
	public void testCreate(ItemRequestDTO request) throws ItemExistenteException, ItemCategoriaNotFoundException {
		ItemResponseDTO response = service.create(request);

		idTesting = response.getId();
		
		assertNotNull(response);
		assertNotNull(idTesting);

		ItemDocument document = itemRepository.findOne(response.getId());

		assertNotNull(document);
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "ItemRequestSuccess", expectedExceptions=ItemExistenteException.class)
	public void testCreateItemExistenteException(ItemRequestDTO request) throws ItemExistenteException, ItemCategoriaNotFoundException {
		service.create(request);
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "ItemRequestNotCategoria", expectedExceptions=ItemCategoriaNotFoundException.class)
	public void testCreateItemCategoriaNotFoundException(ItemRequestDTO request) throws ItemExistenteException, ItemCategoriaNotFoundException {
		service.create(request);
	}
	
	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 4, dataProvider = "ItemRequestSuccess")
	public void testUpdate(ItemRequestDTO request) throws ItemExistenteException, ItemCategoriaNotFoundException {
		
		String oldValue = request.getNome();
		String newValue = String.format("%s-Update!-%s", oldValue, new Date());
		
		request.setNome(newValue);
		
		assertFalse(oldValue.equals(newValue));
		
		ItemResponseDTO response = service.update(request);
		
		assertNotNull(response);
		assertTrue(newValue.equals(response.getNome()));
	}
	
	@DataProvider(name = "ItemRequestNotCategoria")
	public Object[][] providerItemRequestNotCategoria() {
		ItemRequestDTO request = new ItemRequestDTO();
		request.setId(null);
		request.setIdCategoria(ID_CATEGORIA_NOTFOUND);
		request.setNome("Item0002");
		request.setNomeReduzido("Item02");
		request.setStatus(ItemStatus.ATIVO);
		request.setUnitizacao(6);
		request.setValorUnitario(new BigDecimal(0.20));

		return new Object[][] { new Object[] { request } };
	}

	@DataProvider(name = "ItemRequestSuccess")
	public Object[][] providerItemRequest() {
		ItemRequestDTO request = doItem();
		
		return new Object[][] { new Object[] { request } };
	}

	private ItemRequestDTO doItem() {
		ItemRequestDTO request = new ItemRequestDTO();
		request.setId(idTesting);
		request.setIdCategoria(getCategoria4Test().getId());
		request.setNome("Item0001");
		request.setNomeReduzido("Item01");
		request.setStatus(ItemStatus.ATIVO);
		request.setUnitizacao(10);
		request.setValorUnitario(new BigDecimal(1.90));
		return request;
	}
	
	public CategoriaResponseDTO getCategoria4Test() {
		return categoria4Test;
	}

	public void setCategoria4Test(CategoriaResponseDTO categoria4Test) {
		this.categoria4Test = categoria4Test;
	}

	public String getIdTesting() {
		return idTesting;
	}

	public void setIdTesting(String idTesting) {
		this.idTesting = idTesting;
	}

	public String getIdDeleting() {
		return idDeleting;
	}

	public void setIdDeleting(String idDeleting) {
		this.idDeleting = idDeleting;
	}

}
