package net.sinoace.library.utils;

/**
 * 加载方式
 */
public enum LoadWay {
	/**
	 * 从网络加载
	 */
	FROM_NET, 
	/**
	 * 从本地加载
	 */
	FROM_LOCAL, 
	/**
	 * 优先从本地读取（如果从本地读取失败，再从网络上下载）
	 */
	FROM_LOCAL_BY_PRIORITY
}