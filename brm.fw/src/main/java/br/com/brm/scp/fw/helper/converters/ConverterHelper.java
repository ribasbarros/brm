package br.com.brm.scp.fw.helper.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import br.com.brm.scp.fw.annotations.ConvertFor;
import br.com.brm.scp.fw.helper.objects.StringHelper;

public class ConverterHelper {

	private static Logger logger = Logger.getLogger(ConverterHelper.class);
	
	public static <T extends Object, Y> Collection<Y> convert(Collection<T> instance, Class<Y> clazzB){
		
		logger.info("------------------------------------------------------");
		logger.info(String.format("Convertendo Collection<objeto> %s para %s", instance.getClass().getSimpleName(), clazzB.getSimpleName()));
		logger.info("-------------------------------------------------------");
		
		Collection<Y> list = new ArrayList<Y>();
		for(T obj : instance){
			list.add((Y) convert(obj, clazzB));
		}
		return list;
	}
	
	public static <T extends Object> Object convert(T instanceFrom, Class<?> clazzB) {

		logger.info("------------------------------------------------------");
		logger.info(String.format("Convertendo objeto %s para %s", instanceFrom.getClass().getSimpleName(), clazzB.getSimpleName()));
		logger.info("-------------------------------------------------------");

		Object instanceDestine = null;
		try {
			instanceDestine = (Object) Class.forName(clazzB.getCanonicalName()).newInstance();
			Class<? extends Object> fromClass = instanceFrom.getClass();
			Field[] fromFields = fromClass.getDeclaredFields();
			for (Field f : fromFields) {
				try {

					String methodGetterNameOrigem = "get" + StringHelper.toFirstProperCase(f.getName());
					String methodSetterNameDestine = "set" + StringHelper.toFirstProperCase(f.getName());

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
	
	private static Collection<Object> convert(ConvertFor convert2, Object invoke) {
		Collection<Object> collectionConverted = new ArrayList<Object>();
		for (Object elementFromCollection : (ArrayList) invoke) {
			Object element = convert(elementFromCollection, convert2.value());
			collectionConverted.add(element);
		}
		return collectionConverted;
	}

}
