package de.kuro.lazyjam.cdiutils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * To prevent egg-chicken problems,
 * you can declare fields in services that refer other
 * Services, as services are rather final, they will be injected if there 
 * Be warned, it does not consider type hierarchy
 * @author kuro
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectedService {

}
