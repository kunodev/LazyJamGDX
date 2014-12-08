package de.kuro.lazyjam.cdiutils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This Annotation marks something as a Service 
 * (Basically a way to define a super accessible singleton in one line)
 * @author kuro
 *
 */
@Retention(RetentionPolicy.RUNTIME )
@Target(ElementType.TYPE_USE)
public @interface Service { }
