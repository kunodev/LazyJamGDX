package de.kuro.lazyjam.cdiutils.context;

import de.kuro.lazyjam.ecmodel.concrete.GameObject;
import de.kuro.lazyjam.ecmodel.concrete.tools.Collision;

public class CollisionGameObjectContext extends ExtendableContext{
	
	public GameObjectContext backUpContext;
	
	public CollisionGameObjectContext(GameObjectContext otherContext, GameObject thisObject) {
		super(otherContext);
		super.addToContext(new Collision(otherContext, thisObject));
	}			
		
}
