package br.com.brm.scp.fw.helper.validators;

import java.math.BigInteger;

import org.apache.log4j.Logger;

public class NumberHelper {
	
	private static final String SYSTEM_NUMEROINVALIDO = "system.numeroinvalido";

	private static final String SYSTEM_VALIDO = "system.valido";
	
	private static Logger logger = Logger.getLogger(NumberHelper.class);

	public static boolean isNumber(String cnpj) {
		try{
			BigInteger convert = new BigInteger(cnpj);
			logger.info(String.format("%s : %s", convert, SYSTEM_VALIDO));
			
			return true;
		}catch(NumberFormatException ex){
			logger.warn(String.format("%s : %s", cnpj, SYSTEM_NUMEROINVALIDO));
		}
		
		return false;
	}

}
