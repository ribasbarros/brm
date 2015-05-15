package br.com.brm.scp.fw.test.converters;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import br.com.brm.scp.fw.annotations.ConvertFor;
import br.com.brm.scp.fw.helper.converters.ConverterHelper;
import br.com.brm.scp.fw.test.dto.ClassTestA;
import br.com.brm.scp.fw.test.dto.ClassTestB;
import br.com.brm.scp.fw.test.dto.ClassTestC;

public class ConverterHelperTest {

	private static final boolean UTILS = false;

	private static final boolean COLLECTION_CONVERT = true;

	private Logger logger = Logger.getLogger(ConverterHelperTest.class);

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

		Collection<ClassTestC> listC = new ArrayList<ClassTestC>();
		ClassTestC c = new ClassTestC();
		c.setY("Y");
		c.setZ("Z");
		listC.add(c);

		c = new ClassTestC();
		c.setY("Y01");
		c.setZ("Z01");
		listC.add(c);

		clazzA.setListC(listC);

		ClassTestC objectNoList = new ClassTestC();
		objectNoList.setY("Y02");
		objectNoList.setZ("Z02");
		clazzA.setObjectNoList(objectNoList);

		ClassTestB clazzB = (ClassTestB) convert(clazzA, ClassTestB.class);

		assertTrue(clazzB.getA().equals(clazzA.getA()));
		assertTrue(clazzB.getC().equals(clazzA.getC()));
		assertTrue(clazzB.getObjectNoList().getZ().equals(clazzA.getObjectNoList().getZ()));
	}

	@org.testng.annotations.Test(enabled = COLLECTION_CONVERT, groups = "Utils", priority = 1)
	public void converterCollectionHelper() throws Exception {
		
		Collection<ClassTestA> collection = new ArrayList<ClassTestA>();
		
		for (int i = 0; i < 10; i++) {
			ClassTestA clazzA = new ClassTestA();
			clazzA.setA("TESTE A - " + i);
			clazzA.setB(new Integer(i));
			clazzA.setC(Arrays.asList(new Object[] { "value01- " + i, "value02- " + i }));

			Collection<ClassTestC> listC = new ArrayList<ClassTestC>();
			ClassTestC c = new ClassTestC();
			c.setY("Y- " + i);
			c.setZ("Z- " + i);
			listC.add(c);

			c = new ClassTestC();
			c.setY("Y01- " + i);
			c.setZ("Z01- " + i);
			listC.add(c);

			clazzA.setListC(listC);

			ClassTestC objectNoList = new ClassTestC();
			objectNoList.setY("Y02- " + i);
			objectNoList.setZ("Z02- " + i);
			clazzA.setObjectNoList(objectNoList);
			
			collection.add(clazzA);
			
		}
		Collection<ClassTestB> clazzB = ConverterHelper.convert(collection, ClassTestB.class);
		
		assertNotNull(clazzB);
		
	}

	private <T extends Object> Object convert(T instanceFrom, Class<?> clazzB) {

		logger.info("------------------------------------------------------");
		logger.info(String.format("Convertendo objeto %s para %s", instanceFrom.getClass().getSimpleName(),
				clazzB.getSimpleName()));
		logger.info("-------------------------------------------------------");

		Object instanceDestine = null;
		try {
			instanceDestine = (Object) Class.forName(clazzB.getCanonicalName()).newInstance();
			Class<? extends Object> fromClass = instanceFrom.getClass();
			Field[] fromFields = fromClass.getDeclaredFields();
			for (Field f : fromFields) {
				try {

					String methodGetterNameOrigem = "get" + toFirstProperCase(f.getName());
					String methodSetterNameDestine = "set" + toFirstProperCase(f.getName());

					Method getterMethodFrom = fromClass.getDeclaredMethod(methodGetterNameOrigem);
					Class<?> typeObjectDestine = instanceDestine.getClass().getDeclaredField(f.getName()).getType();
					Method setterMethodTo = instanceDestine.getClass().getDeclaredMethod(methodSetterNameDestine,
							typeObjectDestine);
					setterMethodTo.setAccessible(true);

					Annotation annotation4Conversion = f.getAnnotation(ConvertFor.class);

					if (annotation4Conversion instanceof ConvertFor) {

						ConvertFor convert2 = (ConvertFor) annotation4Conversion;
						Object invoke = getterMethodFrom.invoke(instanceFrom);

						logger.info(String.format("ANOTACAO: Converter classe anotada para %s", invoke));

						if (invoke instanceof Collection<?>) {

							logger.info("ANOTACAO: Convertendo uma coleção");

							Collection<Object> collectionConverted = convert(convert2, invoke);

							setterMethodTo.invoke(instanceDestine, collectionConverted);
						} else if (invoke instanceof Object) {

							logger.info("ANOTACAO: Convertendo um objeto");

							Object element = convert(invoke, convert2.value());
							setterMethodTo.invoke(instanceDestine, element);
						}
					} else {
						setterMethodTo.invoke(instanceDestine, getterMethodFrom.invoke(instanceFrom));
					}
				} catch (NoSuchMethodException e) {
					logger.debug(e.getMessage());
				} catch (NoSuchFieldException e) {
					logger.warn(String.format("Campo %s da classe %s não encontrado!", e.getMessage(), instanceDestine
							.getClass().getSimpleName()));
				}
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}

		logger.info("------------------------------------------------------");
		logger.info("Fim da converção!");
		logger.info("------------------------------------------------------");

		return instanceDestine;
	}

	private Collection<Object> convert(ConvertFor convert2, Object invoke) {
		Collection<Object> collectionConverted = new ArrayList<Object>();
		for (Object elementFromCollection : (ArrayList) invoke) {
			Object element = convert(elementFromCollection, convert2.value());
			collectionConverted.add(element);
		}
		return collectionConverted;
	}

	static String toFirstProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}