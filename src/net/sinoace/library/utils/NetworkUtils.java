package net.sinoace.library.utils;

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
	
	/**
	 * 判断当前使用的是否是Wifi网络
	 * @param context 上下文
	 * @return 只有当前网络连接并且是wifi网络时才会返回true
	 */
	public static boolean isWifi(Context context){
		boolean result = false;
		NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED && networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
			result = true;
		}
		return result;
	}
	
	/**
	 * 判断当前使用的是否是Mobile网络
	 * @param context 上下文
	 * @return 只有当前网络连接并且是Mobile网络时才会返回true
	 */
	public static boolean isMobile(Context context){
		boolean result = false;
		NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
			result = true;
		}
		return result;
	}
}