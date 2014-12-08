package de.kuro.lazyjam.cdiutils.context;

import java.util.ArrayList;
import java.util.List;

/**
 * A Context that defines a list of Objects it adds and a fallback context,
 * 
 * @author kuro
 *
 */
public class ExtendableContext implements ICallerContext{
	
	public List<Object> extensions;
	public ICallerContext backup;


	public ExtendableContext(ICallerContext backUp) {
		this.backup = backUp;
		this.extensions = new ArrayList<Object>();
	}
	
	public void addToContext(Object... objs) {
		for(Object obj : objs) {
			this.extensions.add(obj);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getContextObject(Class<T> clazz) {
		for(Object obj : this.extensions) {
			if(clazz.isInstance(obj)) {
				return (T) obj;
			}
		}
		return backup == null ? null : backup.getContextObject(clazz);
	}
	
}
