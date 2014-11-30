package de.kuro.lazyjam.ecmodel;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.kuro.lazyjam.cdiutils.cdihelper.ServiceManager;
import de.kuro.lazyjam.cdiutils.context.GlobalContext;


public class GameStateContextManager {

	private Map<Class<? extends IGameState>, IGameState> gameStates;
	ServiceManager serviceMan;
	IGameState activeState;
	IGameState mainState;

	public GameStateContextManager(ServiceManager serviceMan) {
		gameStates = new HashMap<Class<? extends IGameState>, IGameState>();
		this.serviceMan = serviceMan;
		this.serviceMan.registerAsService(Gdx.input);
		this.serviceMan.registerInterfaceMapping(Input.class, Gdx.input.getClass());
	}

	public void addGameState(Class<? extends IGameState> key, IGameState state) {
		if (gameStates.containsKey(key)) {
			System.err.println("Key " + key + " is duplicated!");
			return;
		}
		this.gameStates.put(key, state);
	}
	
	public void removeGameState(Class<? extends IGameState> key) {
		if (gameStates.containsKey(key)) {
			if (gameStates.get(key) != activeState) {
				gameStates.remove(key);
			}
		}
	}
	
	public void initMainGameState(IGameState gs) {
		this.mainState = gs;
		this.activeState = gs;
	}

	public void setGameState(Class<? extends IGameState> class1) {
		if (this.activeState != null) {
			activeState.onLeaveState();
		}
		this.activeState = gameStates.get(class1);
	}

	public IGameState getGameState() {
		return this.activeState;
	}

	public <T> T getGameStateAs(Class<T> t) {
		return t.cast(this.activeState);
	}

	public <T> T getGameStateAs(Class<T> t, int gamestateId) {
		return t.cast(this.gameStates.get(gamestateId));
	}

	public void render() {
		if(activeState != null) {
			activeState.onRender(new GlobalContext(this.serviceMan, this));
		}
	}

	public void update(int deltaInMilliseconds) {
		if(activeState != null) {
			activeState.onUpdate(new GlobalContext(this.serviceMan, this), deltaInMilliseconds);
		}
	}

	public <T extends IGameState> T getMainGameState(Class<T> clazz) {
		return clazz.cast(mainState);
	}

	public void setServiceManager(ServiceManager serviceMan) {
		this.serviceMan = serviceMan;
		
	}
}
