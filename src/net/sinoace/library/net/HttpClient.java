package net.sinoace.library.net;

import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http客户端
 */
public class HttpClient {
	/**
	 * 输出日志，默认不输出
	 */
	public static boolean OUTPUT_LOG = false;
	private static final String LOG_ADDRESS = "请求地址";
	private static final String LOG_PARAMS = "请求参数";
	private static final String LINK = "?";
	
	/**
	 * 发送请求
	 * @param httpRequest http请求
	 */
	public static void sendRequest(HttpRequest httpRequest,  final HttpListener httpListener){
		//当开始
		httpListener.onStart();
		
		try{
			byte[] paramEntrys = null;
			String hostAddress = httpRequest.getHostAddress();
			String params = httpRequest.getParams();
			
			//如果需要输出LOG
			if(OUTPUT_LOG){
				System.out.println(LOG_ADDRESS + ": " + hostAddress);
				System.out.println(LOG_PARAMS + ": " + params);
			}

			//组织URL
			URL url = httpRequest.getUrl();
			if(url == null){
				if(httpRequest.getRequestMethod() == HttpRequestMethod.GET && params != null){
					url = new URL(hostAddress + LINK + params);
				}else{
					url = new URL(hostAddress);
				}
			}

			//建立连接并设置请求方式以及自动重定向、允许输入
			HttpURLConnection httpURLConn = (HttpURLConnection) NetUtils.getURLConnFromURL(url, httpRequest.getConnectTimeout());
			httpURLConn.setRequestMethod(httpRequest.getRequestMethod().name());
			HttpURLConnection.setFollowRedirects(httpRequest.isFollowRedirects());
			httpURLConn.setDoInput(true);
			
			//如果是POST的请求方式就设置为允许输出、获取请参数的字节数据并设置内容长度属性
			if(httpRequest.getRequestMethod() == HttpRequestMethod.POST){
				httpURLConn.setDoOutput(true);
				if(params != null){
					paramEntrys = params.getBytes();
				}
				httpRequest.setProperty(new ContentLength(paramEntrys != null?String.valueOf(paramEntrys.length):String.valueOf(0)));
			}
			
			//如果请求属性MAP不空，就全取出来并设置属性
			if(!httpRequest.getPropertyMap().isEmpty()) {
				for (String key : httpRequest.getPropertyMap().keySet()) {
					Field field = httpRequest.getProperty(key);
					if(field.getValue() != null){
						httpURLConn.setRequestProperty(field.getName(), field.getValue());
					}
				}
			}
		
			// 尝试连接
			httpURLConn.connect();
			
			//如果是POST请求，就发送参数数据
			if(httpRequest.getRequestMethod() == HttpRequestMethod.POST){
				if (paramEntrys != null) {
					BufferedOutputStream bufferedOutputStream = new  BufferedOutputStream(httpURLConn.getOutputStream());
					bufferedOutputStream.write(paramEntrys); 
					bufferedOutputStream.flush();
					bufferedOutputStream.close(); 
				}
			}
			
			//设置读取超时时间
			httpURLConn.setReadTimeout(httpRequest.getReadTimeout());
			
			//处理响应
			httpListener.onHandleResponse(new HttpResponse(httpURLConn));
		}catch(Exception e){
			e.printStackTrace();
			//当发生异常
			httpListener.onException(e);
		}
		
		//当结束
		httpListener.onEnd();
	}
}