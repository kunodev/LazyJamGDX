package de.kuro.lazyjam.ecmodel.concrete;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.Collide;
import de.kuro.lazyjam.cdiutils.annotations.Destroy;
import de.kuro.lazyjam.cdiutils.annotations.Render;
import de.kuro.lazyjam.cdiutils.annotations.Update;
import de.kuro.lazyjam.cdiutils.cdihelper.CDICallHelper;
import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.cdiutils.context.CollisionGameObjectContext;
import de.kuro.lazyjam.cdiutils.context.GameObjectContext;
import de.kuro.lazyjam.cdiutils.context.GameStateContext;
import de.kuro.lazyjam.ecmodel.IGameState;
import de.kuro.lazyjam.ecmodel.concrete.tools.Collision;

public class GameObject {

	private Vector2 pos;
	private String tag;
	private List<Object> components;
	
	private GameObjectContext goc;

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
		goc = new GameObjectContext(gsc, this);
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
	
	public void callCollide(GameObjectContext otherGOContext) {
		CollisionGameObjectContext cgoc = new CollisionGameObjectContext(otherGOContext, this);
		this.components.stream().forEach(e -> CDICallHelper.callOnObject(cgoc, Collide.class, e));
	}

	public void selfDestruct(GameState gs) {
		gs.removeGameObject(this, tag);
		this.components.stream().forEach(e -> CDICallHelper.callOnObject(goc, Destroy.class, e));	
	}
	
	public List<Object> getComponents() {
		return components;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("GAMEOBJECT \n");
		this.components.stream().forEach(e -> sb.append(e.getClass().toString() + "\n"));
		sb.append("\n");
		return sb.toString();
		
	}
}
