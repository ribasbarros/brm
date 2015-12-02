package br.com.brm.scp.batch.simulator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.exceptions.PedidoNotFoundException;
import br.com.brm.scp.api.exceptions.PedidoOrigemNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotSuchMuchQuantityException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.document.PedidoDocument;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.api.service.status.PedidoType;
import br.com.brm.scp.api.service.status.SkuFiltroEnum;
import br.com.brm.scp.batch.security.config.AppConfigurationBatchTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationBatchTest.class)
public class MakeHistoryTest extends AbstractTestNGSpringContextTests {

	private static final int ESTOQUE_SEGURANCA = 500;

	private static final Long ESTOQUE_PADRAO = 20000L;

	private static final int LEAD_TIME_DEFAULT = 2;

	private static final Long QUANTIDADE_VENDAS_DIA = 200L;

	@Autowired
	private SkuService skuService;

	@Autowired
	private SkuRepository skuRepository;

	@Autowired
	private ItemService itemService;

	@Autowired
	private PedidoService pedidoService;

	@Test(groups = "CRUD", priority = 1)
	public void test() {

		Calendar calStart = Calendar.getInstance();
		calStart.set(Calendar.DATE, 1);
		calStart.set(Calendar.MONTH, 10);
		calStart.set(Calendar.YEAR, 2013);

		Calendar calAtual = Calendar.getInstance();
		calAtual.set(Calendar.DATE, 1);
		calAtual.set(Calendar.MONTH, 10);
		calAtual.set(Calendar.YEAR, 2014);

		List<ItemResponseDTO> items;
		Collection<SkuResponseDTO> chain = null;

		try {
			items = new ArrayList<>(itemService.all());
			chain = skuService.chain(items.get(0).getId());
		} catch (SkuNotFoundException | ItemNotFoundException e1) {
			e1.printStackTrace();
		}

		Map<Integer, Collection<SkuResponseDTO>> map = orderByNiveis(chain);

		do {
			for (Collection<SkuResponseDTO> skus : map.values()) {
				for (SkuResponseDTO sku : skus) {
					if (sku.getTags().size() > 1) {
						vender(sku.getId(), calStart.getTime());
					}
					validaEstoqueSeguranca(sku, calStart);
					resolverPedidos(sku.getId(), calStart);
				}
			}
			calStart.add(Calendar.DATE, 1);
			System.out.println(String.format("DIA %s/%s/%s", calStart.get(Calendar.DATE),
					(calStart.get(Calendar.MONTH) + 1), calStart.get(Calendar.YEAR)));
		} while (calStart.getTimeInMillis() < calAtual.getTimeInMillis());

	}

	private void vender(String id, Date dataSolicitacao) {
		try {
			pedidoService.ordemVenda(PedidoType.EXTERNO, id, -QUANTIDADE_VENDAS_DIA, dataSolicitacao, "VENDA", false);
		} catch (PedidoOrigemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void resolverPedidos(String idSku, final Calendar current) {
		try {

			SkuDocument document = skuRepository.findOne(idSku);

			Collection<PedidoResponseDTO> pedidos = pedidoService.listByOrigem(idSku);

			for (PedidoResponseDTO pedido : pedidos) {

				Calendar calPedido = Calendar.getInstance();
				calPedido.setTime(pedido.getDataSolicitacao());
				calPedido.add(Calendar.DATE, LEAD_TIME_DEFAULT);

				if ("AGUARDANDO_FORNECEDOR".equalsIgnoreCase(pedido.getStatus().toString())) {
					if (calPedido.getTimeInMillis() <= current.getTimeInMillis()) {
						doAprovar(pedido, current);
					} else {
						System.out.println("nao chegou!");
					}
				} else if (("EM_PROCESSAMENTO".equalsIgnoreCase(pedido.getStatus().toString()))
						|| (document.getEstoqueAtual() >= pedido.getQuantidade()
								&& "SOLICITADO".equalsIgnoreCase(pedido.getStatus().toString()))) {
					doAprovar(pedido, current);
				} else if (document.getEstoqueAtual() < pedido.getQuantidade()
						&& "SOLICITADO".equalsIgnoreCase(pedido.getStatus().toString()) && !pedido.isEscalonada()) {
					pedidoService.escalonar(pedido.getId());
				}
			}

		} catch (PedidoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void doAprovar(PedidoResponseDTO pedido, final Calendar dataAtual) {
		try {
			pedidoService.liberar(pedido.getId(), dataAtual.getTime());
		} catch (PedidoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void validaEstoqueSeguranca(SkuResponseDTO sku, Calendar calStart) {
		try {
			sku = skuService.find(SkuFiltroEnum.ID, sku.getId());
		} catch (SkuNotFoundException e1) {
			e1.printStackTrace();
		}
		if (sku.getEstoqueAtual() <= ESTOQUE_SEGURANCA) {
			try {
				Collection<PedidoResponseDTO> pedidos = pedidoService.listByOrigem(sku.getId());
				for (PedidoResponseDTO pedido : pedidos) {
					if ("AGUARDANDO_FORNECEDOR".equalsIgnoreCase(pedido.getStatus().toString())) {
						return;
					}
				}
			} catch (PedidoNotFoundException e) {
			}
			doPedido(sku, calStart.getTime());
		}

	}

	private void doPedido(SkuResponseDTO sku, Date dateSolicitacao) {
		try {
			pedidoService.request(sku.getId(), ESTOQUE_PADRAO, dateSolicitacao, "Estoque de seguranca", false);
		} catch (PedidoOrigemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<Integer, Collection<SkuResponseDTO>> orderByNiveis(Collection<SkuResponseDTO> chain) {
		Map<Integer, Collection<SkuResponseDTO>> map = new TreeMap<>();
		for (SkuResponseDTO response : chain) {
			if (map.get(response.getTags().size()) == null)
				map.put(response.getTags().size(), new ArrayList<>());

			Collection<SkuResponseDTO> temp = map.get(response.getTags().size());
			temp.add(response);

			map.put(response.getTags().size(), temp);
		}

		return map;
	}

}