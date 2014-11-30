package de.kuro.lazyjam.ecmodel.concrete.tools;

import de.kuro.lazyjam.cdiutils.context.GameObjectContext;
import de.kuro.lazyjam.ecmodel.concrete.GameObject;

public class Collision {
	public final GameObject thisGo;
	public final GameObject otherGo;
	
	public Collision(GameObjectContext otherContext, GameObject thisObject) {
		thisGo = thisObject;
		otherGo = otherContext.go;
	}
}
