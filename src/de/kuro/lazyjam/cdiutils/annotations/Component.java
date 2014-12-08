package de.kuro.lazyjam.cdiutils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Use this annotation if you create compnents to be loaded from a tiledmap
 * the name is mapped to the key of the tiled object properties
 * @author kuro
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
	public String name();
}
