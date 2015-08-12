package br.com.brm.scp.api.service.test;

import static org.testng.AssertJUnit.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.cypher.internal.compiler.v2_1.functions.E;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;

import br.com.brm.scp.api.dto.request.GrupoRequestDTO;
import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.api.exceptions.GrupoExistenteException;
import br.com.brm.scp.api.exceptions.GrupoNotFoundException;
import br.com.brm.scp.api.exceptions.PerfilRepetidoException;
import br.com.brm.scp.api.service.GrupoService;

@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class GrupoSeviceMockTest extends AbstractTestNGSpringContextTests {

	@Autowired
	GrupoService service;

	private static final boolean TEST_CRUD = true;
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 1, dataProvider = "novoGrupo")
	public void create(GrupoRequestDTO request) throws GrupoExistenteException, GrupoNotFoundException, PerfilRepetidoException {
		assertNotNull(request);
		service.create(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 2, dataProvider = "novoGrupoAlterado")
	public void update(GrupoRequestDTO request) throws GrupoNotFoundException, PerfilRepetidoException{
		assertNotNull(request);
		service.update(request);
	}
	
	@org.testng.annotations.Test(enabled = TEST_CRUD, groups = "CRUD", priority = 3, dataProvider = "novoGrupoAlterado")
	public void delete(GrupoRequestDTO request) throws GrupoNotFoundException {
		assertNotNull(request);
		service.delete(request);		
	}
			
	@DataProvider(name = "novoGrupo")
	public Object[][] criaFornecedorRequest() {
		GrupoRequestDTO request = new GrupoRequestDTO(null,"ADM",null);
		return new Object[][] { new Object[] { request } };
	}
	
	@DataProvider(name = "novoGrupoAlterado")
	public Object[][] criaFornecedorRequest2() {
		GrupoRequestDTO request = new GrupoRequestDTO(1L,"Outros",null);
		return new Object[][] { new Object[] { request } };
	}
	
}
