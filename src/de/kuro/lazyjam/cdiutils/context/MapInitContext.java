package de.kuro.lazyjam.cdiutils.context;
/**
 * Contains GameObjectContext and String from TiledMap
 * @author kuro
 *
 */
public class MapInitContext implements ICallerContext{

	public GameObjectContext goc;
	public String mapInit;
	
	public MapInitContext(GameObjectContext goc, String mapInit) {
		this.goc = goc;
		this.mapInit = mapInit;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getContextObject(Class<T> clazz) {
		if(clazz.isInstance(mapInit)) {
			return (T) mapInit;
		} else {
			return goc.getContextObject(clazz);
		}
	}

}
