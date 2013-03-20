package net.sinoace.library.net;


/**
 * 下载线程监听器
 */
public abstract class DownloadThreadListener {
	/**
	 * 当开始
	 * @param downloadThread 下载线程
	 */
	public abstract void onStart(DownloadThread downloadThread);
	
	/**
	 * 当更新下载进度
	 * @param downloadThread 下载线程
	 * @param downloadLength 需要下载的长度
	 * @param finishedLength 已完成的长度
	 * @param percentage 完成百分比
	 */
	public abstract void onUpdateProgress(DownloadThread downloadThread, long downloadLength, long finishedLength, String percentage);;
	
	/**
	 * 当下载完成
	 * @param downloadThread 下载线程
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public abstract void onFinished(DownloadThread downloadThread, long timeConsuming, int averageSpeed, String averageSpeedByKB);
	
	/**
	 * 当下载人为中断
	 * @param downloadThread 下载线程
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public abstract void onInterrupt(DownloadThread downloadThread, long timeConsuming, int averageSpeed, String averageSpeedByKB);
	
	/**
	 * 当下载异常中断
	 * @param downloadThread 下载线程
	 * @param e 异常
	 * @param timeConsuming 耗时，单位毫秒
	 * @param averageSpeed 平均下载速度，字节/秒
	 * @param averageSpeedByKB 每秒多少KB
	 */
	public abstract void onExceptionInterrupt(DownloadThread downloadThread, Exception e, long timeConsuming, int averageSpeed, String averageSpeedByKB);
	
	/**
	 * 当出现异常
	 * @param downloadThread 下载线程
	 * @param e
	 */
	public abstract void onException(DownloadThread downloadThread, Exception e);
	
	/**
	 * 当出现错误，错误的可能有消息标记不是PartialContent、状态码不是206、或者内容长度小于等于0
	 * @param downloadThread 下载线程
	 */
	public abstract void onError(DownloadThread downloadThread);
	
	/**
	 * 在下载之后
	 * @param downloadThread 下载线程
	 */
	public abstract void onEnd(DownloadThread downloadThread);
}