package de.kuro.lazyjam.cdiutils.context;

import de.kuro.lazyjam.ecmodel.concrete.GameObject;

public class GameObjectContext implements ICallerContext{
	
	public ICallerContext gsc;
	public GameObject go;

	public GameObjectContext(ICallerContext gsc, GameObject go) {
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
		if(gsc == this) {
			System.out.println("youre doing shizzle!");
		}
		return gsc.getContextObject(clazz);
	}

}
