package net.sinoace.library.app;

import java.io.File;
import java.util.List;

import net.sinoace.library.net.AccessNetworkListener;
import net.sinoace.library.net.HttpRequest;
import net.sinoace.library.net.Request;
import net.sinoace.library.net.ResponseHandler;
import net.sinoace.library.utils.AnimationUtils;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

/**
 * Activity基类接口
 */
public interface BaseActivityInterface {
	/**
	 * 最小用时
	 */
	int MIN_USE_TIME = 1000;
	/**
	 * 对话框 - 消息对话框
	 */
	int DIALOG_MESSAGE = -1212343;
	/**
	 * 对话框 - 进度对话框
	 */
	int DIALOG_PROGRESS = -1212346;
	/**
	 * 键 - 对话框消息
	 */
	String KEY_DIALOG_MESSAGE = "KEY_DIALOG_MESSAGE";
	/**
	 * 键 - 对话框确认按钮的名字
	 */
	String KEY_DIALOG_CONFRIM_BUTTON_NAME = "KEY_DIALOG_CONFRIM_BUTTON_NAME";
	/**
	 * Prefences选项 - 第一次使用
	 */
	String PRFERENCES_FIRST_USING = "FIRST_USING";
	

	/**
	 * 是否启用双击返回按钮退出应用程序功能
	 * @return
	 */
	public boolean isEnableDoubleClickBackButtonExitApplication();
	
	/**
	 * 当启用双击返回按钮退出应用程序的功能时，通过此方法来获取双击的最大间隔时间
	 * @return
	 */
	public int onGetDoubleClickSpacingInterval();
	
	/**
	 * 当启用双击返回按钮退出应用程序的功能时，用户第一次点击返回按钮的时候提示用户再次点击将退出程序。
	 */
	public void onPromptExitApplication();
	
	/**
	 * 主线程接收消息
	 * @param message 要处理的消息
	 */
	public void receiveMessage(Message message);
	
	/**
	 * 主线程接收广播
	 * @param intent
	 */
	public void receiveBroadcast(Intent intent);
	
	
	
	/* ********************************************** 常用 ************************************************ */
	/**
	 * 终止应用程序
	 */
	public void finishApplication();
	
	/**
	 * 获取默认的Preferecnes对象
	 * @return 默认的Preferecnes对象
	 */
	public SharedPreferences getDefultPreferences();
	
	/**
	 * 判断是否是第一次使用
	 * @return 是否是第一次使用
	 */
	public boolean isFirstUsing();
	
	/**
	 * 设置是否是第一次使用
	 * @param firstUsing 是否是第一次使用
	 */
	public void setFirstUsing(boolean firstUsing);

	
	
	/* ********************************************** 提示视图 ************************************************ */
	/**
	 * 显示正在加载提示视图
	 */
	public void showLoadingHintView();
	
	/**
	 * 关闭正在加载提示视图
	 */
	public void closeLoadingHintView();
	
	/**
	 * 显示列表为空提示视图
	 */
	public void showListEmptyHintView();
	
	/**
	 * 关闭列表为空提示视图
	 */
	public void closeListEmptyHintView();
	 
	/**
	 * 获取正在加载提示视图的ID
	 * @return 正在加载提示视图的ID
	 */
	public int getLoadingHintViewId();
	 
	/**
	 * 获取列表为空提示视图的ID
	 * @return 列表为空提示视图的ID
	 */
	public int getListEmptyHintViewId();
	 
	/**
	 * 点击列表为空提示视图
	 */
	public void clickListEmptyHintView();
	
	
	
	/* ********************************************** 网络 ************************************************ */
	/**
	 * 访问网络
	 * @param httpRequest 请求对象
	 * @param responseHandler 响应处理器
	 * @param accessNetworkListener 访问监听器
	 */
	public void accessNetwork(HttpRequest httpRequest, ResponseHandler responseHandler, AccessNetworkListener<?> accessNetworkListener);
	
	/**
	 * 访问网络
	 * @param request 请求对象
	 * @param responseHandler 响应处理器
	 * @param accessNetworkListener 访问监听器
	 */
	public void accessNetwork(Request request, ResponseHandler responseHandler, AccessNetworkListener<?> accessNetworkListener);
	
	/**
	 * 判断网络是否可用
	 * @return true：可用；false：不可用
	 */
	public boolean isNetworkAvailable();
	
	/**
	 * 获取主机服务器的地址
	 * @return
	 */
	public String getHostServerAddress();
	
	
	
	/* ********************************************** 消息/广播 ************************************************ */
	/**
	 * 发送一个没有内容的消息给主线程，其what等于-5
	 */
	public void sendMessage();
	
	/**
	 * 发送消息到主线程，然后在onReceivedMessage()方法中处理
	 * @param message 要发送的消息
	 */
	public void sendMessage(Message message);
	
	/**
	 * 发送消息到主线程，然后在onReceivedMessage()方法中处理
	 * @param what 消息标记，当收到消息时用这个字段来区分该如何执行
	 */
	public void sendMessage(int what);
	
	/**
	 * 发送消息到主线程，然后在onReceivedMessage()方法中处理
	 * @param what 消息标记，当收到消息时用这个字段来区分该如何执行
	 * @param bundle 数据
	 */
	public void sendMessage(int what, Bundle bundle);
	
	/**
	 * 发送消息到主线程，然后在onReceivedMessage()方法中处理
	 * @param what 消息标记，当收到消息时用这个字段来区分该如何执行
	 * @param object 将要携带的对象，将放在Message.obj对象上
	 */
	public void sendMessage(int what, Object object);
	
	/**
	 * 打开广播接收器
	 * @param filterAction Itent过滤器的动作
	 */
	public void openBroadcastReceiver(String filterAction);
	
	/**
	 * 关闭广播接收器
	 */
	public void closeBroadcastReceiver();
	
	
	
	
	/* ********************************************** Toast ************************************************ */
	/**
	 * 吐出一个显示时间较长的提示
	 * @param resId 显示内容资源ID
	 */
	public void toastL(int resId);
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param resId 显示内容资源ID
	 */
	public void toastS(int resId);
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param content 显示内容
	 */
	public void toastL(String content);
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param content 显示内容
	 */
	public void toastS(String content);
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param args 参数数组
	 */
	public void toastL(int formatResId, Object... args);
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param args 参数数组
	 */
	public void toastS(int formatResId, Object... args);
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param format 被格式化的字符串
	 * @param args 参数数组
	 */
	public void toastL(String format, Object... args);
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param format 被格式化的字符串
	 * @param args 参数数组
	 */
	public void toastS(String format, Object... args);
	
	
	
	/* ********************************************** 启动Activity ************************************************ */
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation);

	/**
	 * 当启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void onStartActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, boolean isClose, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, Bundle bundle, boolean isClose, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, boolean isClose);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public void startActivity(Class<?> targetActivity, Bundle bundle, boolean isClose);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, boolean isClose, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public void startActivity(Class<?> targetActivity, boolean isClose);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public void startActivity(Class<?> targetActivity, Bundle bundle);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 */
	public void startActivity(Class<?> targetActivity, int flag);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 */
	public void startActivity(Class<?> targetActivity);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 当启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void onStartActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, Bundle bundle);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode);
	
	
	
	/* ********************************************** 终止Activity ************************************************ */
	/**
	 * 终止当前Activity
	 */
	public void finishActivity();
	
	/**
	 * 当终止当前Activity
	 */
	public void onFinishActivity();
	
	/**
	 * 终止当前Activity
	 * @param inAnimation 下一个Activity的进入动画
	 * @param outAnimation 当前Activity的退出动画
	 */
	public void finishActivity(int inAnimation, int outAnimation);
	
	/**
	 * 终止当前Activity
	 * @param inAnimation 下一个Activity的进入动画
	 * @param outAnimation 当前Activity的退出动画
	 */
	public void onFinishActivity(int inAnimation, int outAnimation);
	
	/**
	 * 终止给定ID的Activity
	 * @param id 要终止的Activity的ID
	 */
	public void finishActivity(long id);
	
	/**
	 * 终止给定ID数组中包含的Activity
	 * @param ids 要终止的Activity的ID数组
	 */
	public void finishActivitys(long[] ids);
	
	/**
	 * 终止给定ID集合中包含的Activity
	 * @param ids 要终止的Activity的ID集合
	 */
	public void finishActivitys(List<Long> ids);
	
	/**
	 * 销毁除当前Activity之外的所有Activity
	 */
	public void finishOtherActivitys();
	
	/**
	 * 因异常而需要终止Activity
	 */
	public void becauseExceptionFinishActivity();
	
	/**
	 * 当因为异常而需要终止Activity
	 */
	public void onBecauseExceptionFinishActivity();
	
	/**
	 * 将当前Activity放到等待终止的Activity列表中
	 */
	public void putToWaitFinishActivitys();
	
	/**
	 * 将当前Activity从等待终止的Activity列表中移除
	 * @return 移除之前列表中是否存在当前Activity
	 */
	public boolean removeFromWaitFinishActivitys();
	
	/**
	 * 把等待终止的Activity列表清空
	 */
	public void clearWaitFinishActivitys();
	
	/**
	 * 终止所有等待中的Activity，终止之后其ID自动从等待列表中清除
	 */
	public void finishAllWaitingActivity();
	
	
	
	/* ********************************************** 对话框 ************************************************ */
	/**
	 * 显示消息对话框
	 * @param message 要显示的消息
	 * @param confrimButtonName 确认按钮的名字
	 */
	public void showMessageDialog(String message, String confrimButtonName);
	
	/**
	 * 显示消息对话框
	 * @param message 要显示的消息的ID
	 * @param confrimButtonName 确认按钮的名字的ID
	 */
	public void showMessageDialog(int messageId, int confrimButtonNameId);
	
	/**
	 * 关闭消息对话框
	 */
	public void closeMessageDialog();
	
	/**
	 * 显示进度对话框
	 * @param message 要显示的消息
	 */
	public void showProgressDialog(String message);
	
	/**
	 * 显示进度对话框
	 * @param message 要显示的消息的ID
	 */
	public void showProgressDialog(int messageId);
	
	/**
	 * 关闭进度对话框
	 */
	public void closeProgressDialog();
	
	
	
	/* ********************************************** 资源管理 ************************************************ */
	/**
	 * 获取动画
	 * @param resId 资源ID
	 * @return 动画
	 */
	public Animation getAnimation(int resId);
	
	/**
	 * 获取布局动画
	 * @param ersId 资源ID
	 * @return 布局动画
	 */
	public LayoutAnimationController getLayoutAnimation(int ersId);
	
	/**
	 * 获取插值器
	 * @param resId 资源ID
	 * @return 插值器
	 */
	public Interpolator getInterpolator(int resId);
	
	/**
	 * 获取布尔值
	 * @param resId 资源ID
	 * @return 布尔值
	 */
	public boolean getBoolean(int resId);
	
	/**
	 * 获取颜色
	 * @param resId 资源ID
	 * @return 颜色
	 */
	public int getColor(int resId);
	
	/**
	 * 获取颜色状态列表
	 * @param resId 资源ID
	 * @return 颜色状态列表
	 */
	public ColorStateList getColorStateList(int resId);
	
	/**
	 * 获取尺寸
	 * @param resId 资源ID
	 * @return 尺寸
	 */
	public float getDimension(int resId);
	
	/**
	 * 获取根据维度便宜后的尺寸
	 * @param resId 资源ID
	 * @return 根据维度便宜后的尺寸
	 */
	public float getDimensionPixelOffset(int resId);
	
	/**
	 * 获取根据维度便宜后的大小值
	 * @param resId 资源ID
	 * @return 根据维度便宜后的大小值
	 */
	public float getDimensionPixelSize(int resId);
	
	/**
	 * 获取图片
	 * @param resId 资源ID
	 * @return 图片
	 */
	public Drawable getDrawable(int resId);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @param base
	 * @param pbase
	 * @return
	 */
	public float getFraction(int resId, int base, int pbase);
	
	/**
	 * 获取标示符
	 * @param name
	 * @param defType
	 * @param defPackage
	 * @return 标示符
	 */
	public int getIdentifier(String name, String defType, String defPackage);
	
	/**
	 * 获取整数数组
	 * @param resId 资源ID
	 * @return 整数数组
	 */
	public int[] getIntArray(int resId);
	
	/**
	 * 获取整数
	 * @param resId 资源ID
	 * @return 整数
	 */
	public int getInteger(int resId);
	
	/**
	 * 获取布局资源
	 * @param resId 资源ID
	 * @return 布局资源
	 */
	public XmlResourceParser getLayout(int resId);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @return
	 */
	public Movie getMovie(int resId);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @param quantity
	 * @return
	 */
	public String getQuantityString(int resId, int quantity);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @param quantity
	 * @param formatArgs
	 * @return
	 */
	public String getQuantityString(int resId, int quantity, Object... formatArgs);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @param quantity
	 * @return
	 */
	public CharSequence getQuantityText(int resId, int quantity);
	
	/**
	 * 获取字符串数组
	 * @param resId 资源ID
	 * @return 字符串数组
	 */
	public String[] getStringArray(int resId);
	
	/**
	 * 获取资源实体的名字
	 * @param resId 资源ID
	 * @return 资源实体的名字
	 */
	public String getResourceEntryName(int resId);
	
	/**
	 * 获取资源名称
	 * @param resId 资源ID
	 * @return 资源名称
	 */
	public String getResourceName(int resId);
	
	/**
	 * 获取资源包名字
	 * @param resId 资源ID
	 * @return 资源包名字
	 */
	public String getResourcePackageName(int resId);
	
	/**
	 * 获取资源类型名字
	 * @param resId 资源ID
	 * @return 资源类型名字
	 */
	public String getResourceTypeName(int resId);
	
	/**
	 * 获取文本
	 * @param resId 资源ID
	 * @param defSequence 默认值
	 * @return 文本
	 */
	public CharSequence getText(int resId, CharSequence defSequence);
	
	/**
	 * 获取文本数组
	 * @param resId 资源ID
	 * @return 文本数组
	 */
	public CharSequence[] getTextArray(int resId);
	
	/**
	 * 获取XML资源
	 * @param resId 资源ID
	 * @return XML资源
	 */
	public XmlResourceParser getXml(int resId);
	
	/**
	 * 根据布局文件ID获取视图
	 * @param resId 布局文件ID
	 * @param parentView 其父容器
	 * @return 视图
	 */
	public View getViewByLayout(int resId, ViewGroup parentView);
	
	/**
	 * 根据布局文件ID获取视图
	 * @param resId 布局文件ID
	 * @return 视图
	 */
	public View getViewByLayout(int resId);
	
	/**
	 * 获取动态文件目录
	 * @return 如果SD卡可用，就返回外部文件目录，否则返回机身自带文件目录
	 */
	public File getDynamicFilesDir();
	
	/**
	 * 获取动态获取缓存目录
	 * @return 如果SD卡可用，就返回外部缓存目录，否则返回机身自带缓存目录
	 */
	public File getDynamicCacheDir();
	
	/**
	 * 从文件目录中获取一个文件
	 * @param fileName 要获取的文件的名称
	 * @return
	 */
	public File getFileFromFilesDir(String fileName);
	
	/**
	 * 从外部文件目录中获取一个文件
	 * @param fileName 要获取的文件的名称
	 * @return null：SD卡不可用
	 */
	public File getFileFromExternalFilesDir(String fileName);
	
	/**
	 * 从文缓存目录中获取一个文件
	 * @param fileName 要获取的文件的名称
	 * @return
	 */
	public File getFileFromCacheDir(String fileName);
	
	/**
	 * 从外部缓存目录中获取一个文件
	 * @param fileName 要获取的文件的名称
	 * @return null：SD卡不可用
	 */
	public File getFileFromExternalCacheDir(String fileName);
	
	/**
	 * 从动态文件目录中获取文件
	 * @param fileName 要获取的文件的名称
	 * @return 如果SD卡可用，就返回外部文件目录中获取文件，否则从机身自带文件目录中获取文件
	 */
	public File getFileFromDynamicFilesDir(String fileName);
	
	/**
	 * 从动态缓存目录中获取文件
	 * @param fileName 要获取的文件的名称
	 * @return 如果SD卡可用，就返回外部缓存目录中获取文件，否则从机身自带缓存目录中获取文件
	 */
	public File getFileFromDynamicCacheDir(String fileName);
	
	
	
	
	/* ********************************************** GET/SET ************************************************ */
	/**
	 * 获取当前Activity的ID
	 * @return
	 */
	public long getActivityId();

	/**
	 * 设置当前Activity的ID
	 * @param activityId 当前Activity的ID
	 */
	public void setActivityId(long activityId);

	/**
	 * 获取消息处理器
	 * @return 消息处理器
	 */
	public MessageHandler getMessageHanlder();

	/**
	 * 设置消息处理器
	 * @param messageHanlder 消息处理器
	 */
	public void setMessageHanlder(MessageHandler messageHanlder);

	/**
	 * 获取加载中提示视图
	 * @return 加载中提示视图
	 */
	public View getLoadingHintView();

	/**
	 * 设置加载中提示视图
	 * @param loadingHintView 加载中提示视图
	 */
	public void setLoadingHintView(View loadingHintView);

	/**
	 * 获取列表为空提示视图
	 * @return 列表为空提示视图
	 */
	public View getListEmptyHintView();

	/**
	 * 设置列表为空提示视图
	 * @param listEmptyHintView 列表为空提示视图
	 */
	public void setListEmptyHintView(View listEmptyHintView);

	/**
	 * 是否加载已完成
	 * @return 是否加载已完成
	 */
	public boolean isLoadFinished();

	/**
	 * 设置是否加载已完成
	 * @param loadFinished 是否加载已完成
	 */
	public void setLoadFinished(boolean loadFinished);
	
	/**
	 * 获取广播接收器
	 * @return 广播接收器
	 */
	public MyBroadcastReceiver getBroadcastReceiver();

	/**
	 * 设置广播接收器
	 * @param broadcastReceiver 广播接收器
	 */
	public void setBroadcastReceiver(MyBroadcastReceiver broadcastReceiver);

	/**
	 * 是否已经开启了广播接收器
	 * @return 已经开启了广播接收器
	 */
	public boolean isOpenedBroadcaseReceiver();

	/**
	 * 设置已经开启了广播接收器
	 * @param openedBroadcaseReceiver 已经开启了广播接收器
	 */
	public void setOpenedBroadcaseReceiver(boolean openedBroadcaseReceiver);
	
	/**
	 * 消息处理器
	 */
	class MessageHandler extends Handler{
		public static final int SHOW_LOADING_HINT_VIEW = 40;
		public static final int CLOSE_LOADING_HINT_VIEW = 41;
		public static final int SHOW_LIST_EMPTY_HINT_VIEW = 43;
		public static final int CLOSE_LIST_EMPTY_HINT_VIEW = 44;
		public static final int SHOW_MESSAGE_DIALOG = 45;
		public static final int CLOSE_MESSAGE_DIALOG = 46;
		public static final int SHOW_PROGRESS_DIALOG = 47;
		public static final int CLOSE_PROGRESS_DIALOG = 48;
		public static final int TOAST = 300;
		public static final int BECAUSE_EXCEPTION_FINISH_ACTIVITY = 2001;
		public static final int FINISH_ACTIVITY = 2002;
		public static final int FINISH_ACTIVITY_ANIMATION = 2003;
		public static final int START_ACTIVITY = 2004;
		public static final int START_ACTIVITY_FOR_RESULT = 2005;
		
		public static final String FLAG = "FLAG";
		public static final String IS_CLOSE = "IS_CLOSE";
		public static final String IN_ANIMATION = "IN_ANIMATION";
		public static final String OUT_ANIMATION = "OUT_ANIMATION";
		public static final String HAVE_BUNDLE = "HAVE_BUNDLE";
		public static final String REQUEST_CODE = "REQUEST_CODE";
		
		private BaseActivityInterface baseActivityInterface;
		private Activity activity;
		
		public MessageHandler(BaseActivityInterface baseActivityInterface){
			this.baseActivityInterface = baseActivityInterface;
			activity = (Activity) baseActivityInterface;
		}
		
		@Override
		public void handleMessage(Message msg) {
			if(!activity.isFinishing()){
				try{
					switch(msg.what){
					case SHOW_MESSAGE_DIALOG : activity.showDialog(BaseActivityInterface.DIALOG_MESSAGE, msg.getData()); break;
					case CLOSE_MESSAGE_DIALOG : activity.dismissDialog(BaseActivityInterface.DIALOG_MESSAGE); break;
					case SHOW_PROGRESS_DIALOG : activity.showDialog(BaseActivityInterface.DIALOG_PROGRESS, msg.getData()); break;
					case CLOSE_PROGRESS_DIALOG : activity.dismissDialog(BaseActivityInterface.DIALOG_PROGRESS); break;
					case SHOW_LOADING_HINT_VIEW : 
						//如果尚未初始化加载中提示视图。并且加载中视图ID是合法的，就获取加载中提示视图
						if(baseActivityInterface.getLoadingHintView() == null && baseActivityInterface.getLoadingHintViewId() != 0){
							baseActivityInterface.setLoadingHintView(activity.findViewById(baseActivityInterface.getLoadingHintViewId()));
						}
						
						//如果已经获取了加载中提示视图并且尚未显示
						if(baseActivityInterface.getLoadingHintView() != null && baseActivityInterface.getLoadingHintView().getVisibility() != View.VISIBLE){
							baseActivityInterface.getLoadingHintView().setVisibility(View.VISIBLE);
							//标记加载未完成
							baseActivityInterface.setLoadFinished(false);
						}
						break;
					case CLOSE_LOADING_HINT_VIEW : 
						//如果尚未初始化加载中提示视图。并且加载中视图ID是合法的，就获取加载中提示视图
						if(baseActivityInterface.getLoadingHintView() == null && baseActivityInterface.getLoadingHintViewId() != 0){
							baseActivityInterface.setLoadingHintView(activity.findViewById(baseActivityInterface.getLoadingHintViewId()));
						}
						
						//如果加载中提示视图正在显示
						if(baseActivityInterface.getLoadingHintView() != null && baseActivityInterface.getLoadingHintView().getVisibility() != View.GONE){
							baseActivityInterface.getLoadingHintView().startAnimation(AnimationUtils.getHiddenAlphaAnimation(500, new AnimationListener() {
								@Override
								public void onAnimationStart(Animation animation) {}
								@Override
								public void onAnimationRepeat(Animation animation) {}
								@Override
								public void onAnimationEnd(Animation animation) {
									baseActivityInterface.getLoadingHintView().setVisibility(View.GONE);
								}
							}));
							//标记加载完成
							baseActivityInterface.setLoadFinished(true);
						}
						break;
					case SHOW_LIST_EMPTY_HINT_VIEW : 
						//如果尚未初始化列表为空提示视图。并且列表为空提示ID是合法的，就获取列表为空提示视图
						if(baseActivityInterface.getListEmptyHintView() == null && baseActivityInterface.getListEmptyHintViewId() != 0){
							baseActivityInterface.setListEmptyHintView(activity.findViewById(baseActivityInterface.getListEmptyHintViewId()));
							baseActivityInterface.getListEmptyHintView().setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									baseActivityInterface.clickListEmptyHintView();
								}
							});
						}
						
						//如果已经获取了列表为空提示视图并且尚未显示
						if(baseActivityInterface.getListEmptyHintView() != null && baseActivityInterface.getListEmptyHintView().getVisibility() != View.VISIBLE){
							baseActivityInterface.getListEmptyHintView().setVisibility(View.VISIBLE);
						}
						break;
					case CLOSE_LIST_EMPTY_HINT_VIEW : 
						//如果尚未初始化列表为空提示视图。并且列表为空提示ID是合法的，就获取列表为空提示视图
						if(baseActivityInterface.getListEmptyHintView() == null && baseActivityInterface.getListEmptyHintViewId() != 0){
							baseActivityInterface.setListEmptyHintView(activity.findViewById(baseActivityInterface.getListEmptyHintViewId()));
							baseActivityInterface.getListEmptyHintView().setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									baseActivityInterface.clickListEmptyHintView();
								}
							});
						}
						
						//如果列表为空提示视图正在显示，就隐藏掉
						if(baseActivityInterface.getListEmptyHintView() != null && baseActivityInterface.getListEmptyHintView().getVisibility() != View.GONE){
							baseActivityInterface.getListEmptyHintView().setVisibility(View.GONE);
						}
						break;
					case TOAST : Toast.makeText(activity, (String)msg.obj, msg.arg1).show(); break;
					case BECAUSE_EXCEPTION_FINISH_ACTIVITY : baseActivityInterface.onBecauseExceptionFinishActivity(); break;
					case FINISH_ACTIVITY : baseActivityInterface.onFinishActivity(); break;
					case FINISH_ACTIVITY_ANIMATION : baseActivityInterface.onFinishActivity(msg.arg1, msg.arg2); break;
					case START_ACTIVITY : 
						baseActivityInterface.onStartActivity(
								(Class<?>)msg.obj, 
								msg.getData().getInt(MessageHandler.FLAG), 
								msg.getData().getBoolean(MessageHandler.HAVE_BUNDLE)?msg.getData():null, 
										msg.getData().getBoolean(MessageHandler.IS_CLOSE), 
										msg.getData().getInt(MessageHandler.IN_ANIMATION), 
										msg.getData().getInt(MessageHandler.OUT_ANIMATION)
								); 
						break;
					case START_ACTIVITY_FOR_RESULT : 
						baseActivityInterface.onStartActivityForResult(
								(Class<?>)msg.obj, 
								msg.getData().getInt(MessageHandler.REQUEST_CODE), 
								msg.getData().getInt(MessageHandler.FLAG), 
								msg.getData().getBoolean(MessageHandler.HAVE_BUNDLE)?msg.getData():null, 
										msg.getData().getInt(MessageHandler.IN_ANIMATION), 
										msg.getData().getInt(MessageHandler.OUT_ANIMATION)
								); 
						break;
					default :  baseActivityInterface.receiveMessage(msg); break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	}
	
	/**
	 * 广播接收器
	 */
	class MyBroadcastReceiver extends BroadcastReceiver{
		private BaseActivityInterface baseActivityInterface;
		
		public MyBroadcastReceiver(BaseActivityInterface baseActivityInterface){
			setBaseActivityInterface(baseActivityInterface);
		}
		
		@Override
		public void onReceive(Context context, Intent intent) {
			baseActivityInterface.receiveBroadcast(intent);
		}

		public BaseActivityInterface getBaseActivityInterface() {
			return baseActivityInterface;
		}

		public void setBaseActivityInterface(BaseActivityInterface baseActivityInterface) {
			this.baseActivityInterface = baseActivityInterface;
		}
	}
}