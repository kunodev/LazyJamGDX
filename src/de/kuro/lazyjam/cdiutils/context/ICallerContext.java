package de.kuro.lazyjam.cdiutils.context;

import java.util.Collection;

/**
 * This Interface defines a function that returns an object when you want something of class XY
 * TODO: maybe a simpler way, like an abstract class, something like FallBackCallerContext
 * currently its with the decorator pattern a strict hierarchy MapInit -> GameObject -> GameState -> Global 
 * @author kuro
 *
 */
public interface ICallerContext {
	
	public <T> T getContextObject(Class<T> clazz);
	
}
