package net.sinoace.library.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>类管理工具类，提供一些Java基本的反射功能</h2>
 */
public class ClassUtils {
	
	/* ************************************************** 字段相关的方法 ******************************************************* */
	/**
	 * 获取给定的类中给定名称的字段
	 * @param clas 给定的类
	 * @param fieldName 要获取的字段的名字
	 * @param isFindDeclaredField 是否查找Declared字段
	 * @param isUpwardFind 是否向上去其父类中寻找
	 * @return 给定的类中给定名称的字段
	 */
	public static Field getField(Class<?> clas, String fieldName, boolean isFindDeclaredField, boolean isUpwardFind){
		Field field = null;
		try {
			field = isFindDeclaredField ? clas.getDeclaredField(fieldName) : clas.getField(fieldName);
		} catch (NoSuchFieldException e1) {
			if(isUpwardFind){
				Class<?> classs = clas.getSuperclass();
				while(field == null && classs != null){
					try {
						field = isFindDeclaredField ? classs.getDeclaredField(fieldName) : classs.getField(fieldName);
					} catch (NoSuchFieldException e11) {
						classs = classs.getSuperclass();
					}
				}
			}
		}
		return field;
	}
	
	/**
	 * 获取给定类的所有字段
	 * @param clas 给定的类
	 * @param isGetDeclaredField 是否需要获取Declared字段
	 * @param isFromSuperClassGet 是否需要把其父类中的字段也取出
	 * @param isDESCGet 在最终获取的列表里，父类的字段是否需要排在子类的前面。只有需要把其父类中的字段也取出时此参数才有效
	 * @return 给定类的所有字段
	 */
	public static List<Field> getFileds(Class<?> clas, boolean isGetDeclaredField, boolean isFromSuperClassGet, boolean isDESCGet){
		List<Field> fieldList = new ArrayList<Field>();
		//如果需要从父类中获取
		if(isFromSuperClassGet){
			//获取当前类的所有父类
			List<Class<?>> classList = getSuperClasss(clas, true);
			
			//如果是降序获取
			if(isDESCGet){
				for(int w = classList.size()-1; w > -1; w--){
					for(Field field : isGetDeclaredField ? classList.get(w).getDeclaredFields() : classList.get(w).getFields()){
						fieldList.add(field);
					}
				}
			}else{
				for(int w = 0; w < classList.size(); w++){
					for(Field field : isGetDeclaredField ? classList.get(w).getDeclaredFields() : classList.get(w).getFields()){
						fieldList.add(field);
					}
				}
			}
		}else{
			for(Field field : isGetDeclaredField ? clas.getDeclaredFields() : clas.getFields()){
				fieldList.add(field);
			}
		}
		return fieldList;
	}

	/**
	 * 设置给定的对象中给定名称的字段的值
	 * @param object 给定的对象
	 * @param filedName 要设置的字段的名称
	 * @param newValue 要设置的字段的值
	 * @param isFindDeclaredField 是否查找Declared字段
	 * @param isUpwardFind 如果在当前类中找不到的话，是否取其父类中查找
	 * @return 设置是否成功。false：字段不存在或新的值与字段的类型不一样，导致转型失败
	 */
	public static boolean setField(Object object, String filedName, Object newValue, boolean isFindDeclaredField, boolean isUpwardFind){
		boolean result = false;
		Field field = getField(object.getClass(), filedName, isFindDeclaredField, isUpwardFind);
		if(field != null){
			try {
				field.setAccessible(true);  
				field.set(object, newValue);
				result = true;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}
	
	
	/* ************************************************** 方法相关的方法 ******************************************************* */
	/**
	 * 获取给定的类中给定名称以及给定参数类型的方法
	 * @param clas 给定的类
	 * @param isFindDeclaredMethod 是否查找Declared字段
	 * @param isUpwardFind 是否向上去其父类中寻找
	 * @param methodName 要获取的方法的名字
	 * @param methodParameterTypes 方法参数类型
	 * @return 给定的类中给定名称以及给定参数类型的方法
	 */
	public static Method getMethod(Class<?> clas, boolean isFindDeclaredMethod, boolean isUpwardFind, String methodName, Class<?>... methodParameterTypes){
		Method method = null;
		try {
			method = isFindDeclaredMethod ? clas.getDeclaredMethod(methodName, methodParameterTypes) : clas.getMethod(methodName, methodParameterTypes);
		} catch (NoSuchMethodException e1) {
			if(isUpwardFind){
				Class<?> classs = clas.getSuperclass();
				while(method == null && classs != null){
					try {
						method = isFindDeclaredMethod ? classs.getDeclaredMethod(methodName, methodParameterTypes) : classs.getMethod(methodName, methodParameterTypes);
					} catch (NoSuchMethodException e11) {
						classs = classs.getSuperclass();
					}
				}
			}
		}
		return method;
	}
	
	/**
	 * 获取给定的类中给定名称以及给定参数类型的方法，默认获取Declared类型的方法，以及向上查找
	 * @param clas 给定的类
	 * @param isFindDeclaredMethod 是否查找Declared字段
	 * @param methodParameterTypes 方法参数类型
	 * @return 给定的类中给定名称以及给定参数类型的方法
	 */
	public static Method getMethod(Class<?> clas, String methodName, Class<?>... methodParameterTypes){
		return getMethod(clas, true, true, methodName, methodParameterTypes);
	}
	
	/**
	 * 获取给定的类中给定名称的字段的GET方法
	 * @param clas 给定的类
	 * @param fieldName 给定名称
	 * @param methodParameterTypes 方法参数类型
	 * @return 给定的类中给定名称的字段的GET方法
	 */
	public static Method getGetMethodByFieldName(Class<?> clas, String fieldName, Class<?>... methodParameterTypes){
		return getMethod(clas, true, true, "get"+StringUtils.firstLetterToUpperCase(fieldName), methodParameterTypes);
	}
	
	/**
	 * 获取给定的类中给定名称的字段的不带参数的GET方法
	 * @param clas 给定的类
	 * @param fieldName 给定名称
	 * @return 给定的类中给定名称的字段的GET方法
	 */
	public static Method getGetMethodByFieldName(Class<?> clas, String fieldName){
		return getGetMethodByFieldName(clas, fieldName, new Class<?>[]{});
	}
	
	/**
	 * 获取给定的类中给定字段的GET方法
	 * @param clas 给定的类
	 * @param field 给定字段
	 * @param methodParameterTypes 方法参数类型
	 * @return 给定的类中给定名称的字段的GET方法
	 */
	public static Method getGetMethodByField(Class<?> clas, Field field, Class<?>... methodParameterTypes){
		return getGetMethodByFieldName(clas, field.getName(), methodParameterTypes);
	}
	
	/**
	 * 获取给定的类中给定字段的不带参数的GET方法
	 * @param clas 给定的类
	 * @param field 给定字段
	 * @param methodParameterTypes 方法参数类型
	 * @return 给定的类中给定名称的字段的GET方法
	 */
	public static Method getGetMethodByField(Class<?> clas, Field field){
		return getGetMethodByFieldName(clas, field.getName());
	}
	
	/**
	 * 获取给定的类中给定字段的GET或IS方法
	 * @param clas 给定的类
	 * @param field 字段
	 * @param methodParameterTypes 方法参数类型
	 * @return 如果给定字段是boolean型的那么获取其IS方法，否则获取其GET方法
	 */
	public static Method getGetOrIsMethodByField(Class<?> clas, Field field, Class<?>... methodParameterTypes){
		Method method = null;
		String fieldName = field.getName();
		//如果字段是boolean型的
		if(field.getType() == Boolean.TYPE){
			//如果以is开头
			if(fieldName.startsWith("is")){
				method = getMethod(clas, true, true, StringUtils.toUpperCase(fieldName, 2, 3), methodParameterTypes);
			}else{
				method = getMethod(clas, true, true, "is"+StringUtils.firstLetterToUpperCase(fieldName), methodParameterTypes);
			}
		}else{
			method = getMethod(clas, true, true, "get"+StringUtils.firstLetterToUpperCase(fieldName), methodParameterTypes);
		}
		return method;
	}
	
	/**
	 * 获取给定的类中给定字段的不带参数的GET或IS方法
	 * @param clas 给定的类
	 * @param field 字段
	 * @param methodParameterTypes 方法参数类型
	 * @return 如果给定字段是boolean型的那么获取其IS方法，否则获取其GET方法
	 */
	public static Method getGetOrIsMethodByField(Class<?> clas, Field field){
		return getGetOrIsMethodByField(clas, field, new Class<?>[]{});
	}
	
	/**
	 * 获取给定的类中给定名称的字段的SET方法
	 * @param clas 给定的类
	 * @param fieldName 给定名称
	 * @param methodParameterTypes 方法参数类型
	 * @return 给定的类中给定名称的字段的SET方法
	 */
	public static Method getSetMethodByFieldName(Class<?> clas, String fieldName, Class<?>... methodParameterTypes){
		return getMethod(clas, true, true, "set"+StringUtils.firstLetterToUpperCase(fieldName), methodParameterTypes);
	}
	
	/**
	 * 获取给定的类中给定名称的字段的不带参数的SET方法
	 * @param clas 给定的类
	 * @param fieldName 给定名称
	 * @return 给定的类中给定名称的字段的SET方法
	 */
	public static Method getSetMethodByFieldName(Class<?> clas, String fieldName){
		return getSetMethodByFieldName(clas, fieldName, new Class<?>[]{});
	}
	
	/**
	 * 获取给定的类中给定字段的SET方法
	 * @param clas 给定的类
	 * @param field 给定字段
	 * @param methodParameterTypes 方法参数类型
	 * @return 给定的类中给定名称的字段的SET方法
	 */
	public static Method getSetMethodByField(Class<?> clas, Field field, Class<?>... methodParameterTypes){
		return getSetMethodByFieldName(clas, field.getName(), methodParameterTypes);
	}
	
	/**
	 * 获取给定的类中给定字段的不带参数的SET方法
	 * @param clas 给定的类
	 * @param field 给定字段
	 * @return 给定的类中给定名称的字段的SET方法
	 */
	public static Method getSetMethodByField(Class<?> clas, Field field){
		return getSetMethodByFieldName(clas, field.getName());
	}
	
	/**
	 * 获取给定的类中给定字段的ValuOf方法
	 * @param clas 给定的类
	 * @param methodParameterTypes 方法参数类型
	 * @return 给定的类中给定名称的字段的GET方法
	 */
	public static Method getValueOfMethod(Class<?> clas, Class<?>... methodParameterTypes){
		return getMethod(clas, true, true, "valueOf", methodParameterTypes);
	}
	
	/**
	 * 获取给定类的所有方法
	 * @param clas 给定的类
	 * @param isGetDeclaredMethod 是否需要获取Declared方法
	 * @param isFromSuperClassGet 是否需要把其父类中的方法也取出
	 * @param isDESCGet 在最终获取的列表里，父类的方法是否需要排在子类的前面。只有需要把其父类中的方法也取出时此参数才有效
	 * @return 给定类的所有方法
	 */
	public static List<Method> getMethods(Class<?> clas, boolean isGetDeclaredMethod, boolean isFromSuperClassGet, boolean isDESCGet){
		List<Method> methodList = new ArrayList<Method>();
		//如果需要从父类中获取
		if(isFromSuperClassGet){
			//获取当前类的所有父类
			List<Class<?>> classList = getSuperClasss(clas, true);
			
			//如果是降序获取
			if(isDESCGet){
				for(int w = classList.size()-1; w > -1; w--){
					for(Method method : isGetDeclaredMethod ? classList.get(w).getDeclaredMethods() : classList.get(w).getMethods()){
						methodList.add(method);
					}
				}
			}else{
				for(int w = 0; w < classList.size(); w++){
					for(Method method : isGetDeclaredMethod ? classList.get(w).getDeclaredMethods() : classList.get(w).getMethods()){
						methodList.add(method);
					}
				}
			}
		}else{
			for(Method method : isGetDeclaredMethod ? clas.getDeclaredMethods() : clas.getMethods()){
				methodList.add(method);
			}
		}
		return methodList;
	}
	
	/* ************************************************** 构造函数相关的方法 ******************************************************* */
	/**
	 * 获取给定的类中给定参数类型的构造函数
	 * @param clas 给定的类
	 * @param isFindDeclaredConstructor 是否查找Declared构造函数
	 * @param isUpwardFind 是否向上去其父类中寻找
	 * @param constructorParameterTypes 构造函数的参数类型
	 * @return 给定的类中给定参数类型的构造函数
	 */
	public static Constructor<?> getConstructor(Class<?> clas, boolean isFindDeclaredConstructor, boolean isUpwardFind, Class<?>... constructorParameterTypes){
		Constructor<?> method = null;
		try {
			method = isFindDeclaredConstructor ? clas.getDeclaredConstructor(constructorParameterTypes) : clas.getConstructor(constructorParameterTypes);
		} catch (NoSuchMethodException e1) {
			if(isUpwardFind){
				Class<?> classs = clas.getSuperclass();
				while(method == null && classs != null){
					try {
						method = isFindDeclaredConstructor ? clas.getDeclaredConstructor(constructorParameterTypes) : clas.getConstructor(constructorParameterTypes);
					} catch (NoSuchMethodException e11) {
						classs = classs.getSuperclass();
					}
				}
			}
		}
		return method;
	}
	
	/**
	 * 获取给定的类中所有的构造函数
	 * @param clas 给定的类
	 * @param isFindDeclaredConstructor 是否需要获取Declared构造函数
	 * @param isFromSuperClassGet 是否需要把其父类中的构造函数也取出
	 * @param isDESCGet 在最终获取的列表里，父类的构造函数是否需要排在子类的前面。只有需要把其父类中的构造函数也取出时此参数才有效
	 * @return 给定的类中所有的构造函数
	 */
	public static List<Constructor<?>> getConstructors(Class<?> clas, boolean isFindDeclaredConstructor, boolean isFromSuperClassGet, boolean isDESCGet){
		List<Constructor<?>> constructorList = new ArrayList<Constructor<?>>();
		//如果需要从父类中获取
		if(isFromSuperClassGet){
			//获取当前类的所有父类
			List<Class<?>> classList = getSuperClasss(clas, true);
			
			//如果是降序获取
			if(isDESCGet){
				for(int w = classList.size()-1; w > -1; w--){
					for(Constructor<?> constructor : isFindDeclaredConstructor ? classList.get(w).getDeclaredConstructors() : classList.get(w).getConstructors()){
						constructorList.add(constructor);
					}
				}
			}else{
				for(int w = 0; w < classList.size(); w++){
					for(Constructor<?> constructor : isFindDeclaredConstructor ? classList.get(w).getDeclaredConstructors() : classList.get(w).getConstructors()){
						constructorList.add(constructor);
					}
				}
			}
		}else{
			for(Constructor<?> constructor : isFindDeclaredConstructor ? clas.getDeclaredConstructors() : clas.getConstructors()){
				constructorList.add(constructor);
			}
		}
		return constructorList;
	}
	
	
	/* ************************************************** 父类相关的方法 ******************************************************* */
	/**
	 * 获取给定的类所有的父类
	 * @param clas 给定的类
	 * @param isAddCurrentClass 是否将当年类放在最终返回的父类列表的首位
	 * @return 给定的类所有的父类
	 */
	public static List<Class<?>> getSuperClasss(Class<?> clas, boolean isAddCurrentClass){
		List<Class<?>> classList = new ArrayList<Class<?>>();
		Class<?> classs;
		if(isAddCurrentClass){
			classs = clas;
		}else{
			classs = clas.getSuperclass();
		}
		while(classs != null){
			classList.add(classs);
			classs = classs.getSuperclass();
		}
		return classList;
	}
	
	
	/* ************************************************** 其它的辅助方法 ******************************************************* */
	/**
	 * 获取给定的类的名字
	 * @param clas 给定的类
	 * @return 给定的类的名字
	 */
	public static String getClassName(Class<?> clas){
		String classPath = clas.getName();
		return classPath.substring(classPath.lastIndexOf('.')+1);
	}
}
