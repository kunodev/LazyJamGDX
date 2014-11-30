package de.kuro.lazyjam.ecmodel.concrete.components;

import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Update;

public class VelocityComponent {
	
	public Vector2 v;
	
	
	public VelocityComponent() {
		this.v = new Vector2();
	}
	
	@Update
	public void addToPos(Vector2 pos) {
		pos.add(v);
	}
	

}
