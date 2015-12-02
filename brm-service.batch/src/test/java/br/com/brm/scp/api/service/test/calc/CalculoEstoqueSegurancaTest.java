package br.com.brm.scp.api.service.test.calc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.brm.scp.api.dto.response.ItemResponseDTO;
import br.com.brm.scp.api.dto.response.SkuResponseDTO;
import br.com.brm.scp.api.exceptions.ItemNotFoundException;
import br.com.brm.scp.api.exceptions.SkuNotFoundException;
import br.com.brm.scp.api.service.ItemService;
import br.com.brm.scp.api.service.PedidoService;
import br.com.brm.scp.api.service.SkuService;
import br.com.brm.scp.api.vo.PedidoVO;
import br.com.brm.scp.batch.security.config.AppConfigurationBatchTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationBatchTest.class)
public class CalculoEstoqueSegurancaTest extends AbstractTestNGSpringContextTests {

	private static final boolean MOCK = true;
	private static final boolean SIMULATION = true;

	private static final double NIVEL_SEGURANCA = 3D;
	private static final Double[] DEMANDA_HISTORICO = new Double[] { 5000D, 5200D, 4980D, 5230D, 4950D, 5030D, 4970D,
			5010D, 5020D, 5005D, 4880D, 5024D };
	private static final Double[] TEMPO_ENTREGA_HISTORICO = new Double[] { 60D, 61D, 62D, 60D, 59D, 60D, 60D, 61D, 61D,
			61D, 62D, 60D };

	@Autowired
	private SkuService skuService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ItemService itemService;

	@BeforeClass
	public void setup() throws Exception {

	}

	@Test(enabled = MOCK, groups = "MOCK", priority = 1)
	public void testRunMock() {

		Double mediaLeadTime = mediaAritmetica(TEMPO_ENTREGA_HISTORICO);
		Double mediaDemanda = mediaAritmetica(DEMANDA_HISTORICO);

		Double desvioPadraoDemanda = desvioPadraoTest(DEMANDA_HISTORICO, mediaDemanda);
		Double desvioPadraoLeadTime = desvioPadraoTest(TEMPO_ENTREGA_HISTORICO, mediaLeadTime);

		Double first = Math.pow(desvioPadraoDemanda, 2) * mediaLeadTime;
		Double second = Math.pow(desvioPadraoLeadTime, 2) * Math.pow(mediaDemanda, 2);

		Double result = NIVEL_SEGURANCA * Math.sqrt(first + second);

		System.out.println("estoque de seguranca: " + result);

		double resultMinLeadTime = NIVEL_SEGURANCA * desvioPadraoDemanda * Math.sqrt(mediaLeadTime);

		System.out.println("estoque de seguranca (Pouco Lead time): " + resultMinLeadTime);

	}

	@Test(enabled = SIMULATION, groups = "SIMULATION", priority = 2)
	public void testSimulation() {

		List<ItemResponseDTO> items;
		Collection<SkuResponseDTO> chain = null;

		try {
			items = new ArrayList<>(itemService.all());
			chain = skuService.chain(items.get(0).getId());
		} catch (SkuNotFoundException | ItemNotFoundException e1) {
			e1.printStackTrace();
		}

		Map<Integer, Collection<SkuResponseDTO>> map = orderByNiveis(chain);

		for (Collection<SkuResponseDTO> skus : map.values()) {
			for (SkuResponseDTO sku : skus) {
				List<PedidoVO> hist = new ArrayList<>(pedidoService.findFaturamentoByMonth(sku.getId(), 32));

				Double[] DEMANDA_HISTORICO_SIMULATION = new Double[hist.size()];
				Double[] DDV = new Double[hist.size()];

				for (int i=0 ; i < hist.size() ; i++) {
					DEMANDA_HISTORICO_SIMULATION[i] = Double.valueOf(hist.get(i).getQuantidade().toString());
					DDV[i] = hist.get(i).getDdv();
				}

				Double mediaDemanda = mediaAritmetica(DEMANDA_HISTORICO_SIMULATION);
				Double desvioPadraoDemanda = desvioPadraoTest(DEMANDA_HISTORICO_SIMULATION,
						mediaDemanda);

				double mediaLeadTime = 2;
				double desvioPadraoLeadTime = 2;
				double ddvMedio = mediaAritmetica(DDV);
				Double first = Math.pow(desvioPadraoDemanda, 2) * mediaLeadTime;
				Double second = Math.pow(desvioPadraoLeadTime, 2) * Math.pow(ddvMedio, 2);

				Double result = sku.getNivelServico() * Math.sqrt(first + second);

				System.out.println("estoque de seguranca: " + result);

				double resultMinLeadTime = sku.getNivelServico() * desvioPadraoDemanda * Math.sqrt(mediaLeadTime);

				System.out.println("estoque de seguranca (Pouco Lead time): " + resultMinLeadTime);
				
				Double estoqueMaximo = result * sku.getLoteReposicao();
				
				try {
					skuService.estoqueSeguranca(sku.getId(), result);
					skuService.estoqueMaximo(sku.getId(), estoqueMaximo);
				} catch (SkuNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public double desvioPadraoTest(Double[] objetos, Double mediaAritimetica) {
		if (objetos.length == 1) {
			return 0.0;
		} else {
			double somatorio = 0D;
			for (int i = 0; i < objetos.length; i++) {
				double result = objetos[i] - mediaAritimetica;
				somatorio += Math.pow(Math.abs(result), 2);
			}

			double raiz = Math.sqrt(somatorio / objetos.length);

			return raiz;
		}
	}

	private Double mediaAritmetica(Double[] list) {

		int size = list.length;
		double sun = 0D;
		for (Double line : list) {
			sun += line;
		}
		double media = sun / size;
		System.out.println("Media: " + media);
		return media;
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
