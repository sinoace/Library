package net.sinoace.library.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注解工具箱
 * @author xiaopan
 *
 */
public class AnnotationUtils {
	private static final String annotationDefaultValueMethodName = "value";
	private static final Class<?>[] emptyParamTypes = new Class<?>[0];
	private static final Object[] emptyParams = new Object[0];
	
	/**
	 * 获取给定Class的所有注解
	 * @param fromClass 给定的Class
	 * @return 给定的Class的所有注解
	 */
	public static Annotation[] getAnnotaions(Class<?> fromClass){
		return fromClass.getAnnotations();
	}
	
	/**
	 * 获取给定方法的所有注解
	 * @param method 给定的方法
	 * @return 给定方法的所有注解
	 */
	public static Annotation[] getAnnotaionsFrom(Method method){
		return method.getAnnotations();
	}
	
	/**
	 * 获取给定字段的所有注解
	 * @param field 给定的字段
	 * @return 给定字段的所有注解
	 */
	public static Annotation[] getAnnotaions(Field field){
		return field.getAnnotations();
	}
	
	
	/**
	 * 判断给定Class是否存在给定的注解
	 * @param fromClass 给定的Class
	 * @param annotationClass 给定的注解
	 * @return true：存在；false：不存在
	 */
	public static boolean existAnnotaion(Class<?> fromClass, Class<? extends Annotation> annotationClass){
		return fromClass.getAnnotation(annotationClass) != null ? true : false;
	}
	
	/**
	 * 判断给定方法是否存在给定的注解
	 * @param method 给定的方法
	 * @param annotationClass 给定的注解
	 * @return true：存在；false：不存在
	 */
	public static boolean existAnnotaion(Method method, Class<? extends Annotation> annotationClass){
		return method.getAnnotation(annotationClass) != null ? true : false;
	}
	
	/**
	 * 判断给定字段是否存在给定的注解
	 * @param field 给定的字段
	 * @param annotationClass 给定的注解
	 * @return true：存在；false：不存在
	 */
	public static boolean existAnnotaion(Field field, Class<? extends Annotation> annotationClass){
		return field.getAnnotation(annotationClass) != null ? true : false;
	}
	
	
	/**
	 * 获取给定Class的给定注解
	 * @param fromClass 给定的Class
	 * @param annotationClass 给定注解
	 * @return 给定Class的给定注解
	 */
	public static <A extends Annotation> A getAnnotaion(Class<?> fromClass, Class<A> annotationClass){
		return fromClass.getAnnotation(annotationClass);
	}
	
	/**
	 * 获取给定方法的给定注解
	 * @param method 给定的方法
	 * @param annotationClass 给定注解
	 * @return 给定方法的给定注解
	 */
	public static <A extends Annotation> A getAnnotaion(Method method, Class<A> annotationClass){
		return method.getAnnotation(annotationClass);
	}
	
	/**
	 * 获取给定字段的给定注解
	 * @param field 给定的字段
	 * @param annotationClass 给定注解
	 * @return 给定字段的给定注解
	 */
	public static <A extends Annotation> A getAnnotaion(Field field, Class<A> annotationClass){
		return field.getAnnotation(annotationClass);
	}
	
	/**
	 * 获取给定Class的给定注解的值
	 * @param fromClass 给定的Class
	 * @param annotaion 给定的注解
	 * @return 给定Class的给定注解的值。null：不存在给定的注解或者注解没有值
	 */
	public static <A extends Annotation> String getAnnotaionValue(Class<?> fromClass, Class<A> annotaion){
		String result = null;
		A a =  fromClass.getAnnotation(annotaion);
		if(a != null){
			Method method = ClassUtils.getMethod(a.getClass(), true, true, annotationDefaultValueMethodName, emptyParamTypes);
			if(method != null){
				try {
					result = (String) method.invoke(a, emptyParams);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取给定方法的给定注解的值
	 * @param method 给定的方法
	 * @param annotaion 给定的注解
	 * @return 给定方法的给定注解的值。null：不存在给定的注解或者注解没有值
	 */
	public static <A extends Annotation> String getAnnotaionValue(Method method, Class<A> annotaion){
		String result = null;
		A a =  method.getAnnotation(annotaion);
		if(a != null){
			Method valueMethod = ClassUtils.getMethod(a.getClass(), true, true, annotationDefaultValueMethodName, emptyParamTypes);
			if(valueMethod != null){
				try {
					result = (String) valueMethod.invoke(a, emptyParams);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取给定字段的给定注解的值
	 * @param field 给定的字段
	 * @param annotaion 给定的注解
	 * @return 给定字段的给定注解的值。null：不存在给定的注解或者注解没有值
	 */
	public static <A extends Annotation> String getAnnotaionValue(Field field, Class<A> annotaion){
		String result = null;
		A a =  field.getAnnotation(annotaion);
		if(a != null){
			Method valueMethod = ClassUtils.getMethod(a.getClass(), true, true, annotationDefaultValueMethodName, emptyParamTypes);
			if(valueMethod != null){
				try {
					result = (String) valueMethod.invoke(a, emptyParams);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}