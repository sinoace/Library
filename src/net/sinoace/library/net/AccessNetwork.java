package net.sinoace.library.net;


/**
 * 访问网络对象
 */
public class AccessNetwork {
	/**
	 * Http请求对象
	 */
	private HttpRequest httpRequest;
	/**
	 * 访问网络监听器
	 */
	private AccessNetworkListener accessNetworkListener;
	/**
	 * 异常
	 */
	private Exception exception;
	/**
	 * 成功结果
	 */
	private Object successResult;
	/**
	 * 失败状态码
	 */
	private Object failStateCode;
	
	/**
	 * 获取Http请求对象
	 * @return Http请求对象
	 */
	public HttpRequest getHttpRequest() {
		return httpRequest;
	}
	
	/**
	 * 设置Http请求对象
	 * @param httpRequest Http请求对象
	 */
	public void setHttpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
	/**
	 * 获取访问网络监听器
	 * @return 访问网络监听器
	 */
	public AccessNetworkListener getAccessNetworkListener() {
		return accessNetworkListener;
	}
	
	/**
	 * 设置访问网络监听器
	 * @param accessNetworkListener 访问网络监听器
	 */
	public void setAccessNetworkListener(AccessNetworkListener accessNetworkListener) {
		this.accessNetworkListener = accessNetworkListener;
	}

	/**
	 * 获取异常
	 * @return 异常
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * 设置异常
	 * @param exception 异常
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * 获取成功结果
	 * @return 成功结果
	 */
	public Object getSuccessResult() {
		return successResult;
	}

	/**
	 * 设置成功结果
	 * @param successResult 成功结果
	 */
	public void setSuccessResult(Object successResult) {
		this.successResult = successResult;
	}

	/**
	 * 获取失败状态码
	 * @return 失败状态码
	 */
	public Object getFailStateCode() {
		return failStateCode;
	}

	/**
	 * 设置失败状态码
	 * @param failStateCode 失败状态码
	 */
	public void setFailStateCode(Object failStateCode) {
		this.failStateCode = failStateCode;
	}
}