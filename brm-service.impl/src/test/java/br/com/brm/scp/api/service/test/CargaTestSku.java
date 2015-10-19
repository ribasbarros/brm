package br.com.brm.scp.api.service.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import br.com.brm.scp.api.service.document.CategoriaDocument;
import br.com.brm.scp.api.service.document.FornecedorCentroDocument;
import br.com.brm.scp.api.service.document.FornecedorDocument;
import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.repositories.CategoriaRepository;
import br.com.brm.scp.api.service.repositories.FornecedorRepository;
import br.com.brm.scp.api.service.repositories.ItemRepository;
import br.com.brm.scp.api.service.repositories.TagRepository;
import br.com.brm.scp.api.service.status.ItemStatus;
import br.com.brm.scp.api.service.test.helper.GeraCpfCnpj;

public class CargaTestSku extends AbstractTestNGSpringContextTests {

	private static final String REGEX_NOCHAR_CNPJ = "[-.//]";
	
	@Autowired
	protected ItemRepository itemRepository;
	
	@Autowired
	protected CategoriaRepository categoriaRepository;
	
	@Autowired
	protected TagRepository tagRepository;
	
	@Autowired
	protected FornecedorRepository fornecedorRepository;
	
	protected ItemDocument item = new ItemDocument();
	
	protected TagDocument tag1 = new TagDocument();
	protected TagDocument tag2 = new TagDocument();
	protected TagDocument tag3 = new TagDocument();
	protected FornecedorDocument fornecedor = new FornecedorDocument();
	
	protected void createFornecedor4Test() {
		fornecedor.setCnpj(GeraCpfCnpj.cnpj().replaceAll(REGEX_NOCHAR_CNPJ,""));
		fornecedor.setDescricao("Google");
		fornecedor.setNomeFantasia("Google");
		fornecedor.setRazaoSocial("Google Brasil Internet Ltda");
		FornecedorCentroDocument centro = createCentroFornecedor4Test();
		fornecedor.setCentros(new ArrayList<>(Arrays.asList(centro)));
		fornecedor = fornecedorRepository.save(fornecedor);
	}

	private FornecedorCentroDocument createCentroFornecedor4Test() {
		FornecedorCentroDocument centro = new FornecedorCentroDocument();
		centro.setCentro(1000);
		centro.setCep("01245-090");
		centro.setCnpj(GeraCpfCnpj.cnpj().replaceAll(REGEX_NOCHAR_CNPJ,""));
		return centro;
	}

	protected void createTags4Test() {
		tag1 = new TagDocument("Brasil", 1);
		tag2 = new TagDocument("São Paulo", 2);
		tag3 = new TagDocument("Rio de Janeiro", 2);
		tagRepository.save(tag1);
		tagRepository.save(tag2);
		tagRepository.save(tag3);
	}

	protected void createItem4Test() {
		CategoriaDocument categoria01 = new CategoriaDocument();
		categoria01.setNome("Categoria001");

		categoria01 = categoriaRepository.save(categoria01);
		
		item.setCategoria(categoria01);
		item.setNome("Item0002");
		item.setNomeReduzido("Item02");
		item.setDescricao("Item 02 descricao teste");
		item.setStatus(ItemStatus.ATIVO);
		item.setUnitizacao(6);
		item.setValorUnitario(new BigDecimal(0.20));
		
		item = itemRepository.save(item);
	}
	
	
}
