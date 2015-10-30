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
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.exceptions.ItemCategoriaNotFoundException;
import br.com.brm.scp.api.exceptions.ItemExistenteException;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.document.CategoriaDocument;
import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.repositories.CategoriaRepository;
import br.com.brm.scp.api.service.repositories.ItemRepository;
import br.com.brm.scp.api.service.status.ItemFiltroEnum;
import br.com.brm.scp.api.service.status.ItemStatus;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
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

	private static final String ID_ITEM_NOTFOUND = "ID_NOT_FOUND";

	private static final boolean DELETE_CATEGORIA_TEST = false;

	private static final boolean DELETE_ITEM_TEST = false;

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
		
		if (DELETE_CATEGORIA_TEST) categoriaRepository.delete(getCategoria4Test().getId());

		if (DELETE_ITEM_TEST) itemRepository.delete(idTesting);
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

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "ItemRequestSuccess", expectedExceptions = ItemExistenteException.class)
	public void testCreateItemExistenteException(ItemRequestDTO request)
			throws ItemExistenteException, ItemCategoriaNotFoundException {
		service.create(request);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "ItemRequestNotCategoria", expectedExceptions = ItemCategoriaNotFoundException.class)
	public void testCreateItemCategoriaNotFoundException(ItemRequestDTO request)
			throws ItemExistenteException, ItemCategoriaNotFoundException {
		service.create(request);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 4, dataProvider = "ItemRequestSuccess")
	public void testUpdate(ItemRequestDTO request) throws ItemNotFoundException, ItemCategoriaNotFoundException {

		String oldValue = request.getDescricao();
		String newValue = String.format("%s-Update!-%s", oldValue, new Date());

		request.setDescricao(newValue);

		assertFalse(oldValue.equals(newValue));

		ItemResponseDTO response = service.update(request);

		assertNotNull(response);
		assertTrue(newValue.equals(response.getDescricao()));
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 5, dataProvider = "ItemRequestNotFound", expectedExceptions = ItemNotFoundException.class)
	public void testUpdateItemNotFoundException(ItemRequestDTO request)
			throws ItemNotFoundException, ItemCategoriaNotFoundException {
		service.update(request);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 6, dataProvider = "ItemRequestSuccess", expectedExceptions = ItemCategoriaNotFoundException.class)
	public void testUpdateItemCategoriaNotFoundException(ItemRequestDTO request)
			throws ItemNotFoundException, ItemCategoriaNotFoundException {

		CategoriaResponseDTO notFound = new CategoriaResponseDTO();
		notFound.setId(ID_CATEGORIA_NOTFOUND);
		notFound.setNome("NOT FOUND");

		request.setCategoria(notFound);
		service.update(request);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 7, dataProvider = "ItemRequestSuccess")
	public void testFind(ItemRequestDTO request) throws ItemNotFoundException, ItemCategoriaNotFoundException {

		ItemResponseDTO response01 = service.find(ItemFiltroEnum.ID, request.getId());

		assertNotNull(response01);
		assertNotNull(response01.getCategoria());

		ItemResponseDTO response02 = service.find(ItemFiltroEnum.NOME, request.getNome());

		assertNotNull(response02);
		assertNotNull(response02.getCategoria());
		
		ItemResponseDTO response03 = service.find(ItemFiltroEnum.NOME_REDUZIDO, request.getNomeReduzido());

		assertNotNull(response03);
		assertNotNull(response03.getCategoria());

	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 8, dataProvider = "ItemRequestNotFound", expectedExceptions = ItemNotFoundException.class)
	public void testFindIDItemNotFoundException(ItemRequestDTO request)
			throws ItemNotFoundException, ItemCategoriaNotFoundException {
		service.find(ItemFiltroEnum.ID, request.getId());
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 9, dataProvider = "ItemRequestNotFound", expectedExceptions = ItemNotFoundException.class)
	public void testFindNOMEItemNotFoundException(ItemRequestDTO request)
			throws ItemNotFoundException, ItemCategoriaNotFoundException {
		service.find(ItemFiltroEnum.NOME, request.getNome());
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 10, dataProvider = "ItemRequestNotFound", expectedExceptions = ItemNotFoundException.class)
	public void testFindNOMEREDUZIDOItemNotFoundException(ItemRequestDTO request)
			throws ItemNotFoundException, ItemCategoriaNotFoundException {
		service.find(ItemFiltroEnum.NOME_REDUZIDO, request.getNomeReduzido());
	}

	@Test(enabled = TEST_CRUD && DELETE_ITEM_TEST, groups = "CRUD", priority = 11, dataProvider = "ItemRequestSuccess")
	public void testDeleleItem(ItemRequestDTO request) throws ItemNotFoundException, ItemCategoriaNotFoundException {
		service.delete(request.getId());
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 12, dataProvider = "ItemRequestNotFound", expectedExceptions = ItemNotFoundException.class)
	public void testDeleleItemNotFoundException(ItemRequestDTO request) throws ItemNotFoundException {
		service.delete(request.getId());
	}

	@DataProvider(name = "ItemRequestNotCategoria")
	public Object[][] providerItemRequestNotCategoria() {

		CategoriaResponseDTO notFound = new CategoriaResponseDTO();
		notFound.setId(ID_CATEGORIA_NOTFOUND);
		notFound.setNome("NOT FOUND");

		ItemRequestDTO request = new ItemRequestDTO();
		request.setCategoria(notFound);
		request.setNome("Item0002");
		request.setNomeReduzido("Item02");
		request.setDescricao("Item 02 descricao teste");
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

	@DataProvider(name = "ItemRequestNotFound")
	public Object[][] providerItemRequestNotFound() {
		ItemRequestDTO request = new ItemRequestDTO();
		request.setId(ID_ITEM_NOTFOUND);
		request.setCategoria(getCategoria4Test());
		request.setNome("NOTFOUND");
		request.setNomeReduzido("NOTFOUND");
		request.setDescricao("NOTFOUND descricao.");
		request.setStatus(ItemStatus.ATIVO);
		request.setUnitizacao(5);
		request.setValorUnitario(new BigDecimal(18.89));

		return new Object[][] { new Object[] { request } };
	}

	private ItemRequestDTO doItem() {
		ItemRequestDTO request = new ItemRequestDTO();
		request.setId(idTesting);
		request.setCategoria(getCategoria4Test());
		request.setNome("Item0001");
		request.setNomeReduzido("Item01");
		request.setDescricao("Item descricao teste.");
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
