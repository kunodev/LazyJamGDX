package de.kuro.lazyjam.ecmodel.concrete.components;

import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Update;
import de.kuro.lazyjam.ecmodel.concrete.GameObject;

/**
 * Keeps on gameobject at a fixed distance to another
 * used to simulate "Child" objects
 * @author kuro
 *
 */
public class RelativityComponent {
	
	public Vector2 offset;
	public GameObject parent;
	
	public RelativityComponent() {
		offset = new Vector2();
	}
	
	@Update
	public void setPosition(Vector2 pos) {
		pos.x = parent.getPos().x + offset.x;
		pos.y = parent.getPos().y + offset.y;
	}

}
