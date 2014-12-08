package de.kuro.lazyjam.cdiutils.context;

import de.kuro.lazyjam.ecmodel.IGameState;

public class GameStateContext implements ICallerContext {

	private GlobalContext globalContext;
	public IGameState gs; 
	
	public GameStateContext(GlobalContext gc, IGameState gs) {
		this.globalContext = gc;
		this.gs = gs;
	}

	@Override
	public <T> T getContextObject(Class<T> clazz) {
		if(clazz.isInstance(gs)) {
			return (T) gs;
		}
		if(gs.getService(clazz) != null) {
			return (T) gs.getService(clazz);
		}
		return globalContext.getContextObject(clazz);
	}
	

}
