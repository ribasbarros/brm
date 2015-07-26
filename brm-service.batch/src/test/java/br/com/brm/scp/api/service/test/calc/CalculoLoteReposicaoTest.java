package br.com.brm.scp.api.service.test.calc;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeClass;

//@ContextConfiguration(locations = { "classpath:META-INF/application-context.xml" })
public class CalculoLoteReposicaoTest {

	private static final boolean ENABLE = true;
	
	private static final double ESTOQUE_IDEAL = 1000;
	private static final double LOTE_ITEM = 200;
	
	@BeforeClass
	public void setup() throws Exception {
		
	}

	@org.testng.annotations.Test(enabled = ENABLE, groups = "CALC", priority = 1)
	public void test() {
		Integer quantidade = (int) Math.abs(ESTOQUE_IDEAL / LOTE_ITEM);
		System.out.println(quantidade);
		assertTrue( quantidade > 0 );
		
	}
}
