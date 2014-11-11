package de.kuro.lazyjam.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import de.kuro.lazyjam.cdiutils.cdihelper.ReflectionUtil;
import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.cdiutils.context.GameStateContext;
import de.kuro.lazyjam.ecmodel.GameStateContextManager;
import de.kuro.lazyjam.settings.Constants;
import de.kuro.lazyjam.simpleservice.FontManager;
import de.kuro.lazyjam.tiled.TiledMapProvider;

public abstract class LazyJamApplicationAdapter extends ApplicationAdapter {

	public SpriteBatch batch;
	public GameStateContextManager gscm;
	public AssetManager assetManager;
	public ServiceManager serviceMan;
	
	public Camera initCam() {
		OrthographicCamera cam = new OrthographicCamera();
		cam.setToOrtho(false, 1024, 768);
		return cam;
	}
	
	public abstract void loadAssets();
	
	@Override
	public void create() {
		//Should be done everytime i guess?
		batch = new SpriteBatch();
		serviceMan = new ServiceManager();
		serviceMan.registerAsService(batch);
		gscm = new GameStateContextManager(serviceMan);
		assetManager = new AssetManager();
		serviceMan.registerAsService(assetManager);
		serviceMan.registerAsService(initCam());
		// only needed once
		this.loadAssets();
		ReflectionUtil.init();
		serviceMan.searchForServices();
	}
	
	@Override
	public void render () {
		int deltaInMs = (int) (Gdx.graphics.getDeltaTime() * 1000);
		gscm.update(deltaInMs);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		serviceMan.getService(TiledMapProvider.class).render();
		batch.begin();
		gscm.render();

		batch.end();
	}
	
	public void setMap(String map, GameStateContext gsc) {
		serviceMan.getService(TiledMapProvider.class).loadMap(map, gsc);
	}
}
