package de.kuro.lazyjam.cdiutils.cdihelper;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class ReflectionUtil {
	
	public static Reflections reflect;
	public static final String SAVE_FILE = "reflectionStore.sav";
	
	public static void init() {
		reflect = new Reflections("de.kuro.lazyjam",new SubTypesScanner(false), new TypeAnnotationsScanner());
		addReflection("");
		//Seriously... have to do something about this?
	}
	
	public static void addReflection(String packageName) {
		Reflections otherReflect = new Reflections(packageName,new SubTypesScanner(false), new TypeAnnotationsScanner() );
		reflect.merge(otherReflect);
//		save();
	}
	
	public static void save() {
		reflect.save(SAVE_FILE); //TODO: use?
	}

}
	
	
