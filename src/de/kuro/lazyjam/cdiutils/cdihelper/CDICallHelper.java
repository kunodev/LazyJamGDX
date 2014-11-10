package de.kuro.lazyjam.cdiutils.cdihelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.kuro.lazyjam.cdiutils.context.ICallerContext;

public class CDICallHelper {
	
	public static void callOnObject(ICallerContext context, 
			Class<? extends Annotation> annoClazz, Object o) {
			Method m = getMethod(o, annoClazz);
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
}
