package de.kuro.lazyjam.cdiutils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this if you need initializations of tiled map compnents, 
 * the value of the property  can be injected as a string
 * also you have most context access
 * @author kuro
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ComponentInit {}
