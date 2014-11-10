package de.kuro.lazyjam.cdiutils.cdihelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.kuro.lazyjam.cdiutils.annotations.InjectedService;
import de.kuro.lazyjam.cdiutils.annotations.Service;

public class ServiceManager {

	private Map<Class<?>, Object> servicesObjects;
	private Map<List<Field>,Class<?>> injections;
	private Map<Class<?>, Class<?>> interfaces;

	public ServiceManager() {
		servicesObjects = new HashMap<Class<?>, Object>();
		injections = new HashMap<List<Field>,Class<?>>();
		interfaces = new HashMap<Class<?>, Class<?>>();
	}

	public void registerAsService(Object service) {
		Class<?> clazz = service.getClass();
		addServiceAndInject(clazz, service);
	}
	
	public void registerInterfaceMapping(Class<?> iface, Class<?> serviceClass) {
		this.interfaces.put(iface, serviceClass); //TODO: sanityChecks?
	}

	public <T> T getService(Class<T> clazz) {
		if(servicesObjects.containsKey(clazz)) {
			return clazz.cast(servicesObjects.get(clazz));
		}
		if(interfaces.containsKey(clazz)) {
			return clazz.cast(servicesObjects.get(interfaces.get(clazz)));
		}
		T specialImplementationService = searchClassHierarchy(clazz);
		return specialImplementationService;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T searchClassHierarchy(Class<T> clazz) {
		for(Entry<Class<?>, Object> entry : this.servicesObjects.entrySet()) {
			Class<?> possibleResult = entry.getKey();
			while(possibleResult != null) {
				if(clazz == possibleResult) {
					return (T) entry.getValue();
				}
				possibleResult = possibleResult.getSuperclass();
			}
		}
		return null;
	}

	public void searchForServices() {
		Set<Class<?>> classes = ReflectionUtil.reflect.getTypesAnnotatedWith(Service.class);
		for (Class<?> clazz : classes) {
			addServiceAndInject(clazz, null);
		}
	}
	
	private void addServiceAndInject(Class<?> clazz, Object o) {
		try {
			Object service = o;
			if(o == null) {
				service = clazz.newInstance();
			}
			servicesObjects.put(clazz, service);
			injectIntoNew(service);
			injectIntoOlds(service);
			addNewLists(clazz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void addNewLists(Class<?> clazz) {
		List<Field> injectorFields = new ArrayList<Field>();
		for(Field f : clazz.getFields()) {
			if(f.isAnnotationPresent(InjectedService.class)) {
				injectorFields.add(f);
			}
		}
		this.injections.put(injectorFields, clazz);
		
	}

	private void injectIntoOlds(Object service) throws IllegalArgumentException, IllegalAccessException {
		for(Entry<List<Field>,Class<?>> entries : injections.entrySet()) {
			for(Field f : entries.getKey()) {
				if( f.getType() == service.getClass()) {
					f.setAccessible(true);
					f.set(servicesObjects.get(entries.getValue()), service);
				}
			}
		}
		
	}

	private void injectIntoNew(Object service) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = service.getClass().getFields();
		for(Field field : fields) {
			if(field.isAnnotationPresent(InjectedService.class)) {
				Class<?> typeOfField = field.getType();
				if(servicesObjects.containsKey(typeOfField)) {
					field.set(service, servicesObjects.get(typeOfField));
				}
				//TODO: i could also look if i have an extension of the injected service... not sure
			}
		}
		
	}

}
