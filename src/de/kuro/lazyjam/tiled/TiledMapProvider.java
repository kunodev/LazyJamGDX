package de.kuro.lazyjam.tiled;

import java.util.Iterator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import de.kuro.lazyjam.cdiutils.annotations.ComponentInit;
import de.kuro.lazyjam.cdiutils.annotations.InjectedService;
import de.kuro.lazyjam.cdiutils.annotations.Service;
import de.kuro.lazyjam.cdiutils.cdihelper.CDICallHelper;
import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.cdiutils.context.GameObjectContext;
import de.kuro.lazyjam.cdiutils.context.GameStateContext;
import de.kuro.lazyjam.cdiutils.context.GlobalContext;
import de.kuro.lazyjam.cdiutils.context.MapInitContext;
import de.kuro.lazyjam.ecmodel.concrete.GameObject;
import de.kuro.lazyjam.settings.Constants;

@Service
public class TiledMapProvider {

	public TiledMap map;
	public TiledMapRenderer renderer;
	
	@InjectedService
	public OrthographicCamera cam;
	
	@InjectedService
	public AssetManager assetMan;
	
	public void loadMap(String file, GameStateContext gsc) {
		map = assetMan.get(file);
		this.renderer = new OrthogonalTiledMapRenderer(map); //TODO: instantiate other?
		spawnGameObjects(gsc);
		
	}
	
	public void spawnGameObjects(GameStateContext gsc) {
		ComponentRegistry components = gsc.getContextObject(ComponentRegistry.class);
		for(MapLayer layer : map.getLayers()) {
			for(MapObject obj : layer.getObjects()) {
				if(obj instanceof RectangleMapObject) {
					RectangleMapObject robj = (RectangleMapObject) obj;
					GameObject spawnedGO;
					if(robj.getProperties().containsKey(Constants.COMPONENT_TAG)) {
						spawnedGO = new GameObject(robj.getRectangle()
								.getCenter(new Vector2()),
								robj.getProperties().get(Constants.COMPONENT_TAG, String.class),
								gsc.gs);
					} else {
						spawnedGO = new GameObject(robj.getRectangle()
								.getCenter(new Vector2()),
								gsc.gs);
					}
					Iterator<String> keys = robj.getProperties().getKeys();
					GameObjectContext goc = new GameObjectContext(gsc, spawnedGO);
					while(keys.hasNext()) {
						String key = keys.next();
						Class<?> compClass = components.getComponentClass(key);
						if( compClass != null) {
							try {
								Object newComp = compClass.newInstance();
								spawnedGO.addComponent(newComp);
								MapInitContext mic = new MapInitContext(goc, robj.getProperties().get(key,String.class));
								CDICallHelper.callOnObject(mic, ComponentInit.class, newComp);
							} catch (InstantiationException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}							
						}
					}
				}
				//TODO: ignore anything not rectangle? seems fair...
			}
		}
	}
	
	public void render() {
		if(map != null) {
			cam.update(); 
			renderer.setView(cam);
			renderer.render();
		}
	}
}
