package de.kuro.lazyjam.cdiutils.context;

import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;

public class GlobalContext implements ICallerContext{

	public ServiceManager serviceMan;
	
	public GlobalContext(ServiceManager serviceMan) {
		this.serviceMan = serviceMan;
	}
	
	@Override
	public <T> T getContextObject(Class<T> clazz) {
		return this.serviceMan.getService(clazz);
	}

}
