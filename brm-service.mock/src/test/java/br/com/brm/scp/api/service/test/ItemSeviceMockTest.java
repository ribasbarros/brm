package br.com.brm.scp.api.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import br.com.brm.scp.api.dto.request.ItemRequestDTO;
import br.com.brm.scp.api.exceptions.ItemNotFound;
import br.com.brm.scp.api.service.ItemService;

//@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class ItemSeviceMockTest {

	@Autowired
	private ItemService itemService;
	
	private static final boolean TEST_CRUD = true;
	
	@BeforeClass
	public void setup() throws Exception {
		//criar massa de dados para o teste
	}

	@AfterClass
	public void tearDown() throws Exception {
		//rollback da massa de dados do teste
	}
	
	public void testCreate(){
		
		
		ItemRequestDTO request = new ItemRequestDTO();
		
		try {
			itemService.create(request);
		} catch (ItemNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
