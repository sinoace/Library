package net.sinoace.library.net;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.sinoace.library.utils.AnnotationUtils;
import net.sinoace.library.utils.ClassUtils;
import net.sinoace.library.utils.StringUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 访问网络工具箱
 * @author xiaopan
 *
 */
public class AccessNetworkUtils {
	/**
	 * 将一个Request对象转换为HttpRequest对象
	 * @param request
	 * @param defaultHostServerAddress 默认的主机地址，挡在request对象上取不到HostAddress注解时将使用此值
	 * @return
	 * @throws Exception 在Request对象上找不到Path注解
	 */
	public static HttpRequest toHttpRequest(Request request, String defaultHostServerAddress) throws Exception{
		Class<? extends Request> requestClass = request.getClass();
		
		//尝试从请求对象中获取地址注解的值
		String hostServerAddress = AnnotationUtils.getAnnotaionValue(requestClass, HostAddress.class);
		if(hostServerAddress == null){
			hostServerAddress = defaultHostServerAddress;
		}
		
		//尝试取得Path注解的值，如果没有就抛出异常
		String path = AnnotationUtils.getAnnotaionValue(requestClass, Path.class);
		if(path == null){
			throw new Exception("Find no 'Path' annotation");
		}
		
		/*
		 * 组织HttpRequest对象
		 */
		HttpRequest httpRequest = new HttpRequest(hostServerAddress + "/" + path);
		
		//如果有Post注解就设置请求方式为POST
		if(AnnotationUtils.existAnnotaion(requestClass, Post.class)){
			httpRequest.setRequestMethod(HttpRequestMethod.POST);
		}
		
		//循环处理所有字段
		for(Field field : ClassUtils.getFileds(requestClass, true, true, true)){
			try {
				//获取字段的值
				field.setAccessible(true);
				Object valueObject = field.get(request);
				String value = valueObject != null?valueObject.toString():null;
				
				//如果当前字段没有被弃用并且不是static final的以及值不为null
				if(!AnnotationUtils.existAnnotaion(field, Deprecated.class) && !(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) && StringUtils.isNotNullAndEmpty(value)){
					//默认参数名字为序列化名字注解的值
					String paramName = AnnotationUtils.getAnnotaionValue(field, SerializedName.class);
					//但如果当前字段上没有使用序列化名字注解，就用字段的名字作为参数名
					if(paramName == null){
						paramName = field.getName();
					}
					
					//添加请求参数
					httpRequest.addParameter(paramName, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return httpRequest;
	}
}