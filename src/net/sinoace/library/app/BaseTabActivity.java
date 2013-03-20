package net.sinoace.library.app;

import java.io.File;
import java.util.List;

import net.sinoace.library.net.AccessNetworkAsyncTask;
import net.sinoace.library.net.AccessNetworkListener;
import net.sinoace.library.net.AccessNetworkUtils;
import net.sinoace.library.net.HttpRequest;
import net.sinoace.library.net.NetworkUtils;
import net.sinoace.library.net.Request;
import net.sinoace.library.net.ResponseHandler;
import net.sinoace.library.utils.AndroidUtils;
import net.sinoace.library.utils.SDCardUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

/**
 * 自定义抽象的Activity基类
 */
public abstract class BaseTabActivity extends TabActivity implements BaseActivityInterface{
	/**
	 * 当前Activity在ActivityManager中的ID
	 */
	private long activityId = -5;
	/**
	 * 主线程消息处理器
	 */
	private MessageHandler messageHanlder;
	/**
	 * 正在加载视图
	 */
	private View loadingHintView;
	/**
	 * 列表为空提示视图
	 */
	private View listEmptyHintView;
	/**
	 * 加载已完成
	 */
	private boolean loadFinished = true;
	/**
	 * 广播接收器
	 */
	private MyBroadcastReceiver broadcastReceiver;
	/**
	 * 已经打开了广播接收器
	 */
	private boolean openedBroadcaseReceiver;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 记录上次点击返回按钮的时间，用来配合实现双击返回按钮退出应用程序的功能
	 */
	private static long lastClickBackButtonTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		//记录创建时间，用于异常终止时判断是否需要等待一段时间再终止，因为时间过短的话体验不好
		createTime = System.currentTimeMillis();
		//将当前Activity放入ActivityManager中，并获取其ID
		activityId = ActivityManager.getInstance().putActivity(this);	
		//如果需要去掉标题栏
		if(isRemoveTitleBar()){																							
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		//如果需要全屏（去掉通知栏）
		if(isFullScreen()){
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		//实例化消息处理器
		messageHanlder = new MessageHandler(this);
		//在初始化之前
		onPreInit(savedInstanceState);											
		//初始化布局
		onInitLayout(savedInstanceState);								
		//初始化监听器
		onInitListener(savedInstanceState);						
		//初始化数据
		onInitData(savedInstanceState);
		//在初始化之后
		onPostInit(savedInstanceState);
	}
	
	/**
	 * 在初始化之前
	 * @param savedInstanceState
	 */
	protected void onPreInit(Bundle savedInstanceState){
		
	}
	
	/**
	 * 在初始化之后
	 * @param savedInstanceState
	 */
	protected void onPostInit(Bundle savedInstanceState){
		
	}
	
	/**
	 * 初始化布局
	 */
	protected abstract void onInitLayout(Bundle savedInstanceState);

	/**
	 * 初始化监听器，设置视图的监听器
	 */
	protected abstract void onInitListener(Bundle savedInstanceState);

	/**
	 * 初始化数据，为视图初始化数据
	 */
	protected abstract void onInitData(Bundle savedInstanceState);
	
	@Override
	protected void onDestroy() {
		ActivityManager.getInstance().removeActivity(getActivityId());
		closeBroadcastReceiver();
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		if(isEnableDoubleClickBackButtonExitApplication()){
			long currentMillisTime = System.currentTimeMillis();
			//两次点击的间隔时间尚未超过规定的间隔时间将执行退出程序
			if(lastClickBackButtonTime != 0 && (currentMillisTime - lastClickBackButtonTime) < onGetDoubleClickSpacingInterval()){
				finishApplication();
			}else{
				onPromptExitApplication();
				lastClickBackButtonTime = currentMillisTime;
			}
		}else{
			finishActivity();
		}
	}
	
	@Override
	public boolean isEnableDoubleClickBackButtonExitApplication(){
		return false;
	}
	
	@Override
	public int onGetDoubleClickSpacingInterval(){
		return 2000;
	}
	
	@Override
	public void onPromptExitApplication(){
		toastS("2秒之内再次点击返回按钮将退出程序！");
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog = null;
		switch(id){
			case DIALOG_MESSAGE : 
					AlertDialog messageDialog = new AlertDialog.Builder(this).create();
					if(args != null){
						messageDialog.setMessage(args.getString(KEY_DIALOG_MESSAGE));
						messageDialog.setButton(args.getString(KEY_DIALOG_CONFRIM_BUTTON_NAME), new android.content.DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialog, int which) {} });
					}
					dialog = messageDialog;
				break;
			case DIALOG_PROGRESS : 
				ProgressDialog progressDialog = new ProgressDialog(this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setCancelable(false);
				if(args != null){
					progressDialog.setMessage(args.getString(KEY_DIALOG_MESSAGE));
				}
				dialog = progressDialog;
				break;
		}
		return dialog;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		switch(id){
			case DIALOG_MESSAGE : 
				if(args != null){
					AlertDialog messageDialog = (AlertDialog) dialog;
					messageDialog.setMessage(args.getString(KEY_DIALOG_MESSAGE));
					messageDialog.setButton(args.getString(KEY_DIALOG_CONFRIM_BUTTON_NAME), new android.content.DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialog, int which) {} });
				}
				break;
			case DIALOG_PROGRESS : 
				if(args != null){
					((ProgressDialog) dialog).setMessage(args.getString(KEY_DIALOG_MESSAGE));
				}
				break;
		}
	}
	
	@Override
	public final void receiveMessage(Message message){
		onReceivedMessage(message);
	};
	
	/**
	 * 当接收到了一个消息
	 * @param message 收到的消息
	 */
	protected void onReceivedMessage(Message message){
		
	}
	
	@Override
	public final void receiveBroadcast(Intent intent){
		onReceivedBroadcast(intent);
	}
	
	/**
	 * 当接收到了一个广播
	 * @param intent 收到的广播
	 */
	protected void onReceivedBroadcast(Intent intent){
		
	}
	
	
	/* ********************************************** 常用 ************************************************ */
	@Override
	public final void finishApplication(){
		ActivityManager.getInstance().finishApplication();
	}
	
	/**
	 * 判断在启动或者终止Activity的时候是否使用自定义动画
	 * @return 在启动或者终止Activity的时候是否使用自定义动画
	 */
	protected boolean isUseCustomAnimation(){
		return true;
	}
	
	/**
	 * 判断是否需要去除标题栏，默认不去除
	 * @return 是否需要去除标题栏
	 */
	protected boolean isRemoveTitleBar() {
		return false;
	}
	
	/**
	 * 判断是否需要全屏，默认不全屏
	 * @return 是否需要全屏
	 */
	protected boolean isFullScreen() {
		return false;
	}
	
	@Override
	public final SharedPreferences getDefultPreferences(){
		return PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}
	
	@Override
	public final boolean isFirstUsing(){
		return getDefultPreferences().getBoolean(PRFERENCES_FIRST_USING, true);
	}
	
	@Override
	public final void setFirstUsing(boolean firstUsing){
		Editor editor = getDefultPreferences().edit();
		editor.putBoolean(PRFERENCES_FIRST_USING, firstUsing);
		editor.commit();
	} 
	
	/**
	 * 当需要获取默认的启动Activity动画
	 * @return 默认的启动Activity动画
	 */
	protected int[] onGetDefaultStartActivityAnimation(){
		return null;
	}
	
	/**
	 * 当需要获取默认的终止Activity动画
	 * @return 默认的终止Activity动画
	 */
	protected int[] onGetDefaultFinishActivityAnimation(){
		return null;
	} 
	

	
	/* ********************************************** 提示视图 ************************************************ */
	@Override
	public final void showLoadingHintView(){
		Message message = messageHanlder.obtainMessage();
		message.what = MessageHandler.SHOW_LOADING_HINT_VIEW;
		message.sendToTarget();
	}
	
	@Override
	public final void closeLoadingHintView(){
		Message message = new Message();
		message.what = MessageHandler.CLOSE_LOADING_HINT_VIEW;
		sendMessage(message);
	}
	
	@Override
	public final void showListEmptyHintView(){
		Message message = new Message();
		message.what = MessageHandler.SHOW_LIST_EMPTY_HINT_VIEW;
		sendMessage(message);
	}
	
	@Override
	public final void closeListEmptyHintView(){
		Message message = new Message();
		message.what = MessageHandler.CLOSE_LIST_EMPTY_HINT_VIEW;
		sendMessage(message);
	}
	 
	@Override
	public final int getLoadingHintViewId(){
		return onGetLoadingHintViewId();
	}
	
	/** 
	 * 当获取正在加载提示视图的ID时
	 * @return 正在加载提示视图的ID时
	 */
	protected int onGetLoadingHintViewId(){
		return 0;
	}

	@Override
	public final int getListEmptyHintViewId(){
		return onGetListEmptyHintViewId();
	}
	
	/**
	 * 当获取列表为空提示视图的ID时
	 * @return 列表为空提示视图的ID
	 */
	protected int onGetListEmptyHintViewId(){
		return 0;
	}
	 
	@Override
	public final void clickListEmptyHintView(){
		onClickListEmptyHintView();
	}
	
	/**
	 * 当点击列表为空提示视图时
	 */
	protected void onClickListEmptyHintView(){
		
	}
	
	
	/* ********************************************** 网络 ************************************************ */
	@Override
	public final void accessNetwork(HttpRequest httpRequest, ResponseHandler responseHandler, AccessNetworkListener<?> accessNetworkListener){
		if(NetworkUtils.isEnabled(getBaseContext())){
			new AccessNetworkAsyncTask(this, httpRequest, responseHandler, accessNetworkListener).execute(0);
		}else{
			accessNetworkListener.onNetworkNotAvailable(this);
		}
	}
	
	@Override
	public final void accessNetwork(Request request, ResponseHandler responseHandler, AccessNetworkListener<?> accessNetworkListener){
		try{
			accessNetwork(AccessNetworkUtils.toHttpRequest(request, getHostServerAddress()), responseHandler, accessNetworkListener);
		}catch(Exception e){
			if(accessNetworkListener != null){
				accessNetworkListener.onException(e, this);
			}
		}
	}
	
	@Override
	public final boolean isNetworkAvailable() {
		boolean result = false;
		result = NetworkUtils.isEnabled(getBaseContext());
		if(!result){
			onNetworkNotAvailable();
		}
		return result;
	}

	/**
	 * 在验证网络是否可用时发现网络不可用
	 */
	protected void onNetworkNotAvailable() {
		
	}

	/* ********************************************** 消息/广播 ************************************************ */
	@Override
	public final void sendMessage(){
		sendMessage(-5);
	}
	
	@Override
	public final void sendMessage(Message message){
		message.setTarget(messageHanlder);
		message.sendToTarget();
	}
	
	@Override
	public final void sendMessage(int what){
		Message message = new Message();
		message.what = what;
		sendMessage(message);
	}
	
	@Override
	public final void sendMessage(int what, Bundle bundle){
		Message message = new Message();
		message.what = what;
		message.setData(bundle);
		sendMessage(message);
	}
	
	@Override
	public final void sendMessage(int what, Object object){
		Message message = new Message();
		message.what = what;
		message.obj = object;
		sendMessage(message);
	}
	
	@Override
	public final void openBroadcastReceiver(String filterAction){
		setOpenedBroadcaseReceiver(true);
		setBroadcastReceiver(new MyBroadcastReceiver(this));
	    registerReceiver(getBroadcastReceiver(), new IntentFilter(filterAction));
	}
	
	@Override
	public final void closeBroadcastReceiver(){
		if(getBroadcastReceiver() != null){
			unregisterReceiver(getBroadcastReceiver());
		}
		setOpenedBroadcaseReceiver(false);
		setBroadcastReceiver(null);
	}
	
	
	/* ********************************************** Toast ************************************************ */
	@Override
	public final void toastL(int resId){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_LONG;
		message.obj = getString(resId);
		sendMessage(message);
	}
	
	@Override
	public final void toastS(int resId){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_SHORT;
		message.obj = getString(resId);
		sendMessage(message);
	}
	
	@Override
	public final void toastL(String content){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_LONG;
		message.obj = content;
		sendMessage(message);
	}
	
	@Override
	public final void toastS(String content){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_SHORT;
		message.obj = content;
		sendMessage(message);
	}
	
	@Override
	public final void toastL(int formatResId, Object... args){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_LONG;
		message.obj = getString(formatResId, args);
		sendMessage(message);
	}
	
	@Override
	public final void toastS(int formatResId, Object... args){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_SHORT;
		message.obj = getString(formatResId, args);
		sendMessage(message);
	}
	
	@Override
	public final void toastL(String format, Object... args){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_LONG;
		message.obj = String.format(format, args);
		sendMessage(message);
	}
	
	@Override
	public final void toastS(String format, Object... args){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_SHORT;
		message.obj = String.format(format, args);
		sendMessage(message);
	}
	
	
	
	/* ********************************************** 启动Activity ************************************************ */
	@Override
	public final void startActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		Bundle bundle2 = null;
		if(bundle != null){
			bundle2 = bundle;
			bundle2.putBoolean(MessageHandler.HAVE_BUNDLE, true);
		}else{
			bundle2 = new Bundle();
			bundle2.putBoolean(MessageHandler.HAVE_BUNDLE, false);
		}
		bundle2.putInt(MessageHandler.FLAG, flag);
		bundle2.putBoolean(MessageHandler.IS_CLOSE, isClose);
		bundle2.putInt(MessageHandler.IN_ANIMATION, inAnimation);
		bundle2.putInt(MessageHandler.OUT_ANIMATION, outAnimation);
		Message message = new Message();
		message.what = MessageHandler.START_ACTIVITY;
		message.obj = targetActivity;
		message.setData(bundle2);
		sendMessage(message);
	}
	
	@Override
	public final void onStartActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		if(isUseCustomAnimation()){
			if(inAnimation > 0 && outAnimation > 0){
				AndroidUtils.startActivity(this, targetActivity, flag, bundle, isClose, inAnimation, outAnimation);
			}else{
				int[] animations = onGetDefaultStartActivityAnimation();
				if(animations != null && animations.length >= 2){
					AndroidUtils.startActivity(this, targetActivity, flag, bundle, isClose, animations[0], animations[1]);
				}else{
					AndroidUtils.startActivity(this, targetActivity, flag, bundle, isClose);
				}
			}
		}else{
			AndroidUtils.startActivity(this, targetActivity, flag, bundle, isClose);
		}
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose){
		startActivity(targetActivity, flag, bundle, isClose, -5, -5);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, int flag, Bundle bundle, int inAnimation, int outAnimation){
		startActivity(targetActivity, flag, bundle, false, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, int flag, boolean isClose, int inAnimation, int outAnimation){
		startActivity(targetActivity, flag, null, isClose, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, bundle, isClose, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, int flag, Bundle bundle){
		startActivity(targetActivity, flag, bundle, false, -5, -5);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, int flag, boolean isClose){
		startActivity(targetActivity, flag, null, false, -5, -5);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, Bundle bundle, boolean isClose){
		startActivity(targetActivity, -5, bundle, isClose, -5, -5);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, int flag, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, null, false, -5, -5);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, Bundle bundle, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, bundle, false, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, boolean isClose, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, null, isClose, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, null, false, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, boolean isClose){
		startActivity(targetActivity, -5, null, isClose, -5, -5);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, Bundle bundle){
		startActivity(targetActivity, -5, bundle, false, -5, -5);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity, int flag){
		startActivity(targetActivity, flag, null, false, -5, -5);
	}
	
	@Override
	public final void startActivity(Class<?> targetActivity){
		startActivity(targetActivity, -5, null, false, -5, -5);
	}
	
	@Override
	public final void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation){
		Bundle bundle2 = null;
		if(bundle != null){
			bundle2 = bundle;
			bundle2.putBoolean(MessageHandler.HAVE_BUNDLE, true);
		}else{
			bundle2 = new Bundle();
			bundle2.putBoolean(MessageHandler.HAVE_BUNDLE, false);
		}
		bundle2.putInt(MessageHandler.REQUEST_CODE, requestCode);
		bundle2.putInt(MessageHandler.FLAG, flag);
		bundle2.putInt(MessageHandler.IN_ANIMATION, inAnimation);
		bundle2.putInt(MessageHandler.OUT_ANIMATION, outAnimation);
		Message message = new Message();
		message.what = MessageHandler.START_ACTIVITY_FOR_RESULT;
		message.obj = targetActivity;
		message.setData(bundle2);
		sendMessage(message);
	}
	
	@Override
	public final void onStartActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation){
		if(isUseCustomAnimation()){
			if(inAnimation > 0 && outAnimation > 0){
				AndroidUtils.startActivityForResult(this, targetActivity, requestCode, flag, bundle, inAnimation, outAnimation);
			}else{
				int[] animations = onGetDefaultStartActivityAnimation();
				if(animations != null && animations.length >= 2){
					AndroidUtils.startActivityForResult(this, targetActivity, requestCode, flag, bundle, animations[0], animations[1]);
				}else{
					AndroidUtils.startActivityForResult(this, targetActivity, requestCode, flag, bundle);
				}
			}
		}else{
			AndroidUtils.startActivityForResult(this, targetActivity, requestCode, flag, bundle);
		}
	}
	
	@Override
	public final void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle){
		startActivityForResult(targetActivity, requestCode, flag, bundle, -5, -5);
	}
	
	@Override
	public final void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, int inAnimation, int outAnimation){
		startActivityForResult(targetActivity, requestCode, flag, null, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivityForResult(Class<?> targetActivity, int requestCode, Bundle bundle, int inAnimation, int outAnimation){
		startActivityForResult(targetActivity, requestCode, -5, bundle, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivityForResult(Class<?> targetActivity, int requestCode, int flag){
		startActivityForResult(targetActivity, requestCode, flag, null, -5, -5);
	}
	
	@Override
	public final void startActivityForResult(Class<?> targetActivity, int requestCode, Bundle bundle){
		startActivityForResult(targetActivity, requestCode, -5, bundle, -5, -5);
	}
	
	@Override
	public final void startActivityForResult(Class<?> targetActivity, int requestCode, int inAnimation, int outAnimation){
		startActivityForResult(targetActivity, requestCode, -5, null, inAnimation, outAnimation);
	}
	
	@Override
	public final void startActivityForResult(Class<?> targetActivity, int requestCode){
		startActivityForResult(targetActivity, requestCode, -5, null, -5, -5);
	}

	
	
	/* ********************************************** 终止Activity ************************************************ */
	@Override
	public final void finishActivity(){
		sendMessage(MessageHandler.FINISH_ACTIVITY);
	}
	
	@Override
	public final void onFinishActivity(){
		finish();
		if(isUseCustomAnimation()){
			int[] animations = onGetDefaultFinishActivityAnimation();
			if(animations != null && animations.length >= 2){
				overridePendingTransition(animations[0], animations[1]);
			}
		}
	}
	
	@Override
	public final void finishActivity(int inAnimation, int outAnimation){
		Message message = new Message();
		message.what = MessageHandler.FINISH_ACTIVITY_ANIMATION;
		message.arg1 = inAnimation;
		message.arg2 = outAnimation;
		sendMessage(message);
	}
	
	@Override
	public final void onFinishActivity(int inAnimation, int outAnimation){
		finish();
		if(isUseCustomAnimation()){
			overridePendingTransition(inAnimation, outAnimation);
		}
	}
	
	@Override
	public final void finishActivity(long id){
		ActivityManager.getInstance().finishActivity(id);
	}
	
	@Override
	public final void finishActivitys(long[] ids){
		ActivityManager.getInstance().finishActivitys(ids);
	}
	
	@Override
	public final void finishActivitys(List<Long> ids){
		ActivityManager.getInstance().finishActivitys(ids);
	}
	
	@Override
	public final void finishOtherActivitys(){
		ActivityManager.getInstance().finishOtherActivitys(getActivityId());
	}
	
	@Override
	public final void becauseExceptionFinishActivity(){
		final int useTime = (int) (System.currentTimeMillis() - createTime);
		//如果当前Activity从创建到销毁的时间小于最小用时
		if(useTime < MIN_USE_TIME){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(MIN_USE_TIME - useTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sendMessage(MessageHandler.BECAUSE_EXCEPTION_FINISH_ACTIVITY);
				}
			}).start();
		}else{
			sendMessage(MessageHandler.BECAUSE_EXCEPTION_FINISH_ACTIVITY);
		}
	}
	
	@Override
	public void onBecauseExceptionFinishActivity(){
		finishActivity();
	}
	
	@Override
	public final void putToWaitFinishActivitys(){
		ActivityManager.getInstance().putToWaitFinishActivitys(getActivityId());
	}
	
	@Override
	public final boolean removeFromWaitFinishActivitys(){
		return ActivityManager.getInstance().removeFromWaitFinishActivitys(getActivityId());
	}
	
	@Override
	public final void clearWaitFinishActivitys(){
		ActivityManager.getInstance().clearWaitFinishActivitys();
	}
	
	@Override
	public final void finishAllWaitingActivity(){
		ActivityManager.getInstance().finishAllWaitingActivity();
	}
	
	
	
	/* ********************************************** 对话框 ************************************************ */
	@Override
	public final void showMessageDialog(String message, String confrimButtonName){
		Bundle bundle = new Bundle();
		bundle.putString(KEY_DIALOG_MESSAGE, message);
		bundle.putString(KEY_DIALOG_CONFRIM_BUTTON_NAME, confrimButtonName);
		sendMessage(MessageHandler.SHOW_MESSAGE_DIALOG, bundle);
	}
	
	@Override
	public final void showMessageDialog(int messageId, int confrimButtonNameId){
		showMessageDialog(getString(messageId), getString(confrimButtonNameId));
	}
	
	@Override
	public final void closeMessageDialog(){
		sendMessage(MessageHandler.CLOSE_MESSAGE_DIALOG);
	}
	
	@Override
	public final void showProgressDialog(String message){
		Bundle bundle = new Bundle();
		bundle.putString(KEY_DIALOG_MESSAGE, message);
		sendMessage(MessageHandler.SHOW_PROGRESS_DIALOG, bundle);
	}
	
	@Override
	public final void showProgressDialog(int messageId){
		showProgressDialog(getString(messageId));
	}
	
	@Override
	public final void closeProgressDialog(){
		sendMessage(MessageHandler.CLOSE_PROGRESS_DIALOG);
	}
	
	
	
	/* ********************************************** 资源管理 ************************************************ */
	@Override
	public final Animation getAnimation(int resId){
		return AnimationUtils.loadAnimation(getBaseContext(), resId);
	}
	
	@Override
	public final LayoutAnimationController getLayoutAnimation(int ersId){
		return AnimationUtils.loadLayoutAnimation(getBaseContext(), ersId);
	}
	
	@Override
	public final Interpolator getInterpolator(int resId){
		return AnimationUtils.loadInterpolator(getBaseContext(), resId);
	}
	
	@Override
	public final boolean getBoolean(int resId){
		return getResources().getBoolean(resId);
	}
	
	@Override
	public final int getColor(int resId){
		return getResources().getColor(resId);
	}
	
	@Override
	public final ColorStateList getColorStateList(int resId){
		return getResources().getColorStateList(resId);
	}
	
	@Override
	public final float getDimension(int resId){
		return getResources().getDimension(resId);
	}
	
	@Override
	public final float getDimensionPixelOffset(int resId){
		return getResources().getDimensionPixelOffset(resId);
	}
	
	@Override
	public final float getDimensionPixelSize(int resId){
		return getResources().getDimensionPixelSize(resId);
	}
	
	@Override
	public final Drawable getDrawable(int resId){
		return getResources().getDrawable(resId);
	}
	
	@Override
	public final float getFraction(int resId, int base, int pbase){
		return  getResources().getFraction(resId, base, pbase);
	}
	
	@Override
	public final int getIdentifier(String name, String defType, String defPackage){
		return getResources().getIdentifier(name, defType, defPackage);
	}
	
	@Override
	public final int[] getIntArray(int resId){
		return getResources().getIntArray(resId);
	}
	
	@Override
	public final int getInteger(int resId){
		return getResources().getInteger(resId);
	}
	
	@Override
	public final XmlResourceParser getLayout(int resId){
		return getResources().getLayout(resId);
	}
	
	@Override
	public final Movie getMovie(int resId){
		return getResources().getMovie(resId);
	}
	
	@Override
	public final String getQuantityString(int resId, int quantity){
		return getResources().getQuantityString(resId, quantity);
	}
	
	@Override
	public final String getQuantityString(int resId, int quantity, Object... formatArgs){
		return getResources().getQuantityString(resId, quantity, formatArgs);
	}
	
	@Override
	public final CharSequence getQuantityText(int resId, int quantity){
		return getResources().getQuantityText(resId, quantity);
	}
	
	@Override
	public final String[] getStringArray(int resId){
		return getResources().getStringArray(resId);
	}
	
	@Override
	public final String getResourceEntryName(int resId){
		return getResources().getResourceEntryName(resId);
	}
	
	@Override
	public final String getResourceName(int resId){
		return getResources().getResourceName(resId);
	}
	
	@Override
	public final String getResourcePackageName(int resId){
		return getResources().getResourcePackageName(resId);
	}
	
	@Override
	public final String getResourceTypeName(int resId){
		return getResources().getResourceTypeName(resId);
	}
	
	@Override
	public final CharSequence getText(int resId, CharSequence defSequence){
		return getResources().getText(resId, defSequence);
	}
	
	@Override
	public final CharSequence[] getTextArray(int resId){
		return getResources().getTextArray(resId);
	}
	
	@Override
	public final XmlResourceParser getXml(int resId){
		return getResources().getXml(resId);
	}
	
	@Override
	public final View getViewByLayout(int resId, ViewGroup parentView){
		return LayoutInflater.from(getBaseContext()).inflate(resId, parentView);
	}
	
	@Override
	public final View getViewByLayout(int resId){
		return getViewByLayout(resId, null);
	}
	
	@Override
	public final File getDynamicFilesDir(){
		return SDCardUtils.isAvailable() ? getExternalFilesDir(null) : getFilesDir();
	}
	
	@Override
	public final File getDynamicCacheDir(){
		return SDCardUtils.isAvailable() ? getExternalCacheDir() : getCacheDir();
	}
	
	@Override
	public final File getFileFromFilesDir(String fileName){
		return new File(getFilesDir().getPath() + File.separator + fileName);
	}
	
	@Override
	public final File getFileFromExternalFilesDir(String fileName){
		return SDCardUtils.isAvailable() ? new File(getExternalFilesDir(null).getPath() + File.separator + fileName) : null;
	}
	
	@Override
	public final File getFileFromCacheDir(String fileName){
		return new File(getCacheDir().getPath() + File.separator + fileName);
	}
	
	@Override
	public final File getFileFromExternalCacheDir(String fileName){
		return SDCardUtils.isAvailable() ? new File(getExternalCacheDir().getPath() + File.separator + fileName) : null;
	}
	
	@Override
	public final File getFileFromDynamicFilesDir(String fileName){
		return new File(getDynamicFilesDir().getPath() + File.separator + fileName);
	}
	
	@Override
	public final File getFileFromDynamicCacheDir(String fileName){
		return new File(getDynamicCacheDir().getPath() + File.separator + fileName);
	}
	
	
	
	/* ********************************************** GET/SET ************************************************ */
	@Override
	public final long getActivityId(){
		return activityId;
	}

	@Override
	public final void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	@Override
	public final MessageHandler getMessageHanlder() {
		return messageHanlder;
	}

	@Override
	public final void setMessageHanlder(MessageHandler messageHanlder) {
		this.messageHanlder = messageHanlder;
	}

	@Override
	public final View getLoadingHintView() {
		return loadingHintView;
	}

	@Override
	public final void setLoadingHintView(View loadingHintView) {
		this.loadingHintView = loadingHintView;
	}

	@Override
	public final View getListEmptyHintView() {
		return listEmptyHintView;
	}

	@Override
	public final void setListEmptyHintView(View listEmptyHintView) {
		this.listEmptyHintView = listEmptyHintView;
	}

	@Override
	public final boolean isLoadFinished() {
		return loadFinished;
	}

	@Override
	public final void setLoadFinished(boolean loadFinished) {
		this.loadFinished = loadFinished;
	}
	
	@Override
	public final MyBroadcastReceiver getBroadcastReceiver() {
		return broadcastReceiver;
	}

	@Override
	public final void setBroadcastReceiver(MyBroadcastReceiver broadcastReceiver) {
		this.broadcastReceiver = broadcastReceiver;
	}

	@Override
	public final boolean isOpenedBroadcaseReceiver() {
		return openedBroadcaseReceiver;
	}

	@Override
	public final void setOpenedBroadcaseReceiver(boolean openedBroadcaseReceiver) {
		this.openedBroadcaseReceiver = openedBroadcaseReceiver;
	}
}