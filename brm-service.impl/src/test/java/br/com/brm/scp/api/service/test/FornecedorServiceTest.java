package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.FornecedorCentroDTO;
import br.com.brm.scp.api.dto.request.FornecedorRequestDTO;
import br.com.brm.scp.api.dto.response.FornecedorResponseDTO;
import br.com.brm.scp.api.exceptions.FornecedorCentroExistenteException;
import br.com.brm.scp.api.exceptions.FornecedorException;
import br.com.brm.scp.api.exceptions.FornecedorExistenteException;
import br.com.brm.scp.api.exceptions.FornecedorNotFoundException;
import br.com.brm.scp.api.pages.Pageable;
import br.com.brm.scp.api.service.FornecedorService;
import br.com.brm.scp.api.service.document.FornecedorCentroDocument;
import br.com.brm.scp.api.service.document.FornecedorDocument;
import br.com.brm.scp.api.service.repositories.FornecedorRepository;
import br.com.brm.scp.api.service.status.FornecedorFiltroEnum;
import br.com.brm.scp.api.service.test.helper.GeraCpfCnpj;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class FornecedorServiceTest extends AbstractTestNGSpringContextTests {

	private static final String REGEX_NOCHAR_CNPJ = "[-.//]";
	private static final boolean TEST_CRUD = true;
	private static final boolean DISABLED = true;
	private static final String ID_INVALIDO = "89898989_INVALIDO_888888";

	private static final boolean DELETED_TEST = true;

	private static final String CNPJ_4CENTRO = GeraCpfCnpj.cnpj().replaceAll(REGEX_NOCHAR_CNPJ, "");
	private static final String CNPJ_4NOTFOUND = GeraCpfCnpj.cnpj().replaceAll(REGEX_NOCHAR_CNPJ, "");
	private static final String CNPJ_4DELETING = GeraCpfCnpj.cnpj().replaceAll(REGEX_NOCHAR_CNPJ, "");
	private static final String CNPJ_4SUCCESS = GeraCpfCnpj.cnpj().replaceAll(REGEX_NOCHAR_CNPJ, "");

	private Collection<FornecedorDocument> fornecedor4Deleted = new ArrayList<>();

	private String idTesting;
	private String idDeleting;

	@Autowired
	private FornecedorService service;

	@Autowired
	private FornecedorRepository fRepo;

	@BeforeClass
	public void setup() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
		if (DELETED_TEST) {
			fRepo.delete(idTesting);
			fRepo.delete(idDeleting);
			fRepo.delete(fornecedor4Deleted);
		}
	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 1, dataProvider = "RequestSuccess")
	public void testCreate(FornecedorRequestDTO request) throws FornecedorExistenteException {
		FornecedorResponseDTO response = service.create(request);

		idTesting = response.getId();

		assertNotNull(response);
		assertNotNull(idTesting);

		FornecedorDocument document = fRepo.findOne(response.getId());

		assertNotNull(document);

	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 2, expectedExceptions = FornecedorExistenteException.class, dataProvider = "RequestSuccess")
	public void testCreateFornecedorExistente(FornecedorRequestDTO request) throws FornecedorExistenteException {
		service.create(request);
	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 3, dataProvider = "RequestSuccess")
	public void testUpdate(FornecedorRequestDTO request) throws FornecedorNotFoundException {

		String oldValue = request.getDescricao();
		String newValue = String.format("%s-Update!-%s", oldValue, new Date());

		request.setDescricao(newValue);

		assertFalse(oldValue.equals(newValue));

		FornecedorResponseDTO response = service.update(request);

		assertNotNull(response);
		assertTrue(newValue.equals(response.getDescricao()));

	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 4, expectedExceptions = FornecedorNotFoundException.class, dataProvider = "RequestNotFound")
	public void testUpdateFornecedorNotFoundException(FornecedorRequestDTO request) throws FornecedorNotFoundException {
		service.update(request);
	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 5, dataProvider = "RequestSuccess")
	public void testFind(FornecedorRequestDTO request) throws FornecedorNotFoundException {

		FornecedorResponseDTO response01 = service.find(FornecedorFiltroEnum.CNPJ, request.getCnpj());

		assertNotNull(response01);

		FornecedorResponseDTO response02 = service.find(FornecedorFiltroEnum.RAZAO_SOCIAL, request.getRazaoSocial());

		assertNotNull(response02);

		FornecedorResponseDTO response03 = service.find(FornecedorFiltroEnum.ID, request.getId());

		assertNotNull(response03);

	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 6, dataProvider = "RequestNotFound", expectedExceptions = FornecedorNotFoundException.class)
	public void testFindCNPJFornecedorNotFoundException(FornecedorRequestDTO request)
			throws FornecedorNotFoundException {
		FornecedorResponseDTO response01 = service.find(FornecedorFiltroEnum.CNPJ, request.getCnpj());
		assertNotNull(response01);
	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 7, dataProvider = "RequestNotFound", expectedExceptions = FornecedorNotFoundException.class)
	public void testFindRSFornecedorNotFoundException(FornecedorRequestDTO request) throws FornecedorNotFoundException {
		FornecedorResponseDTO response02 = service.find(FornecedorFiltroEnum.RAZAO_SOCIAL, request.getRazaoSocial());
		assertNotNull(response02);
	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 8, dataProvider = "RequestNotFound", expectedExceptions = FornecedorNotFoundException.class)
	public void testFindIDFornecedorNotFoundException(FornecedorRequestDTO request) throws FornecedorNotFoundException {
		FornecedorResponseDTO response03 = service.find(FornecedorFiltroEnum.ID, request.getId());
		assertNotNull(response03);
	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 9, dataProvider = "RequestSuccess4Delete")
	public void testDelete(FornecedorRequestDTO request) throws FornecedorNotFoundException {

		FornecedorDocument document = fRepo
				.save((FornecedorDocument) ConverterHelper.convert(request, FornecedorDocument.class));
		idDeleting = document.getId();

		service.delete(idDeleting);

		FornecedorDocument document01 = fRepo.findOne(idDeleting);

		assertNull(document01);

	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 10, dataProvider = "RequestSuccess4Delete", expectedExceptions = FornecedorNotFoundException.class)
	public void testDeleteFornecedorNotFoundException(FornecedorRequestDTO request) throws FornecedorNotFoundException {
		service.delete(request.getId());
	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 11, dataProvider = "FornecedorCentroSuccess")
	public void testAddCentro(FornecedorRequestDTO request, FornecedorCentroDTO centro) throws FornecedorException {

		service.addCentro(request.getId(), centro);

		FornecedorDocument document = fRepo.findOne(idTesting);
		assertNotNull(document);
		assertNotNull(document.getCentros());
		assertFalse(document.getCentros().isEmpty());

		FornecedorCentroDocument result = new ArrayList<>(document.getCentros()).get(0);

		assertTrue(result.getCep().equals(centro.getCep()));
		assertTrue(result.getCentro().equals(centro.getCentro()));
		assertTrue(result.getCnpj().equals(centro.getCnpj()));

	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 12, dataProvider = "FornecedorCentroSuccess", expectedExceptions = FornecedorCentroExistenteException.class)
	public void testAddCentroFornecedorCentroExistenteException(FornecedorRequestDTO request,
			FornecedorCentroDTO centro) throws FornecedorException {
		service.addCentro(request.getId(), centro);
	}

	@Test(enabled = TEST_CRUD && DISABLED, groups = "CRUD", priority = 13, dataProvider = "FornecedorNotFoundCentroSuccess", expectedExceptions = FornecedorNotFoundException.class)
	public void testAddCentroFornecedorNotFoundException(FornecedorRequestDTO request, FornecedorCentroDTO centro)
			throws FornecedorNotFoundException, FornecedorCentroExistenteException {
		service.addCentro(request.getId(), centro);
	}

	@Test(enabled = TEST_CRUD, groups = "CRUD", priority = 14)
	public void testSearchPageable() throws FornecedorNotFoundException {

		Pageable<FornecedorResponseDTO> result01 = service.search("Brasil", 0, 10);
		assertTrue(result01.getNumberOfElements() > 0);

		createPages(1, 9);
		Pageable<FornecedorResponseDTO> result02 = service.search("fornecedor", 0, 10);
		assertTrue(result02.getNumberOfElements() > 0);

		createPages(9, 100);
		Pageable<FornecedorResponseDTO> result03 = service.search("fornecedor", 0, 10);
		assertTrue(result03.getNumberOfElements() > 0);

		Pageable<FornecedorResponseDTO> result04 = service.search("fornecedor", 1, 10);
		assertTrue(result04.getNumberOfElements() > 0);

		Pageable<FornecedorResponseDTO> result05 = service.search("fornecedor", 1, 5);
		assertTrue(result05.getNumberOfElements() > 0);

	}

	private void createPages(int inicio, int quantidade) {

		for (int i = inicio; i < quantidade; i++) {
			FornecedorDocument document = new FornecedorDocument();

			document.setCnpj(GeraCpfCnpj.cnpj().replaceAll(REGEX_NOCHAR_CNPJ, ""));
			document.setDescricao(String.format("Teste Fornecedor%s (DESCRICAO)", i));
			document.setNomeFantasia(String.format("Fornecedor%s (NOME FANTASIA)", i));
			document.setRazaoSocial(String.format("Razao Social Fornecedor%s (NOME FANTASIA)", i));

			FornecedorCentroDocument centro = new FornecedorCentroDocument();
			centro.setCentro(1000);
			centro.setCep("99999-999");
			centro.setCnpj(GeraCpfCnpj.cnpj().replaceAll(REGEX_NOCHAR_CNPJ, ""));

			document.setCentros(new ArrayList<>(Arrays.asList(centro)));

			fRepo.save(document);

			fornecedor4Deleted.add(document);
		}

	}

	@DataProvider(name = "FornecedorNotFoundCentroSuccess")
	public Object[][] providerFFailFornecedorCentroSuccess() {
		FornecedorRequestDTO requestNotFound = doFornecedorNotFound();

		FornecedorCentroDTO centro = doFCentro();

		return new Object[][] { new Object[] { requestNotFound, centro } };
	}

	private FornecedorCentroDTO doFCentro() {
		FornecedorCentroDTO centro = new FornecedorCentroDTO();
		centro.setCentro(1000);
		centro.setCep("01245-090");
		centro.setCnpj(CNPJ_4CENTRO);
		return centro;
	}

	@DataProvider(name = "FornecedorCentroSuccess")
	public Object[][] providerFFornecedorCentroSuccess() {
		FornecedorRequestDTO request = doFornecedor();

		FornecedorCentroDTO centro = doFCentro();

		return new Object[][] { new Object[] { request, centro } };
	}

	@DataProvider(name = "RequestSuccess")
	public Object[][] providerFornecedor() {
		FornecedorRequestDTO request = doFornecedor();

		return new Object[][] { new Object[] { request } };
	}

	private FornecedorRequestDTO doFornecedor() {
		FornecedorRequestDTO request = new FornecedorRequestDTO();

		request.setId(idTesting);
		request.setCnpj(CNPJ_4SUCCESS);
		request.setDescricao("Google");
		request.setNomeFantasia("Google");
		request.setRazaoSocial("Google Brasil Internet Ltda");
		return request;
	}

	@DataProvider(name = "RequestSuccess4Delete")
	public Object[][] providerFornecedor4Delete() {
		FornecedorRequestDTO request = new FornecedorRequestDTO();

		request.setId(idDeleting);
		request.setCnpj(CNPJ_4DELETING);
		request.setDescricao("Microsoft Informatica Ltda");
		request.setNomeFantasia("Microsoft");
		request.setRazaoSocial("Microsoft Informatica Ltda de Sao Paulo");

		return new Object[][] { new Object[] { request } };
	}

	@DataProvider(name = "RequestNotFound")
	public Object[][] providerFornecedorRequestNotFound() {
		FornecedorRequestDTO request = doFornecedorNotFound();

		return new Object[][] { new Object[] { request } };
	}

	private FornecedorRequestDTO doFornecedorNotFound() {
		FornecedorRequestDTO request = new FornecedorRequestDTO();

		request.setId(ID_INVALIDO);
		request.setCnpj(CNPJ_4NOTFOUND);
		request.setDescricao("NOTFOUND");
		request.setNomeFantasia("NOTFOUND");
		request.setRazaoSocial("NOTFOUND");
		return request;
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
