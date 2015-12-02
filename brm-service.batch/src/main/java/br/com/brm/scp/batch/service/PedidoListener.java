package br.com.brm.scp.batch.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.PedidoResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.exceptions.PedidoNotFoundException;
import br.com.brm.scp.api.exceptions.PedidoOrigemNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.api.service.status.SkuFiltroEnum;

@Component
public class PedidoListener {

	@Autowired
	private ItemService itemService;

	@Autowired
	private SkuService skuService;

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private SkuRepository skuRepository; 

	@Scheduled(fixedDelay = 10000)
	public void execute() throws SkuNotFoundException, ItemNotFoundException {

		System.out.println("*******************************");
		System.out.println("VERIFICANDO AS SKUS AUTOMATICAS");
		
		List<ItemResponseDTO> items = new ArrayList<>(itemService.all());
		Collection<SkuResponseDTO> chain = skuService.chain(items.get(0).getId());

		Map<Integer, Collection<SkuResponseDTO>> map = orderByNiveis(chain);

		for (Collection<SkuResponseDTO> skus : map.values()) {
			for (SkuResponseDTO sku : skus) {
				if(sku.isAutomatica()){
					Calendar dataAtual = Calendar.getInstance();
					validaEstoqueSeguranca(sku, dataAtual);
					resolverPedidos(sku.getId(), dataAtual);
				}
			}
		}
		System.out.println("FIM");
		System.out.println("*******************************");
	}
	
	private void validaEstoqueSeguranca(SkuResponseDTO sku, Calendar calStart) {
		try {
			sku = skuService.find(SkuFiltroEnum.ID, sku.getId());
		} catch (SkuNotFoundException e1) {
			e1.printStackTrace();
		}
		if (sku.getEstoqueAtual() <= sku.getEstoqueSeguranca()) {
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
			pedidoService.request(sku.getId(), sku.getEstoqueMaximo(), dateSolicitacao, "(Automático) Estoque de seguranca", false);
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
				if (("EM_PROCESSAMENTO".equalsIgnoreCase(pedido.getStatus().toString()))
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
			pedidoService.liberar(pedido.getId());
		} catch (PedidoNotFoundException e) {
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
