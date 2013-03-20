package net.sinoace.library.utils;

import android.content.Context;
import android.view.View;

/**
 * 数据验证工具箱
 */
public class DataValiUtils {
	/**
	 * 验证给定的值是否合法
	 * @param context 上下文
	 * @param view 要验证的视图，如果错误将会左右晃动
	 * @param vlaue 验证的值
	 * @param errorHint 不合法时提示的内容
	 * @return 是否合法
	 */
	public static boolean valiNullAndEmpty(Context context, View view, String vlaue, String errorHint){
		boolean result = true;
		if(vlaue == null || "".equals(vlaue)){
			if(errorHint != null && !"".equals(errorHint)){
				AndroidUtils.toastS(context, errorHint);
			}
			AnimationUtils.shake(view);
			result = false;
		}
		return result;
	}
}