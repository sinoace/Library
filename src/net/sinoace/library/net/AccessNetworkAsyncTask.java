package net.sinoace.library.net;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

/**
 * 访问网络异步任务
 */
public class AccessNetworkAsyncTask extends AsyncTask<Integer, Integer, Integer>{
	private static final int RESULT_SUCCESS = 101;//结果标记 - 成功了
	private static final int RESULT_FAIL = 102;//结果标记 - 失败了
	private static final int RESULT_EXCEPTION = 103;//结果标记 - 异常了
	
	private Context context;//上下文
	private HttpRequest httpRequest;//请求对象
	private ResponseHandler responseHandler;//响应处理器
	private AccessNetworkListener<?> accessNetworkListener;//监听器
	
	private int resultFlag = RESULT_EXCEPTION;//结果标记，默认异常
	private Exception exception;//访问网络过程中发生的异常
	private ErrorInfo errorInfo;//错误信息
	private Object resultObject;//结果对象
	
	/**
	 * 创建访问网络异步任务
	 * @param context 上下文
	 * @param httpRequest 请求对象
	 * @param responseHandler 想听处理器
	 * @param accessNetworkListener 监听器
	 */
	public AccessNetworkAsyncTask(Context context, HttpRequest httpRequest, ResponseHandler responseHandler, AccessNetworkListener<?> accessNetworkListener){
		this.context = context;
		this.httpRequest = httpRequest;
		this.responseHandler = responseHandler;
		this.accessNetworkListener = accessNetworkListener;
	}
	
	@Override
	protected void onPreExecute() {
		accessNetworkListener.onStart();
	}
	
	@Override
	protected Integer doInBackground(Integer... params) {
		HttpClient.sendRequest(httpRequest, new HttpListener() {
			@Override
			public void onStart() {}
			
			@Override
			public void onHandleResponse(HttpResponse httpResponse) throws Exception {
				//如果有响应处理器
				if(responseHandler != null){
					JSONObject responseJsonObject = new JSONObject(httpResponse.getString());;
					//如果状态为成功
					if(responseHandler.onIsSuccess(responseJsonObject)){
						//调用响应处理器获取处理结果，并标记为成功
						resultObject = responseHandler.onGetSuccessResult(responseJsonObject);
						resultFlag = RESULT_SUCCESS;
					}else{
						//调用响应处理器获取失败状态码，并标记为失败
						errorInfo = responseHandler.onGetError(responseJsonObject);
						resultFlag = RESULT_FAIL;
					}
				}else{
					throw new Exception();
				}
			}
			
			@Override
			public void onException(Exception e) {
				exception = e;
				resultFlag = RESULT_EXCEPTION;
			}
			
			@Override
			public void onEnd(){}
		});
		return null;
	}

	@Override
	protected void onPostExecute(Integer result) {
		switch(resultFlag){
			case RESULT_SUCCESS : accessNetworkListener.success(resultObject); break;
			case RESULT_FAIL : accessNetworkListener.onError(errorInfo); break;
			case RESULT_EXCEPTION : accessNetworkListener.onException(exception, context); break;
		}
		accessNetworkListener.onEnd();
	}
}