package de.kuro.lazyjam.ecmodel.concrete.tools;

import java.lang.reflect.Field;

import de.kuro.lazyjam.cdiutils.annotations.InjectedService;
import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;

/**
 * Same As serviceManager, but can inject global services into gamestate scoped ones
 * @author kuro
 *
 */
public class GameStateServiceManager extends ServiceManager {
	
	protected ServiceManager globalService;
	
	public GameStateServiceManager(ServiceManager serviceMan) {
		this.globalService = serviceMan;
	}
	
	protected void injectIntoNew(Object service) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = service.getClass().getFields();
		for(Field field : fields) {
			if(field.isAnnotationPresent(InjectedService.class)) {
				Class<?> typeOfField = field.getType();
				if(servicesObjects.containsKey(typeOfField)) {
					field.set(service, servicesObjects.get(typeOfField));
				} else {
					if(globalService.getService(typeOfField) != null) {
						field.set(service, servicesObjects.get(typeOfField));
					}
				}
				//TODO: i could also look if i have an extension of the injected service... not sure
			}
		}
	}

}
