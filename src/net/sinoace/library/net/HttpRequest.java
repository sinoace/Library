package net.sinoace.library.net;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>Http请求类</h2>
 */
public class HttpRequest {
	/**
	 * 完整的地址，包括主机地址、路径以及参数
	 */
	private URL url;
	/**
	 * 主机地址（可以包括路径）
	 */
	private String hostAddress;
	/**
	 * 属性Map
	 */
	private Map<String, Field> propertyMap;
	/**
	 * 参数Map
	 */
	private Map<String, String> parameterMap;
	/**
	 * 编码，默认为UTF-8
	 */
	private String encode;
	/**
	 * 请求方式
	 */
	private HttpRequestMethod requestMethod;
	/**
	 * 连接超时时间
	 */
	private int connectTimeout;
	/**
	 * 读取超时时间
	 */
	private int readTimeout;
	/**
	 * 自动重定向
	 */
	private boolean followRedirects;

	/**
	 * 创建Http请求对象
	 * @param url 完整的地址，包括主机地址、路径以及参数
	 */
	public HttpRequest(URL url){
		setUrl(url);
		setPropertyMap(new HashMap<String, Field>());
		setParameterMap(new HashMap<String, String>());
		setEncode("UTF-8");
		setRequestMethod(HttpRequestMethod.GET);
		setConnectTimeout(NetUtils.DEFAULT_CONNECT_TIMEOUT);
		setReadTimeout(NetUtils.DEFAULT_READ_TIMEOUT);
		setFollowRedirects(true);
	}
	
	/**
	 * 创建Http请求对象
	 * @param hostAddress 主机地址（可以包括路径）
	 */
	public HttpRequest(String hostAddress) {
		setHostAddress(hostAddress);
		setPropertyMap(new HashMap<String, Field>());
		setParameterMap(new HashMap<String, String>());
		setEncode("UTF-8");
		setRequestMethod(HttpRequestMethod.GET);
		setConnectTimeout(NetUtils.DEFAULT_CONNECT_TIMEOUT);
		setReadTimeout(NetUtils.DEFAULT_READ_TIMEOUT);
		setFollowRedirects(true);
	}
	
	/**
	 * 获取主机地址（可以包括路径）
	 * @return 主机地址（可以包括路径），例如：http://baidu.com/login
	 */
	public String getHostAddress() {
		return hostAddress;
	}

	/**
	 * 设置主机地址（可以包括路径）
	 * @param hostAddress 主机地址（可以包括路径），例如：http://baidu.com/login
	 */
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	/**
	 * 获取属性Map
	 * @return 属性Map
	 */
	public Map<String, Field> getPropertyMap() {
		return propertyMap;
	}

	/**
	 * 设置属性Map
	 * @param propertyMap 属性Map
	 */
	public void setPropertyMap(Map<String, Field> propertyMap) {
		this.propertyMap = propertyMap;
	}

	/**
	 * 设置属性
	 * @param field 继承自Field的对象
	 */
	public void setProperty(Field field) {
		propertyMap.put(field.getName(), field);
	}

	/**
	 * 移除属性
	 * @param name 属性名
	 * @return 属性
	 */
	public Field removeProperty(String name) {
		return propertyMap.remove(name);
	}

	/**
	 * 获取属性
	 * @param name 属性名
	 * @return 值
	 */
	public Field getProperty(String name) {
		return propertyMap.get(name);
	}

	/**
	 * 获取参数Map
	 * @return 参数Map
	 */
	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	/**
	 * 设置参数Map
	 * @param parameterMap 参数Map
	 */
	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, boolean value) {
		parameterMap.put(key, String.valueOf(value));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, char value) {
		parameterMap.put(key, String.valueOf(value));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, char[] value) {
		parameterMap.put(key, String.valueOf(value));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 * @param offset 偏移量
	 * @param count 个数
	 */
	public void addParameter(String key, char[] value, int offset, int count) {
		parameterMap.put(key, String.valueOf(value, offset, count));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, double value) {
		parameterMap.put(key, String.valueOf(value));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, float value) {
		parameterMap.put(key, String.valueOf(value));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, int value) {
		parameterMap.put(key, String.valueOf(value));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, long value) {
		parameterMap.put(key, String.valueOf(value));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, Object value) {
		parameterMap.put(key, String.valueOf(value));
	}

	/**
	 * 添加请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParameter(String key, String value) {
		parameterMap.put(key, value);
	}

	/**
	 * 移除参数
	 * @param key 键
	 * @return 值
	 */
	public String removeParameter(String key) {
		return parameterMap.remove(key);
	}

	/**
	 * 获取参数
	 * @param key 键
	 * @return 值
	 */
	public String getParameter(String key) {
		return parameterMap.get(key);
	}

	/**
	 * 获取编码
	 * @return 编码，默认为UTF-8
	 */
	public String getEncode() {
		return encode;
	}

	/**
	 * 设置编码
	 * @param encode 编码
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	/**
	 * 获取请求方式
	 * @return 请求方式，默认GET
	 */
	public HttpRequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * 设置请求方式
	 * @param requestMethod 请求方式
	 */
	public void setRequestMethod(HttpRequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	/**
	 * 获取参数字符串
	 * @return 参数字符串，例如：username=xiaoipan&password=xiaopan；null：没有参数
	 * @throws UnsupportedEncodingException 通过setEncode()方法设置的编码是错误的
	 */
	public String getParams() throws UnsupportedEncodingException{
		String result = null;
		//如果有参数
		if (!getParameterMap().isEmpty()) {
			StringBuffer params = new StringBuffer();
			boolean addSeparator = false;
			String value;
			for (String key : getParameterMap().keySet()) {
				//如果需要添加参数分隔符，就添加参数分隔符并将标记设为不需要添加参数分隔符
				if (addSeparator) {
					params.append("&");
					addSeparator = false;
				}
				//取出当前参数的值
				value = getParameter(key);
				//如果当前参数的键以及值都不是null，就添加参数
				if(key != null && value != null){
					params.append(key);
					params.append("=");
					params.append(URLEncoder.encode(value, getEncode()));
					addSeparator = true;
				}
			}
			if(params.length() > 0){
				result = params.toString();
			}
		}
		return result;
	}

	/**
	 * 获取完整的地址，包括主机地址、路径以及参数
	 * @return url 完整的地址，包括主机地址、路径以及参数
	 */
	public URL getUrl(){
		return url;
	}

	/**
	 * 设置完整的地址，包括主机地址、路径以及参数
	 * @param url 完整的地址，包括主机地址、路径以及参数
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * 获取连接超时时间
	 * @return 连接超时时间
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * 设置连接超时时间
	 * @param connectTimeout 连接超时时间
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 获取读取超时时间
	 * @return 读取超时时间
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * 设置读取超时时间
	 * @param readTimeout 读取超时时间
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * 判断是否自动重定向
	 * @return 是否自动重定向
	 */
	public boolean isFollowRedirects() {
		return followRedirects;
	}

	/**
	 * 设置是否自动重定向
	 * @param followRedirects 是否自动重定向
	 */
	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}
}