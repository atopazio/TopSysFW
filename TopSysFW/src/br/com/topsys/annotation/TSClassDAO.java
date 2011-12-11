/**
 * 
 */
package br.com.topsys.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.topsys.database.hibernate.TSHibernateBrokerAb;

/**
 * @author andre
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)  
public @interface TSClassDAO {
	Class<? extends TSHibernateBrokerAb> value();
}
