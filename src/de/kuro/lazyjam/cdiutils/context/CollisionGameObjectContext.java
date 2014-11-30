package de.kuro.lazyjam.cdiutils.context;

import de.kuro.lazyjam.ecmodel.concrete.GameObject;
import de.kuro.lazyjam.ecmodel.concrete.tools.Collision;

public class CollisionGameObjectContext implements ICallerContext{
	
	public GameObjectContext backUpContext;
	public Collision c;
	
	public CollisionGameObjectContext(GameObjectContext otherContext, GameObject thisObject) {
		this.backUpContext = new GameObjectContext(otherContext.gsc, thisObject);
		this.c = new Collision(otherContext, thisObject);
	}			
			
	@Override
	public <T> T getContextObject(Class<T> clazz) {
		if(clazz.isInstance(c)) {
			return (T) c;
		}
		return backUpContext.getContextObject(clazz);
	}
		
	}
