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
import de.kuro.lazyjam.cdiutils.context.ExtendableContext;
import de.kuro.lazyjam.cdiutils.context.GameObjectContext;
import de.kuro.lazyjam.cdiutils.context.GameStateContext;
import de.kuro.lazyjam.cdiutils.context.ICallerContext;
import de.kuro.lazyjam.ecmodel.IGameState;
import de.kuro.lazyjam.ecmodel.concrete.components.RelativityComponent;
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

	public void onUpdate(ICallerContext gsc) {
		goc = new GameObjectContext(gsc, this);
		callUpdateOnChildren(goc);
	}

	private void callUpdateOnChildren(ICallerContext ec) {
		for(Object comp : this.components) {
			CDICallHelper.callOnObjectWithHierarchy(ec, Update.class, comp);
		}
	}

	public void onRender(GameStateContext gsc) {
		GameObjectContext goc = new GameObjectContext(gsc, this);
		renderWithContext(goc);
	}

	private void renderWithContext(ICallerContext goc) {
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
	
	@Update
	public void onUpdate(GameObjectContext goc) {
		GameObjectContext childContext = new GameObjectContext(goc, this);
		this.callUpdateOnChildren(childContext);
	}
	
	@Render
	public void onRender(GameObjectContext goc) {
		Vector2 combinedPos = new Vector2();
		combinedPos.set(this.pos);
		combinedPos.add(goc.go.getPos());
		ExtendableContext ec = new ExtendableContext(goc);
		this.renderWithContext(ec);
	}
	
	public GameObject createChild(IGameState gs) {
		GameObject child = new GameObject(new Vector2(), gs);
		RelativityComponent comp = new RelativityComponent();
		child.addComponent(comp);
		comp.parent = this;
		this.addComponent(child);
		return child;
	}
	
}
