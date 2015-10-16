package br.com.brm.scp.fw.test.mask;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class CNPJMaskTest {

	//11.210.205/0001-18
	private static final String CNPJ_TEST = "11210205000118";

	private Logger logger = Logger.getLogger(CNPJMaskTest.class);

	@BeforeClass
	public void setup() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
	}

	@org.testng.annotations.Test(enabled = true, groups = "Utils", priority = 1)
	public void test() throws Exception {
		
		String cnpj = String.format("%s.%s.%s/%s-%s", CNPJ_TEST.substring(0,2), CNPJ_TEST.substring(2,5), CNPJ_TEST.substring(5,8) , CNPJ_TEST.substring(8,12) , CNPJ_TEST.substring(12,14));
		
		System.out.println(CNPJ_TEST);
		System.out.println(cnpj);
		
	}

}