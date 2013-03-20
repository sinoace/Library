package net.sinoace.library.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表注解，标识当前domain在数据库中所对应的表
 */
@Target ({ElementType.TYPE})
@Retention (RetentionPolicy.RUNTIME)
public @interface Table {
	public String value();
}
