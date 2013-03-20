package net.sinoace.library.net;



/**
 * 下载监听器
 */
public abstract class DownloadListener {
	
	/**
	 * 当开始
	 */
	public abstract void onStart();
	
	/**
	 * 当更新下载进度
	 * @param downloadThread 本次更新的线程
	 * @param fileLength 文件长度
	 * @param finishedLength 已完成长度 
	 * @param percentage 已完成百分比
	 */
	public abstract void onUpdateProgresss(DownloadThread downloadThread, long fileLength, long finishedLength, String percentage);
	
	/**
	 * 当更新下载速度
	 * @param downloadSpeed 下载速度 字节/秒 
	 */
	public abstract void onUpdateSpeed(int downloadSpeed);
	
	/**
	 * 当下载已完成
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public abstract void onFinished(long timeConsuming, int averageSpeed, String averageSpeedByKB);
	
	/**
	 * 当下载中断
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public abstract void onInterrupt(long timeConsuming, int averageSpeed, String averageSpeedByKB);
	
	/**
	 * 当下载异常中断
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public abstract void onExceptionInterrupt(long timeConsuming, int averageSpeed, String averageSpeedByKB);
	
	/**
	 * 当下载出现异常
	 * @param e 异常
	 */
	public void onException(Exception e){}
	
	/**
	 * 当下载错误，错误的可能有消息标记不是OK、状态码不是200、或者内容长度小于等于0
	 */
	public void onError(){}
	
	
	/**
	 * 当下载线程开始
	 * @param downloadThread 下载线程
	 */
	public void onDownloadThreadStart(DownloadThread downloadThread){}
	
	/**
	 * 当下载线程更新进度
	 * @param downloadThread
	 * @param downloadLength 需要下载的长度
	 * @param finishedLength 已完成长度 
	 * @param percentage 已完成百分比
	 */
	public void onDownloadThreadUpdateProgresss(DownloadThread downloadThread, long downloadLength, long finishedLength, String percentage){}
	
	/**
	 * 当下载线程完成
	 * @param downloadThread 下载线程
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public void onDownloadThreadFinished(DownloadThread downloadThread, long timeConsuming, int averageSpeed, String averageSpeedByKB){}
	
	/**
	 * 当下载线程人为中断
	 * @param downloadThread 下载线程
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public void onDownloadThreadInterrupt(DownloadThread downloadThread, long timeConsuming, int averageSpeed, String averageSpeedByKB){}
	
	/**
	 * 当下载线程异常中断，读取数据的过程中中断了
	 * @param downloadThread 下载线程
	 * @param e 异常
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public void onDownloadThreadExceptionInterrupt(DownloadThread downloadThread, Exception e, long timeConsuming, int averageSpeed, String averageSpeedByKB){}

	/**
	 * 当下载线程出现异常
	 * @param downloadThread 下载线程
	 * @param e 异常
	 */
	public void onDownloadThreadException(DownloadThread downloadThread, Exception e){}
	
	/**
	 * 当下载线程出现错误，错误的可能有消息标记不是OK、状态码不是200、内容类型不是下载、或者内容长度小于等于0
	 * @param downloadThread 下载线程
	 */
	public void onDownloadThreadError(DownloadThread downloadThread){}
	
	/**
	 * 当下载线程结束
	 * @param downloadThread 下载线程
	 */
	public void onDownloadThreadEnd(DownloadThread downloadThread){}
}