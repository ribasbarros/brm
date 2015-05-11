package br.com.brm.scp.fw.test.converters;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import scala.annotation.meta.setter;

public class ConverterTest {

	private static final boolean UTILS = true;

	@BeforeClass
	public void setup() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
	}

	@org.testng.annotations.Test(enabled = UTILS, groups = "Utils", priority = 1)
	public void converterHelper() throws Exception {
		ClassTestA clazzA = new ClassTestA();
		clazzA.setA("TESTE A");
		clazzA.setB(new Integer(3));
		clazzA.setC(Arrays.asList(new Object[] { "value01", "value02" }));

		ClassTestB clazzB = (ClassTestB) convert(clazzA, ClassTestB.class);

	}

	@SuppressWarnings({ "unchecked", "unused" })
	private <T extends Object, Y extends Object> Object convert(T clazzA, Class<Y> clazzB) {

		try {
			
			Object test = (Object) Class.forName(clazzB.getCanonicalName()).newInstance();
			
			Class<? extends Object> fromClass = clazzA.getClass();
			Field[] fromFields = fromClass.getDeclaredFields();

			for(Field f : fromFields){
				//test.getClass().getDeclaredMethods().Field(f.getName()).setAccessible(true);
			}
			
			
			System.out.println(test);
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}