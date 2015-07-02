package br.com.brm.scp.fw.helper.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.fw.annotations.BindingClass;
import br.com.brm.scp.fw.helper.objects.StringHelper;

public class ConverterHelper {

	private static final String SUFFIX = "_META";
	private static Logger logger = Logger.getLogger(ConverterHelper.class);

	/**
	 * @param instance ORIGEM
	 * @param clazzB DESTINO
	 * @param firstLayer (TRUE = conversao de DTO -> Document, FALSE = conversao de Document --> DTO) (isDTO)
	 * @return
	 */
	public static <T extends Object, Y> Collection<Y> convert(Collection<T> instance, Class<Y> clazzB) {

		logger.info("------------------------------------------------------");
		logger.info(String.format("Convertendo Collection<objeto> %s para %s", instance.getClass().getSimpleName(),
				clazzB.getSimpleName()));
		logger.info("-------------------------------------------------------");

		Collection<Y> list = new ArrayList<Y>();
		for (T obj : instance) {
			list.add((Y) convert(obj, clazzB));
		}
		return list;
	}

	public static <T extends Object> Object convert(T instanceFrom, Class<?> clazzB) {

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

				if (!"serialVersionUID".equals(f.getName())) {

					try {

						String methodGetterNameOrigem = "get" + StringHelper.toFirstProperCase(f.getName());
						String methodSetterNameDestine = "set" + StringHelper.toFirstProperCase(f.getName());

						Method getterMethodFrom = fromClass.getDeclaredMethod(methodGetterNameOrigem);
						Class<?> typeObjectDestine = instanceDestine.getClass().getDeclaredField(f.getName()).getType();
						Method setterMethodTo = instanceDestine.getClass().getDeclaredMethod(methodSetterNameDestine,
								typeObjectDestine);
						setterMethodTo.setAccessible(true);

						Annotation[] annotation4Conversion = f.getAnnotations();

						Annotation bindingClass = getBindingClass(annotation4Conversion);
						
						if (bindingClass != null) {
							
							BindingClass convert2 = (BindingClass) bindingClass;
							Object invoke = getterMethodFrom.invoke(instanceFrom);

							logger.info(String.format("ANOTACAO: Converter classe anotada para %s", invoke));

							if (invoke instanceof Collection<?>) {

								logger.info("ANOTACAO: Convertendo uma coleção");

								Collection<Object> collectionConverted = convert(convert2.value(), invoke);

								setterMethodTo.invoke(instanceDestine, collectionConverted);
							} else if (invoke instanceof Object) {

								logger.info("ANOTACAO: Convertendo um objeto");

								Object element = convert(invoke, convert2.value());
								setterMethodTo.invoke(instanceDestine, element);
							}
							
						} else {
							
							Annotation bindingClassMeta = getBindingClassMeta(annotation4Conversion);
							
							if(bindingClassMeta != null){
								Class<?> intanceMetaClass = Class.forName(fromClass.getCanonicalName()+SUFFIX);
								
								BindingClassMeta convert2 = (BindingClassMeta) bindingClassMeta;
								
								Field declaredField = intanceMetaClass.getDeclaredField(convert2.value());
								declaredField.setAccessible(true);
								Class<?> clazzMeta = (Class<?>) declaredField.get(null); 
								
								Object invoke = getterMethodFrom.invoke(instanceFrom);

								logger.info(String.format("ANOTACAO: <METADADO> Converter classe anotada para %s em METADADO", invoke));

								if (invoke instanceof Collection<?>) {

									logger.info("ANOTACAO: <METADADO> Convertendo uma coleção VIA METADADO");

									Collection<Object> collectionConverted = convert(clazzMeta, invoke);

									setterMethodTo.invoke(instanceDestine, collectionConverted);
								} else if (invoke instanceof Object) {

									logger.info("ANOTACAO: <METADADO> Convertendo um objeto VIA METADADO");

									Object element = convert(invoke, clazzMeta);
									setterMethodTo.invoke(instanceDestine, element);
								}
								
								System.out.println(intanceMetaClass);
							}else{
								setterMethodTo.invoke(instanceDestine, getterMethodFrom.invoke(instanceFrom));
							}
						}
					} catch (NoSuchMethodException e) {
						logger.debug(e.getMessage());
					} catch (NoSuchFieldException e) {
						logger.warn(String.format("Campo %s da classe %s não encontrado!", e.getMessage(),
								instanceDestine.getClass().getSimpleName()));
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}

		logger.info("------------------------------------------------------");
		logger.info(" Fim da converção!");
		logger.info("------------------------------------------------------");

		return instanceDestine;
	}

	private static Annotation getBindingClass(Annotation[] annotation4Conversion) {
		for(Annotation ann : annotation4Conversion){
			if(ann instanceof BindingClass) return ann;
		}
		return null;
	}
	
	private static Annotation getBindingClassMeta(Annotation[] annotation4Conversion) {
		for(Annotation ann : annotation4Conversion){
			if(ann instanceof BindingClassMeta) return ann;
		}
		return null;
	}


	private static Collection<Object> convert(Class<?> convert2, Object invoke) {
		Collection<Object> collectionConverted = new ArrayList<Object>();
		for (Object elementFromCollection : (ArrayList) invoke) {
			Object element = convert(elementFromCollection, convert2);
			collectionConverted.add(element);
		}
		return collectionConverted;
	}

}
