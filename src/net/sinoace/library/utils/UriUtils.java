package net.sinoace.library.utils;

import android.net.Uri;

/**
 * URI工具箱
 * @author xiaopan
 *
 */
public class UriUtils {
	/**
	 * 获取呼叫给定的电话号码时用的Uri
	 * @param phoneNumber 给定的电话号码
	 * @return 呼叫给定的电话号码时用的Uri
	 */
	public static Uri getCallUri(String phoneNumber){
		return Uri.parse("tel:"+phoneNumber);
	}
}