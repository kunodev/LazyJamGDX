package de.kuro.lazyjam.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.kuro.lazyjam.cdiutils.cdihelper.ReflectionUtil;
import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.cdiutils.context.GameStateContext;
import de.kuro.lazyjam.ecmodel.GameStateContextManager;
import de.kuro.lazyjam.tiled.TiledMapProvider;
/**
 * Main Class extend this to start off
 * @author kuro
 *
 */
public abstract class LazyJamApplicationAdapter extends ApplicationAdapter {

	public SpriteBatch batch;
	public GameStateContextManager gscm;
	public AssetManager assetManager;
	public ServiceManager serviceMan;

	//debug
	//private FPSLogger fps = new FPSLogger();

	private final float width;
	private final float height;
	
	private OrthographicCamera cam;

	public LazyJamApplicationAdapter(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public Camera initCam() {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
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
		assetManager.finishLoading();
		ReflectionUtil.init();
		serviceMan.searchForServices();
	}
	
	@Override
	public void render () {
		int deltaInMs = (int) (Gdx.graphics.getDeltaTime() * 1000);
		gscm.update(deltaInMs);
		
		Gdx.gl.glClearColor(3/255.f, 10/255.f, 20/255.f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		serviceMan.getService(TiledMapProvider.class).render();
		batch.begin();
		batch.setProjectionMatrix(cam.combined);
		gscm.render();

		batch.end();
		//fps.log();
	}
	
	public void setMap(String map, GameStateContext gsc) {
		serviceMan.getService(TiledMapProvider.class).loadMap(map, gsc);
	}
}
