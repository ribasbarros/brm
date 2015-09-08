package br.com.brm.scp.api.service.test.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import br.com.brm.scp.api.dto.request.SkuRequestDTO;
import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.TagResponseDTO;
import br.com.brm.scp.api.exceptions.UsuarioNotFoundException;
import br.com.brm.scp.api.service.UsuarioService;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.helper.objects.RandomHelper;
import br.com.brm.scp.mock.api.mockdata.MockData;
import br.com.brm.scp.mock.api.service.document.CategoriaDocument;
import br.com.brm.scp.mock.api.service.document.ContatoDocument;
import br.com.brm.scp.mock.api.service.document.FornecedorDocument;
import br.com.brm.scp.mock.api.service.document.ItemDocument;
import br.com.brm.scp.mock.api.service.document.TagDocument;
import br.com.brm.scp.mock.api.service.document.TelefoneDocument;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;
import br.com.brm.scp.mock.api.service.status.PlanejamentoSku;
import br.com.brm.scp.mock.api.service.status.ItemStatus;

public class SkuDadosFake extends AbstractTestNGSpringContextTests {

	public static final long USUARIO_LOGADO_FAKE = RandomHelper.UUID();
	
	@Autowired
	public MockData mockDb;
	
	@Autowired
	public UsuarioService usuarioService;
	
	/**
	 * Gerador de objeto de sku not found
	 * 
	 * @return
	 * @throws UsuarioNotFoundException
	 */
	public SkuRequestDTO doSkuNotFound() throws UsuarioNotFoundException {

		SkuRequestDTO notFound = new SkuRequestDTO();

		notFound.setId(RandomHelper.UUID());

		Collection<ItemResponseDTO> items = ConverterHelper.convert(mockDb.getItemCollection().values(),
				ItemResponseDTO.class);
		notFound.setItem(new ArrayList<ItemResponseDTO>(items).get(0));

		Collection<TagResponseDTO> selecionadas = new ArrayList<TagResponseDTO>();
		selecionarRandomTagByNivel(1, selecionadas);
		selecionarRandomTagByNivel(2, selecionadas);
		selecionarRandomTagByNivel(3, selecionadas);
		notFound.setTags(selecionadas);

		notFound.setAutomatica(Boolean.TRUE);
		notFound.setCustoUnitario(BigDecimal.ZERO);
		notFound.setDataDescontinuacao(Calendar.getInstance());
		notFound.setDataMaturidade(Calendar.getInstance());
		notFound.setDescricao("NOT FOUND");
		notFound.setEstoqueAtual(99);
		notFound.setEstoqueIdeal(99);
		notFound.setEstoqueMaximo(99);
		notFound.setEstoqueMinimo(99);
		notFound.setFrequenciaAnalise(new Integer[] { Calendar.DAY_OF_WEEK });
		notFound.setLoteReposicao(99);
		notFound.setLoteReposicaoHistorico(0);
		notFound.setModelo(PlanejamentoSku.ESTOQUE);

		notFound.setDataCriacao(Calendar.getInstance());
		//notFound.setUsuarioCriacao(usuarioService.findById(USUARIO_LOGADO_FAKE));

		return notFound;

	}

	/**
	 * Gerador de objetos de fornecedor (1) e items (n)
	 */
	public void doCreateItems4MassTest() {

		long uuidFornecedor = RandomHelper.UUID();

		FornecedorDocument fornecedor = new FornecedorDocument();
		fornecedor.setCnpj("999.999.999/9999-99");

		Collection<ContatoDocument> contato = doContatoFake();

		fornecedor.setContato(contato);
		fornecedor.setDescricao("FORNECEDOR PARA TESTE");
		fornecedor.setId(uuidFornecedor);
		fornecedor.setInscricaoEstadual("999.999.999.999");
		fornecedor.setNomeFantasia("NOME FANTASIA FAKE");
		fornecedor.setRazaoSocial("RAZAO SOCIAL FAKE");

		mockDb.getFornecedorCollection().put(uuidFornecedor, fornecedor);

		for (int i = 0; i < 100; i++) {

			ItemDocument item = new ItemDocument();

			long uuid = RandomHelper.UUID();

			item.setCategoria(new CategoriaDocument(RandomHelper.UUID(), "CATEGORIA_TESTE"));
			item.setFornecedor(fornecedor);
			item.setId(uuid);

			String nomeProduto = RandomHelper.randomString(5);

			item.setNome(String.format("TST_NOME_PRODUTO_%s:%s", i, nomeProduto));
			item.setNomeReduzido(String.format("TST_%s:%s", i, nomeProduto));
			item.setStatus(ItemStatus.ATIVO);
			item.setValorUnitario(new BigDecimal(Math.random() * 1000));
			item.setQuantidadeLote(200);

			mockDb.getItemCollection().put(uuid, item);

		}
	}

	/**
	 * Gerador de contatos fake
	 * 
	 * @return Collection<ContatoDocument>
	 */
	public Collection<ContatoDocument> doContatoFake() {

		Collection<ContatoDocument> contatos = new ArrayList<ContatoDocument>();
		for (int i = 0; i < 3; i++) {

			long uuid = RandomHelper.UUID();

			ContatoDocument contato = new ContatoDocument();
			contato.setNome(String.format("NM_%s", RandomHelper.randomString(5)));
			contato.setCep(String.format("03344-00%s", i));
			contato.setCidade("São Paulo");
			contato.setEndereco(String.format("Rua %s", i));
			contato.setId(uuid);
			Collection<TelefoneDocument> telefone = doTelefoneFake();
			contato.setTelefone(telefone);
			contato.setUf("SP");

			contatos.add(contato);
		}

		return contatos;

	}

	/**
	 * Gerador de Telefones fake
	 * 
	 * @return
	 */
	public Collection<TelefoneDocument> doTelefoneFake() {

		Collection<TelefoneDocument> telefones = new ArrayList<TelefoneDocument>();

		for (int i = 0; i < 2; i++) {
			TelefoneDocument telefone = new TelefoneDocument();

			telefone.setCelular(Boolean.FALSE);
			telefone.setNumero(new BigInteger(String.format("1194455315%s", i)));
			telefone.setRamal("");

			telefones.add(telefone);
		}

		return telefones;

	}

	/**
	 * Gerador de massa de tag para teste
	 */
	public void doCreateTag4MassTest() {
		for (int i = 0; i < 5; i++) { // Numero de Niveis
			int randomNum = 2 + (int) (Math.random() * 10); // Gera numero de
															// niveis
															// randomicamente
			for (int x = 0; x < randomNum; x++) {
				long uuid = RandomHelper.UUID();
				TagDocument tag = new TagDocument();
				tag.setId(uuid);
				tag.setNivel(i + 1);
				tag.setNome(String.format("TST_NVL_%s:%s", i, RandomHelper.randomString(5)));
				mockDb.getTagCollection().put(uuid, tag);
			}
		}
	}

	/**
	 * Gerador de Usuario Fake
	 */
	public void doCreateUsuario4MassTest() {
		mockDb.getUsuarioCollection().put(USUARIO_LOGADO_FAKE,
				new UsuarioDocument(USUARIO_LOGADO_FAKE, "Usuario Teste 01", "4Test", "test@test.com.br"));
	}

	/**
	 * Selecao de Tag por nivel
	 * 
	 * @param nivel
	 * @param selecionadas
	 * @return
	 */
	public Collection<TagResponseDTO> selecionarRandomTagByNivel(int nivel, Collection<TagResponseDTO> selecionadas) {

		Collection<TagDocument> all = mockDb.getTagCollection().values();

		Collection<TagDocument> byNivelParam = new ArrayList<TagDocument>();
		for (TagDocument doc : all) {
			if (doc.getNivel().intValue() == nivel) {
				byNivelParam.add(doc);
			}
		}

		int selecionarRandomicamente = 0 + (int) (Math.random() * byNivelParam.size());
		TagDocument selecionado = new ArrayList<TagDocument>(byNivelParam).get(selecionarRandomicamente);

		selecionadas.add((TagResponseDTO) ConverterHelper.convert(selecionado, TagResponseDTO.class));

		return selecionadas;
	}

}
