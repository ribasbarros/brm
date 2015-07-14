package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.List;

import static org.testng.AssertJUnit.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

import br.com.brm.scp.api.service.document.test.TestDocument;
import br.com.brm.scp.api.service.repositories.test.TestRepository;


@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class MongoDBTest extends AbstractTestNGSpringContextTests {

	private static final boolean RODAR = true;

	private static final boolean EXCLUIR_TESTES = false;

	@Autowired
	private TestRepository testRepository;

	private String uidSaved;
	
	@BeforeClass
	public void setup() throws Exception {
	}

	@org.testng.annotations.Test(enabled = RODAR, groups = "Test", priority = 1)
	public void testCriar() {
		TestDocument document = new TestDocument();
		document.setTestDouble(100.02D);
		document.setTestString("Teste String 1234");
		
		TestDocument response = testRepository.save(document);
		
		uidSaved = response.getId();
		
		assertNotNull(response);
		assertTrue(!uidSaved.isEmpty());
		
	}
	
	
	@org.testng.annotations.Test(enabled = RODAR, groups = "Test", priority = 2)
	public void testAlterar() {
		double doubleAlterado = 9999.02D;
		String stringAlterada = "Teste BLA";

		TestDocument document = new TestDocument();
		document.setId(uidSaved);
		document.setTestDouble(doubleAlterado);
		document.setTestString(stringAlterada);
		
		TestDocument response = testRepository.save(document);
		
		assertNotNull(response);
		assertTrue(uidSaved.equals(response.getId()));
		assertTrue(stringAlterada.equals(response.getTestString()));
		assertTrue(doubleAlterado == response.getTestDouble().doubleValue());
		
	}
	
	@org.testng.annotations.Test(enabled = RODAR, groups = "Test", priority = 3)
	public void testFind() {

		
		List<TestDocument> findAll = testRepository.findAll();
		
		assertTrue(findAll.size() > 0);
		
		TestDocument findOne = testRepository.findOne(uidSaved);
		
		assertNotNull(findOne);
		
	}
	
	@org.testng.annotations.Test(enabled = RODAR, groups = "Test", priority = 4)
	public void testCustom() {

		TestDocument findOne = testRepository.findOne(uidSaved);
		assertNotNull(findOne);
		
		List<TestDocument> findAll = testRepository.findTest(findOne.getTestString());
		assertTrue(findAll.size() > 0);
		
		
	}
	
	@org.testng.annotations.Test(enabled = RODAR && EXCLUIR_TESTES, groups = "Test", priority = 5)
	public void testExcluir() {

		testRepository.delete(uidSaved);
		
		TestDocument response = testRepository.findOne(uidSaved);
		
		assertNull(response);
		
	}
	
	@org.testng.annotations.Test(enabled = RODAR && EXCLUIR_TESTES, groups = "Test", priority = 6)
	public void cleanDb() {

		testRepository.deleteAll();
		
		List<TestDocument> findAll = testRepository.findAll();
		
		assertTrue(findAll.isEmpty());
		
	}
	
}
