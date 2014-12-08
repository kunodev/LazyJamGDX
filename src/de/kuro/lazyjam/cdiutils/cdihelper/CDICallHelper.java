package de.kuro.lazyjam.cdiutils.cdihelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import de.kuro.lazyjam.cdiutils.context.ICallerContext;

public class CDICallHelper {
	
	/**
	 * MAin interface to the outside
	 * 
	 * @param context the context, every Class that is known to this will be avaiable as a parameter
	 * @param annoClazz what mark on the method you want to call
	 * @param o which object to call on
	 */
	public static void callOnObject(ICallerContext context, 
			Class<? extends Annotation> annoClazz, Object o) {
			Method m = getMethod(o.getClass(), annoClazz);
			invokeMethod(context, o, m);
	}

	/**
	 * Catches exceptions and wont happen if no method can be found
	 * @param context
	 * @param o
	 * @param m
	 */
	private static void invokeMethod(ICallerContext context, Object o, Method m) {
		if(m != null) {
			Object[] params = getParams(m.getParameterTypes(), context);
			try {
				m.invoke(o, params);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * The same as the callOnObject method, 
	 * but will call every update marked method that can be found in the type hierarchy
	 * @param context
	 * @param annoClazz
	 * @param o
	 */
	public static void callOnObjectWithHierarchy(ICallerContext context, 
			Class<? extends Annotation> annoClazz, Object o) {
		List<Method> methods = getMethodsIncludingHierarchy(o, annoClazz);
		for(Method m : methods) {
			invokeMethod(context, o, m); 
		}
	}
	/**
	 * Gets the parameters of the method
	 * @param parameterTypes
	 * @param context
	 * @return
	 */
	private static Object[] getParams(Class<?>[] parameterTypes, ICallerContext context) {
		Object[] result = new Object[parameterTypes.length];
		for(int i=0;i<parameterTypes.length;i++) {
			result[i] = context.getContextObject(parameterTypes[i]);
		}
		return result;
	}
	/**
	 * Checks for annotation of the class
	 * @param compClassOrSuper
	 * @param annotation
	 * @return
	 */
	
	private static Method getMethod(Class<?> compClassOrSuper, Class<? extends Annotation> annotation) {
		for(Method m : compClassOrSuper.getMethods()) {
			if(m.getAnnotation(annotation) != null) {
				return m;
			}
		}
		return null;
	}
	/**
	 * same as getMethod but uses while loop to check hierarchy
	 * @param comp
	 * @param annotation
	 * @return
	 */
	public static List<Method> getMethodsIncludingHierarchy(Object comp, Class<? extends Annotation> annotation) {
		LinkedList<Method> result = new LinkedList<Method>();
		Class<?> current = comp.getClass();
		while(current != null) {
			result.addLast(getMethod(current, annotation));
			current = current.getSuperclass();
		}
		return result;
	}
}
