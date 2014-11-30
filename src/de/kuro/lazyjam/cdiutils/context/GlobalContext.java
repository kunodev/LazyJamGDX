package de.kuro.lazyjam.cdiutils.context;

import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.ecmodel.GameStateContextManager;

public class GlobalContext implements ICallerContext{

	public ServiceManager serviceMan;
	public GameStateContextManager gscm;
	
	public GlobalContext(ServiceManager serviceMan, GameStateContextManager gscm) {
		this.serviceMan = serviceMan;
		this.gscm = gscm;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getContextObject(Class<T> clazz) {
		if(clazz.isInstance(gscm)) {
			return (T) gscm;
		}
		if(clazz.isInstance(serviceMan)) {
			return (T) serviceMan;
		}
		return this.serviceMan.getService(clazz);
	}

}
