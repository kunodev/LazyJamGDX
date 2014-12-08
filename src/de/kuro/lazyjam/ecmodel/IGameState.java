package de.kuro.lazyjam.ecmodel;

import de.kuro.lazyjam.cdiutils.context.GlobalContext;
import de.kuro.lazyjam.ecmodel.concrete.GameObject;

/**
 * Defines a gamestate Interesting is what happens on the input and how often
 * this game state needs updates
 * 
 * @author kuro
 *
 */
public interface IGameState {

	/**
	 * Does update things
	 */
	public void onUpdate(GlobalContext globalContext, int deltaInMilliseconds);

	/**
	 * Does the rendering
	 */
	public void onRender(GlobalContext globalContext);

	/**
	 * returns the time to be update -1 if as fast as possible
	 * 
	 * @return
	 */
	public int getTickTimer();

	/**
	 * Does things that should happen on leavin ghte state, e.g. stopping music
	 */
	public void onLeaveState();

	/**
	 * Adds a gameobject with a tag if necessary
	 * @param gameObject
	 * @param tag
	 */
	public void addGameObject(GameObject gameObject, String tag);

	public Object getService(Class<?> clazz);

}
