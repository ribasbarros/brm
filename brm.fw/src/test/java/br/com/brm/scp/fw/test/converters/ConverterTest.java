package br.com.brm.scp.fw.test.converters;

import static org.testng.AssertJUnit.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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
		ClassTestA obj = new ClassTestA();
		obj.setA("TESTE A");
		obj.setB(new Integer(3));
		obj.setC(Arrays.asList(new Object[] { "value01", "value02" }));
		
		Collection<ClassTestC> listC = new ArrayList<ClassTestC>();
		ClassTestC c = new ClassTestC();
		c.setY("Y");
		c.setZ("Z");
		listC.add(c);
		obj.setListC(listC);

		ClassTestB clazzB = (ClassTestB) convert(obj, ClassTestB.class);

		assertTrue(clazzB.getA().equals(obj.getA()));
		assertTrue(clazzB.getC().equals(obj.getC()));

	}

	private <T extends Object> Object convert(T instanceFrom, Class<?> clazzB) {

		Object instanceTo = null;

		try {

			instanceTo = (Object) Class.forName(clazzB.getCanonicalName()).newInstance();

			Class<? extends Object> fromClass = instanceFrom.getClass();
			Field[] fromFields = fromClass.getDeclaredFields();

			for (Field f : fromFields) {

				Annotation declaredAnnotations = f.getAnnotation(ConvertFor.class);

				if (declaredAnnotations instanceof ConvertFor) {
					System.out.println("annotation in " + f.getName());
				}

				try {
					Method setterMethodTo = instanceTo.getClass().getDeclaredMethod("set" + toCamelCase(f.getName()),
							f.getType());
					setterMethodTo.setAccessible(true);

					Method getterMethodFrom = fromClass.getDeclaredMethod("get" + toCamelCase(f.getName()));

					setterMethodTo.invoke(instanceTo, getterMethodFrom.invoke(instanceFrom));

				} catch (NoSuchMethodException e) {
				}
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return instanceTo;
	}

	static String toCamelCase(String s) {
		String[] parts = s.split("_");
		String camelCaseString = "";
		for (String part : parts) {
			camelCaseString = camelCaseString + toProperCase(part);
		}
		return camelCaseString;
	}

	static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

}