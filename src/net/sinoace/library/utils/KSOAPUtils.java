package net.sinoace.library.utils;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * KSOAP工具箱
 */
public class KSOAPUtils {
	
	/**
	 * 发送请求
	 * @param nameSpace 命名空间。必须以/结尾，否则抛出异常
	 * @param methodName 要调用的方法名
	 * @param url 要请求的webservices的完整的URL地址
	 * @param dotNet 提供webservices的服务器是否是.net服务器
	 * @param debug 是否开启degub模式
	 * @param propertys
	 * @return 结果字符串
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static String sendRequest(String nameSpace, String methodName, String url, boolean dotNet, boolean debug, Property... propertys) throws IOException, XmlPullParserException{
		
		//创建输出的SOAP对象
		SoapObject outSoapObject = new SoapObject(nameSpace, methodName);
		//设置输出参数
		for(Property property : propertys){
			outSoapObject.addProperty(property.getName(), property.getValue());
		}
		
		//创建SOAP序列化包装器
		SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		//设置输出的内容
		soapSerializationEnvelope.bodyOut = outSoapObject;
		//设置是否是.net服务器
		soapSerializationEnvelope.dotNet = dotNet;
		//设置输出的SOAP对象
		soapSerializationEnvelope.setOutputSoapObject(outSoapObject);
		
		//创建Http发送对象
		HttpTransportSE transportSE = new HttpTransportSE(url);
		//设置是否开启debug模式
		transportSE.debug = true;
		//发送请求
		transportSE.call(nameSpace+methodName, soapSerializationEnvelope);
		
		//获取返回结果SOAP对象
		SoapObject responseSoapObject = (SoapObject) soapSerializationEnvelope.getResponse();
		
		//返回结果
		return responseSoapObject.toString();
	}
	
	/**
	 * 发送请求
	 * @param nameSpace 命名空间。必须以/结尾，否则抛出异常
	 * @param methodName 要调用的方法名
	 * @param url 要请求的webservices的完整的URL地址
	 * @param dotNet 提供webservices的服务器是否是.net服务器
	 * @param propertys
	 * @return 结果字符串
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static String sendRequest(String nameSpace, String methodName, String url, boolean dotNet, Property... propertys) throws IOException, XmlPullParserException{
		return sendRequest(nameSpace, methodName, url, dotNet, false, propertys);
	}
	
	/**
	 * 发送请求
	 * @param nameSpace 命名空间。必须以/结尾，否则抛出异常
	 * @param methodName 要调用的方法名
	 * @param url 要请求的webservices的完整的URL地址
	 * @param propertys
	 * @return 结果字符串
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static String sendRequest(String nameSpace, String methodName, String url, Property... propertys) throws IOException, XmlPullParserException{
		return sendRequest(nameSpace, methodName, url, false, false, propertys);
	}
	
	/**
	 * 发送webservices请求时需要的参数
	 */
	public static class Property{
		private String name;
		private Object value;
		
		public Property(String name, Object value){
			setName(name);
			setValue(value);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}