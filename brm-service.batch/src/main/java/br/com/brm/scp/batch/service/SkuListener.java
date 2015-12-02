package br.com.brm.scp.batch.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.vo.PedidoVO;
import br.com.brm.scp.fw.helper.MathBrmHelper;

@Component
public class SkuListener {

	private static final double MEDIA_LEAD_TIME = 2; /* CALCULAR LT */

	private static final double DESVIO_PADRAO_LEAD_TIME = 2;

	@Autowired
	private ItemService itemService;

	@Autowired
	private SkuService skuService;

	@Autowired
	private PedidoService pedidoService;

	@Scheduled(fixedDelay = 10000)
	public void execute() throws SkuNotFoundException, ItemNotFoundException {

		System.out.println("***************************************************");
		System.out.println("ATUALIZANDO ESTOQUE DE SEGURANÇA E ESTOQUE MAXIMO !");
		
		List<ItemResponseDTO> items = new ArrayList<>(itemService.all());
		Collection<SkuResponseDTO> chain = skuService.chain(items.get(0).getId());

		Map<Integer, Collection<SkuResponseDTO>> map = orderByNiveis(chain);

		for (Collection<SkuResponseDTO> skus : map.values()) {
			for (SkuResponseDTO sku : skus) {

				List<PedidoVO> hist = new ArrayList<>(pedidoService.findFaturamentoByMonth(sku.getId(), 32));

				Double[] DEMANDA_HISTORICO_SIMULATION = new Double[hist.size()];
				Double[] DDV = new Double[hist.size()];

				for (int i = 0; i < hist.size(); i++) {
					DEMANDA_HISTORICO_SIMULATION[i] = Double.valueOf(hist.get(i).getQuantidade().toString());
					DDV[i] = hist.get(i).getDdv();
				}

				Double mediaDemanda = MathBrmHelper.mediaAritmetica(DEMANDA_HISTORICO_SIMULATION);
				Double desvioPadraoDemanda = MathBrmHelper.desvioPadraoTest(DEMANDA_HISTORICO_SIMULATION, mediaDemanda);

				double ddvMedio = MathBrmHelper.mediaAritmetica(DDV);
				
				Double first = Math.pow(desvioPadraoDemanda, 2) * MEDIA_LEAD_TIME;
				Double second = Math.pow(DESVIO_PADRAO_LEAD_TIME, 2) * Math.pow(ddvMedio, 2);

				Double result = sku.getNivelServico() * Math.sqrt(first + second);

				Double estoqueMaximo = result * sku.getLoteReposicao();

				skuService.estoqueSeguranca(sku.getId(), result);
				skuService.estoqueMaximo(sku.getId(), estoqueMaximo);
			}
		}
		
		System.out.println("FIM ");
		System.out.println("***************************************************");

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
