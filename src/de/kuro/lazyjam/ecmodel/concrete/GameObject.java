package de.kuro.lazyjam.ecmodel.concrete;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Render;
import de.kuro.lazyjam.cdiutils.annotations.Update;
import de.kuro.lazyjam.cdiutils.cdihelper.CDICallHelper;
import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.cdiutils.context.GameObjectContext;
import de.kuro.lazyjam.cdiutils.context.GameStateContext;
import de.kuro.lazyjam.ecmodel.IGameState;

public class GameObject {

	private Vector2 pos;
	private String tag;
	private List<Object> components;

	public GameObject(Vector2 pos, IGameState gs) {
		this(pos, null, gs);
	}

	public GameObject(Vector2 pos, String tag, IGameState gs) {
		this.tag = tag;
		this.setPos(pos);
		components = new ArrayList<Object>();
		gs.addGameObject(this, tag);
	}

	public void onUpdate(GameStateContext gsc) {
		GameObjectContext goc = new GameObjectContext(gsc, this);
		for(Object comp : this.components) {
			CDICallHelper.callOnObject(goc, Update.class, comp);
		}
	}

	public void onRender(GameStateContext gsc) {
		GameObjectContext goc = new GameObjectContext(gsc, this);
		for(Object comp : this.components) {
			CDICallHelper.callOnObject(goc, Render.class, comp);
		}
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}
	
	public void addComponent(Object comp) {
		this.components.add(comp);
	}

	public <T> T getComponent(Class<T> clazz) {
		if (this.components.stream().filter(l -> clazz.isInstance(l)).count() > 0) {
			T result = clazz.cast(this.components.stream().filter(l -> clazz.isInstance(l)).findFirst().get());
			return result;
		} 
		return null;
	}

	public void selfDestruct(GameState gs) {
		gs.removeGameObject(this, tag);
	}
	
	public List<Object> getComponents() {
		return components;
	}
}
