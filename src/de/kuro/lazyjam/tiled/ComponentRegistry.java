package de.kuro.lazyjam.tiled;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.reflections.Reflections;

import de.kuro.lazyjam.cdiutils.annotations.Component;
import de.kuro.lazyjam.cdiutils.annotations.Service;
import de.kuro.lazyjam.cdiutils.cdihelper.ReflectionUtil;


@Service
public class ComponentRegistry {
	
	private Map<String, Class<?>> componentRegistry;
	
	public ComponentRegistry() {
		this.componentRegistry = new TreeMap<String, Class<?>>();
		this.loadComponents();
	}

	public void loadComponents() {
		Set<Class<?>> classes = ReflectionUtil.reflect.getTypesAnnotatedWith(Component.class, true);
		for (Class<?> c : classes) {
			try {
				Component anno = c.getAnnotation(Component.class);
				componentRegistry.put(anno.name(), c);
			} catch (IllegalArgumentException | SecurityException e) {
				System.err.println(c.getName());
				e.printStackTrace();
			}
		}
	}


	public Set<String> getPossibleComponentNames() {
		// List<String> names = new ArrayList<String>();
		// return names;
		return componentRegistry.keySet();
	}

	public Class<?> getComponentClass(String possibleComponentName) {
		return this.componentRegistry.get(possibleComponentName);
	}

}
