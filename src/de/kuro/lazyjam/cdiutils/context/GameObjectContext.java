package de.kuro.lazyjam.cdiutils.context;

import de.kuro.lazyjam.ecmodel.concrete.GameObject;

public class GameObjectContext implements ICallerContext{
	
	public GameStateContext gsc;
	public GameObject go;

	public GameObjectContext(GameStateContext gsc, GameObject go) {
		this.gsc = gsc;
		this.go = go;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getContextObject(Class<T> clazz) {
		if(clazz.isInstance(this)) {
			return (T) this;
		}
		if(clazz.isInstance(go)) {
			return (T) go;
		} 
		if(clazz.isInstance(go.getPos())) {
			return (T) go.getPos();
		}
		for(Object silbing : go.getComponents()) {
			if(clazz.isInstance(silbing)) {
				return (T) silbing;
			}
		}
		return gsc.getContextObject(clazz);
	}

}
