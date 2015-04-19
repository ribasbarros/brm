package br.com.brm.scp.api.service.test;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.request.DfuRequestDTO;
import br.com.brm.scp.api.service.DfuService;

//@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class DfuSeviceMockTest {

	private static final Long ID_DFU_INVALIDO = -999L;
	
	private static final boolean RUN_CRUD = true;
	private static final boolean RUN_BUSINESS = true;
	
	@Autowired
	private DfuService dfuService;
	
	@BeforeClass
	public void setup() throws Exception {
		//criar massa de dados para o teste
	}

	@AfterClass
	public void tearDown() throws Exception {
		//rollback da massa de dados do teste
	}
	
	@Test(enabled=RUN_CRUD, groups="CRUD", dataProvider="", priority=1)
	public void testCreate(DfuRequestDTO dfuSuccess, DfuRequestDTO dfuFail){
		
		
		dfuService.create(dfuSuccess);
		
		dfuService.create(dfuFail);
		
		
	}

	@Test(enabled=RUN_CRUD, groups="CRUD", dataProvider="", priority=2)
	public void testAll(DfuRequestDTO dfuSuccess, DfuRequestDTO dfuFail){
		Collection<DfuRequestDTO> list = dfuService.all();
	}
	
	@Test(enabled=RUN_CRUD, groups="CRUD", dataProvider="", priority=2)
	public void testFindById(DfuRequestDTO dfuSuccess, DfuRequestDTO dfuFail){
		
		DfuRequestDTO dtoSuccess = dfuService.find(dfuSuccess.getId());
		
		DfuRequestDTO dtoFail = dfuService.find(ID_DFU_INVALIDO);
		
	}
	
	@Test(enabled=RUN_CRUD, groups="CRUD", dataProvider="", priority=3)
	public void testDelete(DfuRequestDTO dfuSuccess, DfuRequestDTO dfuFail){
		
		Boolean deleted = dfuService.delete(dfuSuccess.getId());
		
		Boolean failed = dfuService.delete(ID_DFU_INVALIDO);
		
	}
	
	@Test(enabled=RUN_BUSINESS, groups="NEGOCIO", priority=1)
	public void testDesvioPadrao(){
		
	}
	
	@Test(enabled=RUN_BUSINESS, groups="NEGOCIO", priority=2)
	public void testCreateDinamicamenteDFUs(){
		
	}
	
}
