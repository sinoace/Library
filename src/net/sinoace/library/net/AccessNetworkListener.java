package net.sinoace.library.net;

import android.content.Context;

/**
 * 访问网络监听器
 * @param <T> 如果你想将处理结果直接转换为方便处理的对象，那么请在次指定
 */
public abstract class AccessNetworkListener<T> {
	/**
	 * 当开始访问
	 */
	public abstract void onStart();
	
	/**
	 * 当响应成功（此成功是服务器返回结果为成功的意思）
	 * @param result 返回结果
	 */
	public abstract void onSuccess(T resultObject);
	
	/**
	 * 当发生错误
	 * @param errorInfo 错误信息
	 */
	public abstract void onError(ErrorInfo errorInfo);
	
	/**
	 * 当出现异常
	 * @param e 异常。可能的情况有：
	 * <br>SocketTimeoutException（连接超时）；
	 * <br>UnknownHostException（本地网络不可用或者目标主机不存在）；
	 * <br>FileNotFoundException（URL地址拼写错误或者目标主机上不存在URL中指定的路径）；
	 * <br>或者其他的未知异常
	 * @param context 上下文
	 */
	public abstract void onException(Exception e, Context context);
	
	/**
	 * 当结束访问
	 */
	public abstract void onEnd();
	
	/**
	 * 当尚未开始执行onStart()就发现网络不可用
	 * @param context 上下文
	 */
	public abstract void onNetworkNotAvailable(Context context);
	
	@SuppressWarnings("unchecked")
	public void success(Object result){
		onSuccess((T) result);
	}
}