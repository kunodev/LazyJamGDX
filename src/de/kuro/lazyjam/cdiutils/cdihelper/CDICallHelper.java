package de.kuro.lazyjam.cdiutils.cdihelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import de.kuro.lazyjam.cdiutils.context.ICallerContext;

public class CDICallHelper {
	
	public static void callOnObject(ICallerContext context, 
			Class<? extends Annotation> annoClazz, Object o) {
			Method m = getMethod(o, annoClazz);
			invokeMethod(context, o, m);
	}

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
	
	public static void callOnObjectWithHierarchy(ICallerContext context, 
			Class<? extends Annotation> annoClazz, Object o) {
		List<Method> methods = getMethodsIncludingHierarchy(o, annoClazz);
		for(Method m : methods) {
			invokeMethod(context, o, m);
		}
	}
	
	private static Object[] getParams(Class<?>[] parameterTypes, ICallerContext context) {
		Object[] result = new Object[parameterTypes.length];
		for(int i=0;i<parameterTypes.length;i++) {
			result[i] = context.getContextObject(parameterTypes[i]);
		}
		return result;
	}

	private static Method getMethod(Object comp, Class<? extends Annotation> annotation) {
		for(Method m : comp.getClass().getMethods()) {
			if(m.getAnnotation(annotation) != null) {
				return m;
			}
		}
		return null;
	}
	
	public static List<Method> getMethodsIncludingHierarchy(Object comp, Class<? extends Annotation> annotation) {
		LinkedList<Method> result = new LinkedList<Method>();
		Class<?> current = comp.getClass();
		while(current != null) {
			result.addLast(getMethod(comp, annotation));
			current = current.getSuperclass();
		}
		return result;
	}
}
