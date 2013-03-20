package net.sinoace.library.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列注解，标识当前字段对应表中的列
 */
@Target ({ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
public @interface Column {
	public String value();
}
