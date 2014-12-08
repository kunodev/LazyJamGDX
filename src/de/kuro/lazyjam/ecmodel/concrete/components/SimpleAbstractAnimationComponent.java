package de.kuro.lazyjam.ecmodel.concrete.components;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

import de.kuro.lazyjam.asciiassetextension.IRectangleProvider;

/**
 * Basic Animation Compnent
 * @author kuro
 *
 */
public abstract class SimpleAbstractAnimationComponent {

	protected int xOffset;
	protected int state;
	protected ArrayList<ArrayList<IRectangleProvider>> renderableObjects;

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		this.xOffset = this.xOffset % renderableObjects.get(state).size();
	}

	public void incrementXOffset() {
		this.xOffset = (this.xOffset + 1) % renderableObjects.get(state).size();
	}

	public Rectangle getDefaultRectangle() {
		return renderableObjects.get(0).get(0).getRectangle();
	}
	
	public IRectangleProvider getCurrent() {
		return this.renderableObjects.get(state).get(xOffset);
	}

}
