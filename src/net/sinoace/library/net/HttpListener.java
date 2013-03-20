package net.sinoace.library.net;

/**
 * Http监听器
 */
public abstract class HttpListener {
	/**
	 * 当开始
	 */
	public abstract void onStart();
	
	/**
	 * 当需要处理响应
	 * @param httpResponse Http响应
	 */
	public abstract void onHandleResponse(HttpResponse httpResponse) throws Exception;
	
	/**
	 * 当出现异常
	 * @param e 异常。可能的情况有：
	 * <br>SocketTimeoutException（连接超时）；
	 * <br>UnknownHostException（本地网络不可用或者目标主机不存在）；
	 * <br>FileNotFoundException（URL地址拼写错误或者目标主机上不存在URL中指定的路径）；
	 * <br>或者其他的未知异常
	 */
	public abstract void onException(Exception e);
	
	/**
	 * 当结束
	 */
	public abstract void onEnd();
}