package br.com.brm.scp.api.service.test.util;

import static org.testng.Assert.fail;

public class TestUtils {

	public static void expectException(Class<? extends Exception> exceptionType, TestCallback callback)
			throws Exception {

		try {
			callback.doTest();
			fail("Expected exception not throw.");
		} catch (Exception e) {
			if (!exceptionType.isAssignableFrom(e.getClass()))
				fail("An exception is throw but not is the an exception expected.", e);
		}
	}
}