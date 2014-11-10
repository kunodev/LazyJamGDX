package de.kuro.lazyjam.ecmodel.concrete;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.cdiutils.context.GameStateContext;
import de.kuro.lazyjam.cdiutils.context.GlobalContext;
import de.kuro.lazyjam.ecmodel.AGameState;
import de.kuro.lazyjam.ecmodel.concrete.tools.QueueSet;
import de.kuro.lazyjam.ecmodel.concrete.tools.VectorDistanceComparator;

public class GameState extends AGameState {

	public Collection<GameObject> gameObjects;
	public Map<String, Collection<GameObject>> taggedGameObjects;
	
	public GameState() {
		taggedGameObjects = new HashMap<String, Collection<GameObject>>();
		gameObjects = new QueueSet<GameObject>(this);
	}

	@Override
	public void onRender(GlobalContext globalContext) {
//		if (map != null) {
//			map.render(cam.getX() % serviceMan.getService(Settings.class).getInt("TILE_SIZE"), cam.getY()
//					% serviceMan.getService(Settings.class).getInt("TILE_SIZE"),
//					cam.getX() / serviceMan.getService(Settings.class).getInt("TILE_SIZE"), cam.getY()
//							/ serviceMan.getService(Settings.class).getInt("TILE_SIZE"),
//					(serviceMan.getService(Settings.class).getInt("SCREENWIDTH") / serviceMan.getService(Settings.class).getInt("TILE_SIZE")) + 2,
//					(serviceMan.getService(Settings.class).getInt("SCREENHEIGHT") / serviceMan.getService(Settings.class).getInt("TILE_SIZE")) + 2);
//			// //
//			// map.render(cam.getX(), cam.getY());
//			// map.render(0, 0, 0, 0, 1024, 768);
//		} TODO: Tiled Support
		GameStateContext gsc = new GameStateContext(globalContext, this);
		gameObjects.stream().forEach(e -> e.onRender(gsc));
	}

	@Override
	protected void update(GlobalContext globalContext) {
		updateAbles.stream().forEach(e -> e.run());
		GameStateContext gsc = new GameStateContext(globalContext, this);
		for (GameObject go : gameObjects) {
			go.onUpdate(gsc);
		}
	}

	public void addGameObject(GameObject go) {
		gameObjects.add(go);
	}

	public void removeGameObject(GameObject go, String tag) {
		gameObjects.remove(go);
		if (tag != null) {
			taggedGameObjects.get(tag).remove(go);
		}
	}

	public void addGameObject(GameObject gameObject, String tag) {
		this.addGameObject(gameObject);
		this.addTag(gameObject, tag);
	}

	public GameObject getFirstTaggedGameObject(String tag) {
		return this.taggedGameObjects.get(tag).stream().findFirst().get();
	}

	public Collection<GameObject> getTaggedGameObjects(String tag) {
		Collection<GameObject> result = this.taggedGameObjects.get(tag);
		if (result == null) {
			return Collections.emptyList();
		} else {
			return result;
		}
	}

	public void addTag(GameObject gameObject, String tag) {
		if(tag == null) {
			return;
		}
		if (this.taggedGameObjects.get(tag) == null) {
			this.taggedGameObjects.put(tag, new QueueSet<GameObject>(this));
		}
		this.taggedGameObjects.get(tag).add(gameObject);
	}

//	public void setMap(TiledMap map) {
//		this.map = map;
//	}

	public void registerUpdateable(Runnable r) {
		this.updateAbles.add(r);
	}

	public List<GameObject> getGameObjectsInRange(Vector2 pos, int width, int height) {
		return getGameObjectsInRange(pos, width, height, gameObjects);
	}

	public List<GameObject> getGameObjectsInRange(Vector2 pos, int width, int height, String tag) {
		Collection<GameObject> tagged = taggedGameObjects.get(tag);
		if (tagged == null) {
			return Collections.emptyList();
		}
		return getGameObjectsInRange(pos, width, height, tagged);
	}

	public List<GameObject> getGameObjectsInRange(Vector2 pos, int width, int height, Collection<GameObject> list) {
		Rectangle range = new Rectangle(pos.x, pos.y, width, height);
		Stream<GameObject> filter = list.stream().filter(e -> range.contains(e.getPos().x, e.getPos().y));
		return filter.collect(Collectors.toList());
	}

	public List<GameObject> getClosestGameObject(Vector2 pos) {
		return getClosestGameObject(pos, gameObjects);
	}

	public List<GameObject> getClosestGameObject(Vector2 pos, String tag) {
		Collection<GameObject> tagged = taggedGameObjects.get(tag);
		if (tagged == null) {
			return Collections.emptyList();
		}
		return getClosestGameObject(pos, tagged);
	}

	public List<GameObject> getClosestGameObject(Vector2 pos, Collection<GameObject> list) {
		return list.stream().sorted(new VectorDistanceComparator(pos)).collect(Collectors.toList());
	}

}
