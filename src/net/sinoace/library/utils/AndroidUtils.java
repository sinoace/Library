package net.sinoace.library.utils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Android工具箱
 * @author panpf
 *
 */
public class AndroidUtils {
	/**
	 * 吐出一个显示时间较长的提示
	 * @param context 上下文对象
	 * @param resId 显示内容资源ID
	 */
	public static final void toastL(Context context, int resId){
		Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param context 上下文对象
	 * @param resId 显示内容资源ID
	 */
	public static final void toastS(Context context, int resId){
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param context 上下文对象
	 * @param content 显示内容
	 */
	public static final void toastL(Context context, String content){
		Toast.makeText(context, content, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param context 上下文对象
	 * @param content 显示内容
	 */
	public static final void toastS(Context context, String content){
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param context 上下文对象
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param args 参数数组
	 */
	public static final void toastL(Context context, int formatResId, Object... args){
		Toast.makeText(context, String.format(context.getString(formatResId), args), Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param context 上下文对象
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param args 参数数组
	 */
	public static final void toastS(Context context, int formatResId, Object... args){
		Toast.makeText(context, String.format(context.getString(formatResId), args), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param context 上下文对象
	 * @param format 被格式化的字符串
	 * @param args 参数数组
	 */
	public static final void toastL(Context context, String format, Object... args){
		Toast.makeText(context, String.format(format, args), Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param context 上下文对象
	 * @param format 被格式化的字符串
	 * @param args 参数数组
	 */
	public static final void toastS(Context context, String format, Object... args){
		Toast.makeText(context, String.format(format, args), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		Intent intent = new Intent(fromActivity, targetActivity);
		if(flag != -5){
			intent.setFlags(flag);
		}
		if(bundle != null){
			intent.putExtras(bundle);
		}
		fromActivity.startActivity(intent);
		if(inAnimation >0 && outAnimation >0){
			fromActivity.overridePendingTransition(inAnimation, outAnimation);
		}
		if(isClose){
			fromActivity.finish();
		}
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, Bundle bundle, boolean isClose){
		startActivity(fromActivity, targetActivity, flag, bundle, isClose, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, Bundle bundle, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, flag, bundle, false, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, boolean isClose, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, flag, null, isClose, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, -5, bundle, isClose, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, Bundle bundle){
		startActivity(fromActivity, targetActivity, flag, bundle, false, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, boolean isClose){
		startActivity(fromActivity, targetActivity, flag, null, isClose, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, Bundle bundle, boolean isClose){
		startActivity(fromActivity, targetActivity, -5, bundle, isClose, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, flag, null, false, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, Bundle bundle, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, -5, bundle, false, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, boolean isClose, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, -5, null, isClose, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, -5, null, false, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, boolean isClose){
		startActivity(fromActivity, targetActivity, -5, null, isClose, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, Bundle bundle){
		startActivity(fromActivity, targetActivity, -5, bundle, false, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag){
		startActivity(fromActivity, targetActivity, flag, null, false, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity){
		startActivity(fromActivity, targetActivity, -5, null, false, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivity(Context context, Class<?> targetActivity, int flag, Bundle bundle){
		Intent intent = new Intent(context, targetActivity);
		if(flag != -5){
			intent.setFlags(flag);
		}
		if(bundle != null){
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}
	
	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivity(Context context, Class<?> targetActivity, Bundle bundle){
		startActivity(context, targetActivity, -5, bundle);
	}
	
	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 */
	public static void startActivity(Context context, Class<?> targetActivity, int flag){
		startActivity(context, targetActivity, flag, null);
	}
	
	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivity 目标Activity
	 */
	public static void startActivity(Context context, Class<?> targetActivity){
		startActivity(context, targetActivity, -5, null);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation){
		Intent intent = new Intent(fromActivity, targetActivity);
		if(flag != -5){
			intent.setFlags(flag);
		}
		if(bundle != null){
			intent.putExtras(bundle);
		}
		fromActivity.startActivityForResult(intent, requestCode);
		if(inAnimation >0 && outAnimation >0){
			fromActivity.overridePendingTransition(inAnimation, outAnimation);
		}
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int flag, Bundle bundle){
		startActivityForResult(fromActivity, targetActivity, requestCode, flag, bundle, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int flag, int inAnimation, int outAnimation){
		startActivityForResult(fromActivity, targetActivity, requestCode, flag, null, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, Bundle bundle, int inAnimation, int outAnimation){
		startActivityForResult(fromActivity, targetActivity, requestCode, -5, bundle, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int flag){
		startActivityForResult(fromActivity, targetActivity, requestCode, flag, null, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, Bundle bundle){
		startActivityForResult(fromActivity, targetActivity, requestCode, -5, bundle, -5, -5);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int inAnimation, int outAnimation){
		startActivityForResult(fromActivity, targetActivity, requestCode, -5, null, inAnimation, outAnimation);
	}
	
	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode){
		startActivityForResult(fromActivity, targetActivity, requestCode, -5, null, -5, -5);
	}
	
	/**
	 * 获取当前屏幕的尺寸
	 * @param context
	 * @return
	 */
	public static Size getScreenSize(Context context){
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Size size = new Size(display.getWidth(), display.getHeight());
		return size;
	}
	
	/**
	 * 打开拨号界面，需要CALL_PHONE权限
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能打开拨号界面
	 */
	public static void openDialing(Activity activity){
		activity.startActivity(new Intent(Intent.ACTION_DIAL));
	}
	
	/**
	 * 打开给定的页面
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能打开给定的页面
	 * @param url 要打开的web页面的地址
	 */
	public static void openWebBrowser(Activity activity, String url){
		activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}
	
	/**
	 * 呼叫给定的电话号码，需要CALL_PHONE权限
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能呼叫给定的电话
	 * @param phoneNumber 要呼叫的电话号码
	 */
	public static void call(Activity activity, String phoneNumber){
		activity.startActivity(new Intent(Intent.ACTION_CALL, UriUtils.getCallUri(phoneNumber)));
	}
	
	/**
	 * 获取所有联系人的姓名和电话号码，需要READ_CONTACTS权限
	 * @param context 上下文
	 * @return Cursor。姓名：CommonDataKinds.Phone.DISPLAY_NAME；号码：CommonDataKinds.Phone.NUMBER
	 */
	public static Cursor getContactsNameAndNumber(Context context){
		return context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {
				CommonDataKinds.Phone.DISPLAY_NAME, CommonDataKinds.Phone.NUMBER}, null, null, CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
	}
	
	/**
	 * 发送短信，需要SEND_SMS权限
	 * @param context 上下文
	 * @param number 电话号码
	 * @param messageContent 短信内容，如果长度过长将会发多条发送
	 */
	public static void sendMessage(Context context, String number, String messageContent){
		SmsManager smsManager = SmsManager.getDefault();
		List<String> contentList = smsManager.divideMessage(messageContent);
		for(String content : contentList){
			smsManager.sendTextMessage(number, null, content, null, null);
		}
	}
	
	/**
	 * 为给定的字符串添加HTML红色标记，当使用Html.fromHtml()方式显示到TextView 的时候其将是红色的
	 * @param string 给定的字符串
	 * @return
	 */
	public static String addHtmlRedFlag(String string){
		return "<font color=\"red\">"+string+"</font>";
	}
	
	/**
	 * 将给定的字符串中所有给定的关键字标红
	 * @param sourceString 给定的字符串
	 * @param keyword 给定的关键字
	 * @return 返回的是带Html标签的字符串，在使用时要通过Html.fromHtml()转换为Spanned对象再传递给TextView对象
	 */
	public static String keywordMadeRed(String sourceString, String keyword){
		String result = "";
		if(sourceString != null && !"".equals(sourceString.trim())){
			if(keyword != null && !"".equals(keyword.trim())){
				result = sourceString.replaceAll(keyword, "<font color=\"red\">"+keyword+"</font>"); 
			}else{
				result = sourceString;
			}
		}
		return result;
	}
	
	/**
	 * 当前是否是横屏
	 * @param context
	 * @return
	 */
	public static final boolean isLandscape(Context context){
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}
	
	/**
	 * 为给定的编辑器开启软键盘
	 * @param context 
	 * @param editText 给定的编辑器
	 */
	public static void openSoftKeyboard(Context context, EditText editText){
		editText.requestFocus();
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}
	
	/**
	 * 关闭软键盘
	 * @param context
	 */
	public static void closeSoftKeyboard(Activity activity){
		try{
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			//如果软键盘已经开启
			if(inputMethodManager.isActive()){
				inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 切换软键盘的状态
	 * @param context
	 */
	public static void toggleSoftKeyboardState(Context context){
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * 获取设备ID
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context){
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
}