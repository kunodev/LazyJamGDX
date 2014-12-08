package de.kuro.lazyjam.cdiutils.cdihelper;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
/**
 * Does nothing but statically keep the Reflections Object
 * @author kuro
 *
 */
public class ReflectionUtil {
	
	public static Reflections reflect;
	public static final String SAVE_FILE = "reflectionStore.sav";
	
	/**
	 * Inits the lazyjam project aqnd everything on the direct classpath
	 * WARNING: If you build a fat jar, 
	 * you NEED TO change the addReflection call to call some subpackage of your project
	 * else every single jarfile known will be reflected
	 */
	public static void init() {
		reflect = new Reflections("de.kuro.lazyjam",new SubTypesScanner(false), new TypeAnnotationsScanner());
		addReflection("");
		//Seriously... have to do something about this?
	}
	/**
	 * Adds another package
	 * @param packageName
	 */
	public static void addReflection(String packageName) {
		Reflections otherReflect = new Reflections(packageName,new SubTypesScanner(false), new TypeAnnotationsScanner() );
		reflect.merge(otherReflect);
//		save();
	}
	/**
	 * unused
	 */
	public static void save() {
		reflect.save(SAVE_FILE); //TODO: use?
	}

}
	
	
