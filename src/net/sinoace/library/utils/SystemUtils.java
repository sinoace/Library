package net.sinoace.library.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;


/**
 * 系统工具箱
 */
public class SystemUtils {
	/**
	 * 获取系统屏幕亮度模式的状态，需要WRITE_SETTINGS权限
	 * @param context 上下文
	 * @return System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC：自动；System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC：手动；默认：System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
	 */
	public static int getScreenBrightnessModeState(Context context){
		return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
	}
	
	/**
	 * 判断系统屏幕亮度模式是否是自动，需要WRITE_SETTINGS权限
	 * @param context 上下文
	 * @return true：自动；false：手动；默认：true
	 */
	public static boolean isScreenBrightnessModeAuto(Context context){
		return getScreenBrightnessModeState(context) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC ? true:false;
	}
	
	/**
	 * 设置系统屏幕亮度模式，需要WRITE_SETTINGS权限
	 * @param context 上下文
	 * @param auto 自动
	 * @return 是否设置成功
	 */
	public static boolean setScreenBrightnessMode(Context context, boolean auto){
		boolean result = true;
		if(isScreenBrightnessModeAuto(context) != auto){
			result = Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, auto?Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC:Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		}
		return result;
	}
	
	/**
	 * 获取系统亮度，需要WRITE_SETTINGS权限
	 * @param context 上下文
	 * @return 亮度，范围是0-255；默认255
	 */
	public static int getScreenBrightness(Context context){
		return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
	}
	
	/**
	 * 设置系统亮度（此方法只是更改了系统的亮度属性，并不能看到效果。要想看到效果可以使用setWindowBrightness()方法设置窗口的亮度），需要WRITE_SETTINGS权限
	 * @param context 上下文
	 * @param screenBrightness 亮度，范围是0-255
	 * @return 设置是否成功
	 */
	public static boolean setScreenBrightness(Context context, int screenBrightness){
		int brightness = screenBrightness;
		if(screenBrightness < 1){
			brightness = 1;
		}else if(screenBrightness > 255){
			brightness = screenBrightness%255;
			if(brightness == 0){
				brightness = 255;
			}
		}
		boolean result = Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
		return result;
	}
	
	/**
	 * 设置给定Activity的窗口的亮度（可以看到效果，但系统的亮度属性不会改变）
	 * @param activity 要通过此Activity来设置窗口的亮度
	 * @param screenBrightness 亮度，范围是0-255
	 */
	public static void setWindowBrightness(Activity activity, float screenBrightness){
		float brightness = screenBrightness;
		if(screenBrightness < 1){
			brightness = 1;
		}else if(screenBrightness > 255){
			brightness = screenBrightness%255;
			if(brightness == 0){
				brightness = 255;
			}
		}
		Window window = activity.getWindow();
		WindowManager.LayoutParams localLayoutParams = window.getAttributes();  
	    localLayoutParams.screenBrightness = (float) brightness/255;  
	    window.setAttributes(localLayoutParams);  
	}
	
	/**
	 * 设置系统亮度并实时可以看到效果，需要WRITE_SETTINGS权限
	 * @param activity 要通过此Activity来设置窗口的亮度
	 * @param screenBrightness 亮度，范围是0-255
	 * @return 设置是否成功
	 */
	public static boolean setScreenBrightnessAndApply(Activity activity, int screenBrightness){
		boolean result = true;
		result = setScreenBrightness(activity, screenBrightness);
		if(result){
			setWindowBrightness(activity, screenBrightness);
		}
		return result;
	}
	
	/**
	 * 获取屏幕休眠时间，需要WRITE_SETTINGS权限
	 * @param context 上下文
	 * @return 屏幕休眠时间，单位毫秒，默认30000
	 */
	public static int getScreenDormantTime(Context context){
		return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
	}
	
	/**
	 * 设置屏幕休眠时间，需要WRITE_SETTINGS权限
	 * @param context 上下文
	 * @return 设置是否成功
	 */
	public static boolean setScreenDormantTime(Context context, int millis){
		return Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, millis);
	}
	
	
	/**
	 * 获取Wifi的状态，需要ACCESS_WIFI_STATE权限
	 * @param context 上下文
	 * @return 取值为WifiManager中的WIFI_STATE_ENABLED、WIFI_STATE_ENABLING、WIFI_STATE_DISABLED、WIFI_STATE_DISABLING、WIFI_STATE_UNKNOWN之一
	 * @throws DeviceNotFoundException 没有找到wifi设备
	 */
	public static int getWifiState(Context context) throws DeviceNotFoundException{
		WifiManager wifiManager = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE));
		if(wifiManager == null){
			throw new DeviceNotFoundException("wifi device not found!");
		}
		return wifiManager.getWifiState();
	}
	
	/**
	 * 判断Wifi是否打开，需要ACCESS_WIFI_STATE权限
	 * @param context 上下文
	 * @return true：打开；false：关闭
	 * @throws DeviceNotFoundException 没有找到wifi设备
	 */
	public static boolean isWifiOpen(Context context) throws DeviceNotFoundException{
		int wifiState = getWifiState(context);
		return wifiState == WifiManager.WIFI_STATE_ENABLED || wifiState == WifiManager.WIFI_STATE_ENABLING ? true : false;
	}
	
	/**
	 * 设置Wifi，需要CHANGE_WIFI_STATE权限
	 * @param context 上下文
	 * @param enable wifi状态
	 * @return 设置是否成功
	 * @throws DeviceNotFoundException 
	 */
	public static boolean setWifi(Context context, boolean enable) throws DeviceNotFoundException{
		boolean result = true;
		//如果当前wifi的状态和要设置的状态不一样
		if(isWifiOpen(context) != enable){
			((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).setWifiEnabled(enable);
		}
		return result;
	}
	
	/**
	 * 判断移动网络是否打开，需要ACCESS_NETWORK_STATE权限
	 * @param context 上下文
	 * @return true：打开；false：关闭
	 */
	public static boolean isMobileNetworkOpen(Context context){
		return (((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).isConnected();
	}
	
	/**
	 * 设置移动网络，需要CHANGE_NETWORK_STATE权限
	 * @param context
	 * @param eanble 状态
	 * @return true：设置成功；false：设置失败
	 */
	public static boolean setMobileNetwork(Context context, boolean eanble){ 
		boolean result = false;
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Method method = ClassUtils.getMethod(mConnectivityManager.getClass(), "setMobileDataEnabled", boolean.class);
		try {
			if(method != null){
				method.invoke(mConnectivityManager, eanble);
				result = true;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取飞行模式的状态，需要WRITE_APN_SETTINGS权限 
	 * @param context 上下文
	 * @return 1：打开；0：关闭；默认：关闭
	 */
	public static int getAirplaneModeState(Context context){
		return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
	}
	
	/**
	 * 判断飞行模式是否打开，需要WRITE_APN_SETTINGS权限 
	 * @param context 上下文
	 * @return true：打开；false：关闭；默认关闭
	 */
	public static boolean isAirplaneModeOpen(Context context){
		return getAirplaneModeState(context) == 1?true:false;
	}
	
	/**
	 * 设置飞行模式的状态，需要WRITE_APN_SETTINGS权限 
	 * @param context 上下文
	 * @param enable 飞行模式的状态
	 * @return 设置是否成功
	 */
	public static boolean setAirplaneMode(Context context, boolean enable){
		boolean result = true;
		//如果飞行模式当前的状态与要设置的状态不一样
		if(isAirplaneModeOpen(context) != enable){
			result = Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enable?1:0);
			//发送飞行模式已经改变广播
			context.sendBroadcast(new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED));
		}
		return result;
	}
	
	/**
	 * 获取蓝牙的状态
	 * @return 取值为BluetoothAdapter的四个静态字段：STATE_OFF, STATE_TURNING_OFF, STATE_ON, STATE_TURNING_ON
	 * @throws DeviceNotFoundException 没有找到蓝牙设备
	 */
	public static int getBluetoothState() throws DeviceNotFoundException{
		 BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		 if(bluetoothAdapter == null){
			 throw new DeviceNotFoundException("bluetooth device not found!");
		 }else{
			 return bluetoothAdapter.getState();
		 }
	}
	
	/**
	 * 判断蓝牙是否打开
	 * @return true：已经打开或者正在打开；false：已经关闭或者正在关闭
	 * @throws DeviceNotFoundException 没有找到蓝牙设备
	 */
	public static boolean isBluetoothOpen() throws DeviceNotFoundException{
		int bluetoothStateCode = getBluetoothState();
		return bluetoothStateCode == BluetoothAdapter.STATE_ON || bluetoothStateCode == BluetoothAdapter.STATE_TURNING_ON ? true : false;
	}
	
	/**
	 * 设置蓝牙状态
	 * @param enable 打开
	 * @throws DeviceNotFoundException 没有找到蓝牙设备
	 */
	public static void setBluetooth(boolean enable) throws DeviceNotFoundException{
		//如果当前蓝牙的状态与要设置的状态不一样
		if(isBluetoothOpen() != enable){
			//如果是要打开就打开，否则关闭
			if(enable){
				BluetoothAdapter.getDefaultAdapter().enable();
			}else{
				BluetoothAdapter.getDefaultAdapter().disable();
			}
		}
	}
	
	/**
	 * 获取媒体音量，需要WRITE_APN_SETTINGS权限 
	 * @param context 上下文
	 * @return 媒体音量，取值范围为0-15；默认0
	 */
	public static int getMediaVolume(Context context){
		return Settings.System.getInt(context.getContentResolver(), Settings.System.VOLUME_MUSIC, 0);
	}
	
	/**
	 * 获取媒体音量，需要WRITE_APN_SETTINGS权限 
	 * @param context 上下文
	 * @return 媒体音量，取值范围为0-15
	 */
	public static boolean setMediaVolume(Context context, int mediaVloume){
		if(mediaVloume < 0){
			mediaVloume = 0;
		}else if(mediaVloume > 15){
			mediaVloume = mediaVloume%15;
			if(mediaVloume == 0){
				mediaVloume = 15;
			}
		}
		return Settings.System.putInt(context.getContentResolver(), Settings.System.VOLUME_MUSIC, mediaVloume);
	}
	
	/**
	 * 获取铃声音量，需要WRITE_APN_SETTINGS权限 
	 * @param context 上下文
	 * @return 铃声音量，取值范围为0-7；默认为0
	 */
	public static int getRingVolume(Context context){
		return Settings.System.getInt(context.getContentResolver(), Settings.System.VOLUME_RING, 0);
	}
	
	/**
	 * 获取媒体音量
	 * @param context 上下文
	 * @return 媒体音量，取值范围为0-7
	 */
	public static boolean setRingVolume(Context context, int ringVloume){
		if(ringVloume < 0){
			ringVloume = 0;
		}else if(ringVloume > 7){
			ringVloume = ringVloume%7;
			if(ringVloume == 0){
				ringVloume = 7;
			}
		}
		return Settings.System.putInt(context.getContentResolver(), Settings.System.VOLUME_MUSIC, ringVloume);
	}
	
	/**
	 * 关机，需要SHUTDOWN权限 
	 * @param context
	 */
	public static void shutDown(Context context){
//		Intent intent = new Intent();
//		intent.setAction("android.intent.action.ACTION_SHUTDOWN");  
//		context.sendBroadcast(intent);
	}
	
	/**
	 * 重启，需要REBOOT权限 
	 * @param context
	 */
	public static void reboot(Context context){
//		Intent intent =  new Intent(Intent.ACTION_REBOOT);
//		intent.putExtra("nowait", 1);
//		intent.putExtra("interval", 1);
//		intent.putExtra("window", 0);
//		context.sendBroadcast(intent);
	}
	
	/**
	 * 获取手机号码
	 * @param context 上下文
	 * @return 手机号码，手机号码不一定能获取到
	 */
	public static String getMobilePhoneNumber(Context context){
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
	}
	
	/**
	 * 判断当前应用是否已经获取ROOT权限。
	 * <br>如果当前设备没有ROOT就直接返回false；
	 * <br>如果当前设备已经ROOT，但是当前应用没有获得ROOT权限，系统就会弹出 申请获取ROOT权限提示；
	 * <br>如果当前设备已经ROOT，当前应用已经获得ROOT权限就直接返回true。
	 * @return true：当前设备已经ROOT，当前应用已经获得ROOT权限；<br> false：当前设备没有ROOT或者当前设备已经ROOT，但是当前应用没有获得ROOT权限。
	 */
	public static boolean isRooted(){
    	boolean result = false;
    	try {
			Process process = Runtime.getRuntime().exec("su -");
			DataOutputStream dos = new DataOutputStream(process.getOutputStream());
			dos.writeBytes("ls /data/\n");
			dos.flush();
			dos.writeBytes("exit\n");
			dos.flush();
			try {
				if(process.waitFor() == 0){
					result = true;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process.destroy();
        } catch (IOException e) {
			e.printStackTrace();
		}
        return result;
    }
	
	/**
	 * 判断当前系统是否是Android4.0
	 * @return 0：是；小于0：小于4.0；大于0：大于4.0
	 */
	public static int isAndroid14(){
		return getAPILevel() - 14;
	}
	
	/**
	 * 获取API级别
	 * @return AndroidAPI级别
	 */
	public static int getAPILevel(){
		return Build.VERSION.SDK_INT;
	}
	
	/**
	 * 找不到设备异常
	 * @author xiaopan
	 *
	 */
	public static class DeviceNotFoundException extends Exception{
		private static final long serialVersionUID = 1L;
		
		public DeviceNotFoundException(){}
		
		public DeviceNotFoundException(String  message){
			super(message);
		}
	}
}