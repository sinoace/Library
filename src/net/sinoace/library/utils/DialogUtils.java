package net.sinoace.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * 对话框工具箱
 * @author xiaopan
 *
 */
public class DialogUtils {
	/**
	 * 显示一个对话框
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能显示提示框
	 * @param title 标题
	 * @param message 消息
	 * @param confrimButton 确认按钮
	 * @param confrimButtonCliskListener 确认按钮点击监听器
	 * @param centerButton 中间按钮
	 * @param centerButtonCliskListener 中间按钮点击监听器
	 * @param cancelButton 取消按钮
	 * @param cancelButtonCliskListener 取消按钮点击监听器
	 * @param onShowListener 显示监听器
	 * @param cancelable 是否允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
	 * @param onCancelListener 取消监听器
	 * @param onDismissListener 销毁监听器
	 * @return 对话框
	 */
	public static AlertDialog showAlert(Activity activity, String title, String message, 
			String confrimButton, DialogInterface.OnClickListener confrimButtonCliskListener, 
			String centerButton, DialogInterface.OnClickListener centerButtonCliskListener, 
			String cancelButton, DialogInterface.OnClickListener cancelButtonCliskListener, 
			DialogInterface.OnShowListener onShowListener, 
			boolean cancelable, DialogInterface.OnCancelListener onCancelListener, 
			DialogInterface.OnDismissListener onDismissListener){
		AlertDialog.Builder promptBuilder = new AlertDialog.Builder(activity);
		if(title != null){
			promptBuilder.setTitle(title);
		}
		if(message != null){
			promptBuilder.setMessage(message);
		}
		if(confrimButton != null){
			promptBuilder.setPositiveButton(confrimButton, confrimButtonCliskListener);
		}
		if(centerButton != null){
			promptBuilder.setNeutralButton(centerButton, centerButtonCliskListener);
		}
		if(cancelButton != null){
			promptBuilder.setNegativeButton(cancelButton, cancelButtonCliskListener);
		}
		promptBuilder.setCancelable(true);
		if(cancelable){
			promptBuilder.setOnCancelListener(onCancelListener);
		}
		AlertDialog alertDialog = promptBuilder.create();
		alertDialog.setOnDismissListener(onDismissListener);
		alertDialog.setOnShowListener(onShowListener);
		alertDialog.show();
		return alertDialog;
	}
	
	/**
	 * 显示一个对话框
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能显示提示框
	 * @param title 标题
	 * @param message 消息
	 * @param confrimButton 确认按钮
	 * @param confrimButtonCliskListener 确认按钮点击监听器
	 * @param cancelButton 取消按钮
	 * @param cancelButtonCliskListener 取消按钮点击监听器
	 * @return 对话框
	 */
	public static AlertDialog showAlert(Activity activity, String title, String message, 
			String confrimButton, DialogInterface.OnClickListener confrimButtonCliskListener, 
			String cancelButton, DialogInterface.OnClickListener cancelButtonCliskListener){
		return showAlert(activity, title, message, confrimButton, confrimButtonCliskListener, null, null, cancelButton, cancelButtonCliskListener, null, true, null, null);
	}
	
	/**
	 * 显示一个提示框
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能显示提示框
	 * @param message 提示的消息
	 * @param confrimButton 确定按钮的名字
	 */
	public static AlertDialog showPrompt(Activity activity, String message, String confrimButton){
		return showAlert(activity, null, message, confrimButton, null, null, null, null, null, null, true, null, null);
	}
	
	/**
	 * 显示一个提示框
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能显示提示框
	 * @param message 提示的消息
	 */
	public static AlertDialog showPrompt(Activity activity, String message){
		return showAlert(activity, null, message, "OK", null, null, null, null, null, null, true, null, null);
	}
	
	/**
	 * 设置给定的对话框点击是否关闭
	 * @param alertDialog 给定的对话框
	 * @param close 点击是否关闭
	 */
	public static void setDialogClickClose(AlertDialog alertDialog, boolean close){
		ClassUtils.setField(alertDialog, "mShowing", close, true, true);
	}
}