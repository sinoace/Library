package net.sinoace.library.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据类型注解，标识字段在表中的数据类型
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataType {
	/**
	 * 数据类型 - 文本
	 */
	public static final String TEXT = "text";
	/**
	 * 数据类型 - 数字
	 */
	public static final String NUMBER = "number";
	/**
	 * 数据类型 - 浮点
	 */
	public static final String FLOAT = "float";
	
	/**
	 * 值
	 * @return
	 */
	public String value();
}
