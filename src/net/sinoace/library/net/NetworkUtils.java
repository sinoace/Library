package net.sinoace.library.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	/**
	 * 判断网络连接是否可用
	 * @param context 上下文
	 * @return 网络连接是否可用
	 */
	public static boolean isEnabled(Context context){
		boolean result = false;
		NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED){
			result = true;
		}
		return result;
	}
}