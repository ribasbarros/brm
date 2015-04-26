package br.com.brm.scp.api.service.test.calc;

import org.testng.annotations.BeforeClass;

//@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class CalculoPreviaVendasMockTest {

	private static final boolean TEST_ESTOQUE_SEGURANCA = true;
	
	private static final double NIVEL_SEGURANCA = 3D;
	private static final Double[] DEMANDA_HISTORICO = new Double[]{5000D, 5200D, 4980D, 5230D, 4950D, 5030D, 4970D, 5010D, 5020D, 5005D, 4880D, 5024D};
	private static final Double[] TEMPO_ENTREGA_HISTORICO = new Double[]{60D, 61D, 62D, 60D, 59D, 60D, 60D, 61D, 61D, 61D, 62D, 60D};
	
	
	@BeforeClass
	public void setup() throws Exception {
		
	}

	@org.testng.annotations.Test(enabled = TEST_ESTOQUE_SEGURANCA, groups = "CRUD", priority = 1)
	public void create() {
		
		Double mediaLeadTime = mediaAritmetica(TEMPO_ENTREGA_HISTORICO);
		Double mediaDemanda = mediaAritmetica(DEMANDA_HISTORICO);

		Double desvioPadraoDemanda = desvioPadraoTest(DEMANDA_HISTORICO, mediaDemanda);
		Double desvioPadraoLeadTime = desvioPadraoTest(TEMPO_ENTREGA_HISTORICO, mediaLeadTime);
		
		Double first = Math.pow(desvioPadraoDemanda, 2) * mediaLeadTime;
		Double second = Math.pow(desvioPadraoLeadTime, 2) * Math.pow(mediaDemanda,2);
		
		Double result = NIVEL_SEGURANCA * Math.sqrt(first + second);
		
		System.out.println("estoque de seguranca: " + result);
		
		double resultMinLeadTime = NIVEL_SEGURANCA * desvioPadraoDemanda * Math.sqrt(mediaLeadTime);
		
		System.out.println("estoque de seguranca (Pouco Lead time): " + resultMinLeadTime);
		
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
            
            double raiz = Math.sqrt(somatorio/objetos.length);
            
			return raiz;  
        }  
    }
	
	private Double mediaAritmetica(Double[] list) {
		
		int size = list.length;
		double sun = 0D;
		for(Double line : list){
			sun += line;
		}
		double media = sun/size;
		System.out.println("Media: " + media);
		return media;
	}
	
}
