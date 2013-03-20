package net.sinoace.library.io;

import java.io.File;

import android.os.Environment;

/**
 * SD卡相关的工具类
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(01)、判断SD卡是否存在：static boolean sdCardIsExist()
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(02)、获取SD卡的根目录：static File getSDCardRootDirectory()
 * 
 * 
 *
 */

public class SDCardUtils {
	
	/**
	 * (01)、判断SD卡是否存在
	 * @return boolean true：存在
	 */
	public static boolean existSDCard(){
		/*
		 * Environment.getExternalStorageState()：获取外部设备也就是SD卡的状态
		 * Environment.MEDIA_MOUNTED：已经安装了SD卡并且具有读写权限
		 * 两者比较
		 */
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * (02)、获取SD卡的根目录，如果当前设备上 没有安装SD卡，那么返回null
	 * @return 如果当前设备上 没有安装SD卡，那么返回null
	 */
	public static File getSDCardRootDirectory(){
		if(existSDCard()){												//如果SD卡存在
			return Environment.getExternalStorageDirectory();
		}else{																//如果SD卡不存在
			return null; 
		}
	}
}
