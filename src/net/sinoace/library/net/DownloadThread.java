package net.sinoace.library.net;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;

import net.sinoace.library.io.IOUtils;
import net.sinoace.library.io.IntegerUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class DownloadThread extends Thread{
	private static final String KEY_THREAD_ID = "KEY_THREAD_ID";
	private static final String KEY_URL = "KEY_URL";
	private static final String KEY_SAVE_FILE = "KEY_SAVE_FILE";
	private static final String KEY_START_LOCATION = "KEY_START_LOCATION";
	private static final String KEY_END_LOCATION = "KEY_END_LOCATION";
	private static final String KEY_DOWNLOAD_LENGTH = "KEY_DOWNLOAD_LENGTH";
	private static final String KEY_FINISHED_LENGTH = "KEY_FINISHED_LENGTH";
	/**
	 * 线程ID
	 */
	private int threadId;
	/**
	 * URL
	 */
	private URL url;
	/**
	 * 保存数据的文件
	 */
	private File saveFile;
	/**
	 * 开始位置
	 */
	private long startLocation;
	/**
	 * 结束位置
	 */
	private long endLocation;
	/**
	 * 需要下载的长度
	 */
	private long downloadLength;
	/**
	 * 已完成的长度
	 */
	private long finishedLength;
	/**
	 * 本次完成的长度
	 */
	private long thisFinishedLength;
	/**
	 * 中断
	 */
	private boolean interrupt;
	/**
	 * 已完成
	 */
	private boolean finished;
	/**
	 * 异常中断
	 */
	private boolean exceptionInterrupt;
	/**
	 * 下载线程监听器
	 */
	private DownloadThreadListener downloadThreadListener;
	/**
	 * 耗时
	 */
	private long timeConsuming;
	/**
	 * 是否是断点续传
	 */
	private boolean breakpointContinuingly;
	/**
	 * 平均速度
	 */
	private int averageSpeed;
	
	/**
	 * 创建下载线程
	 * @param threadId 线程ID
	 * @param url 下载地址
	 * @param saveFile 保存数据的文件
	 * @param startLocation 开始位置
	 * @param endLocation 结束位置
	 */
	public DownloadThread(int threadId, URL url, File saveFile, long startLocation, long endLocation){
		setThreadId(threadId);
		setUrl(url);
		setSaveFile(saveFile);
		setStartLocation(startLocation);
		setEndLocation(endLocation);
		setDownloadLength((getEndLocation() - getStartLocation()) + 1);
	}
	
	/**
	 * 创建下载线程，此构造函数值专门用来断点续产的
	 * @param json 数据JSON，此JSON必须是DownloadThread.toJSON()方法返回的
	 * @throws JSONException
	 * @throws MalformedURLException
	 */
	public DownloadThread(String json) throws JSONException, MalformedURLException{
		JSONObject jsonObject = new JSONObject(json);
		setThreadId(jsonObject.getInt(KEY_THREAD_ID));
		setUrl(new URL(jsonObject.getString(KEY_URL)));
		setSaveFile(new File(jsonObject.getString(KEY_SAVE_FILE)));
		setStartLocation(jsonObject.getLong(KEY_START_LOCATION));
		setEndLocation(jsonObject.getInt(KEY_END_LOCATION));
		setDownloadLength(jsonObject.getLong(KEY_DOWNLOAD_LENGTH));
		setFinishedLength(jsonObject.getLong(KEY_FINISHED_LENGTH));
		setBreakpointContinuingly(true);
	}
	
	@Override
	public void run() {
		//记录开始时间，用于结束时计算时间消耗
		setTimeConsuming(System.currentTimeMillis());
		
		//实例化Http请求对象
		HttpRequest httpRequest = new HttpRequest(url);
		
		//设置范围参数，当是断点续传时就从开始位置加已完成长度处开始获取数据，否则直接从开始位置处开始获取数据
		httpRequest.setProperty(new Range(isBreakpointContinuingly()?getStartLocation()+getFinishedLength():getStartLocation(), getEndLocation()));
		
		//发送请求
		HttpClient.sendRequest(httpRequest, new HttpListener() {
			@Override
			public void onStart() {
				if(getDownloadThreadListener() != null){
					getDownloadThreadListener().onStart(DownloadThread.this);
				}
			}

			@Override
			public void onHandleResponse(HttpResponse httpResponse) throws Exception {
				//如果消息标记识Partial Content，状态码是206，并且内容长度大于0
				if(httpResponse.messageIsPartialContent() && httpResponse.codeIs206() && httpResponse.getContentLength().getLength() > 0){
					Exception exception = null;
					InputStream inputStream = null;
					RandomAccessFile outputStream = null;
					try{
						//打开输入输出流并
						inputStream = httpResponse.getInputStream();
						outputStream = new RandomAccessFile(getSaveFile(), "rwd");
						
						//定位在保存数据的文件中的写入位置，如果是断点续传就从开始位置加已完成长度处开始写，否则直接从开始位置处写
						outputStream.seek(isBreakpointContinuingly()?getStartLocation()+getFinishedLength():getStartLocation());
						
						//实例化用来缓存读取到的数据的字节数组
						byte[] tempData = new byte[IOUtils.MAX_OPERATION_LENGTH];
						
						//实例化每次读取的长度对象
						int thisReadLength = 0;
						
						//循环读取数据，循环的前提条件时不中断并且读取到的数据的长度不为-1
						while(!isInterrupt() && (thisReadLength = inputStream.read(tempData)) != -1){
							//将读取到的数据写入文件中
							outputStream.write(tempData, 0, thisReadLength);
							//记录本次读取到的数据的长度
							setThisFinishedLength(thisReadLength);
							//设置当前线程已完成的长度
							setFinishedLength(getFinishedLength() + thisReadLength);
							//如果下载线程监听器不为null就通知更新进度
							if(getDownloadThreadListener() != null){
								getDownloadThreadListener().onUpdateProgress(DownloadThread.this, getDownloadLength(), getFinishedLength(), IntegerUtils.countPercent(getFinishedLength(), getDownloadLength()));
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						exception = e;
						setExceptionInterrupt(true);
					}finally{
						//关闭输入输出流
						if(inputStream != null){
							inputStream.close();
							inputStream = null;
						}
						if(outputStream != null){
							outputStream.close();
							outputStream = null;
						}
						
						//计算时间消耗和平均速度
						countTimeConsumingAndAverageDownloadSpeed();
						
						//如果已经下载完成，就设置完成标记并通知下载器下载完成
						if(getFinishedLength() >= getDownloadLength()){
							setFinished(true);
							if(getDownloadThreadListener() != null){
								getDownloadThreadListener().onFinished(DownloadThread.this, getTimeConsuming(), getAverageSpeed(), (getAverageSpeed()/1024)+"kb/s");
							}
						}else {
							//如果是人为中断的，就通知下载器人为中断
							if(isInterrupt()){
								if(getDownloadThreadListener() != null){
									getDownloadThreadListener().onInterrupt(DownloadThread.this, getTimeConsuming(), getAverageSpeed(), (getAverageSpeed()/1024)+"kb/s");
								}
							//如果是异常中断的，就通知下载器异常中断
							}else if(isExceptionInterrupt()){
								if(getDownloadThreadListener() != null){
									getDownloadThreadListener().onExceptionInterrupt(DownloadThread.this, exception, getTimeConsuming(), getAverageSpeed(), (getAverageSpeed()/1024)+"kb/s");
								}
							}
						}
					}
				}else{
					//通知下载器下载错误
					if(getDownloadThreadListener() != null){
						getDownloadThreadListener().onError(DownloadThread.this);
					}
				}
			}

			@Override
			public void onException(Exception e) {
				if(getDownloadThreadListener() != null){
					getDownloadThreadListener().onException(DownloadThread.this, e);
				}
			}

			@Override
			public void onEnd() {
				if(getDownloadThreadListener() != null){
					getDownloadThreadListener().onEnd(DownloadThread.this);
				}
			}
		});
	}
	
	/**
	 * 计算时间消耗和平均速度
	 */
	private void countTimeConsumingAndAverageDownloadSpeed(){
		setTimeConsuming(System.currentTimeMillis() - getTimeConsuming());
		setAverageSpeed((int) (getFinishedLength()/(timeConsuming/1000)));
	}
	
	/**
	 * 中断下载
	 */
	public void interruptDownload(){
		setInterrupt(true);
	}

	/**
	 * 获取线程ID
	 * @return 线程ID
	 */
	public int getThreadId() {
		return threadId;
	}

	/**
	 * 设置线程ID
	 * @param threadId 线程ID
	 */
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	/**
	 * 获取URL
	 * @return URL
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * 设置URL
	 * @param url URL
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * 获取保存文件
	 * @return 保存文件
	 */
	public File getSaveFile() {
		return saveFile;
	}

	/**
	 * 设置保存文件
	 * @param saveFile 保存文件
	 */
	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
	}

	/**
	 * 获取开始位置
	 * @return 开始位置
	 */
	public long getStartLocation() {
		return startLocation;
	}

	/**
	 * 设置开始位置
	 * @param startLocation 开始位置
	 */
	public void setStartLocation(long startLocation) {
		this.startLocation = startLocation;
	}

	/**
	 * 获取结束位置
	 * @return 结束位置
	 */
	public long getEndLocation() {
		return endLocation;
	}

	/**
	 * 设置结束位置
	 * @param endLocation 结束位置
	 */
	public void setEndLocation(long endLocation) {
		this.endLocation = endLocation;
	}

	/**
	 * 获取下载长度
	 * @return 下载长度
	 */
	public long getDownloadLength() {
		return downloadLength;
	}

	/**
	 * 设置下载长度
	 * @param downloadLength 下载长度
	 */
	public void setDownloadLength(long downloadLength) {
		this.downloadLength = downloadLength;
	}

	/**
	 * 获取已完成的长度
	 * @return 已完成的长度
	 */
	public long getFinishedLength() {
		return finishedLength;
	}

	/**
	 * 设置已完成的长度
	 * @param finishedLength 已完成的长度
	 */
	private void setFinishedLength(long finishedLength) {
		this.finishedLength = finishedLength;
	}

	/**
	 * 获取本次完成的长度
	 * @return 本次完成的长度
	 */
	public long getThisFinishedLength() {
		return thisFinishedLength;
	}

	/**
	 * 设置本次完成的长度
	 * @param thisFinishedLength 本次完成的长度
	 */
	private void setThisFinishedLength(long thisFinishedLength) {
		this.thisFinishedLength = thisFinishedLength;
	}

	/**
	 * 判断是否已中断
	 * @return 是否已中断
	 */
	public boolean isInterrupt() {
		return interrupt;
	}

	/**
	 * 设置是否已中断
	 * @param interrupt 是否已中断
	 */
	private void setInterrupt(boolean interrupt) {
		this.interrupt = interrupt;
	}

	/**
	 * 判断是否已完成
	 * @return 是否已完成
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * 设置是否已完成
	 * @param finished 是否已完成
	 */
	private void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * 判断是否是异常中断
	 * @return 是否是异常中断
	 */
	public boolean isExceptionInterrupt() {
		return exceptionInterrupt;
	}

	/**
	 * 设置是否是异常中断
	 * @param exceptionInterrupt 是否是异常中断
	 */
	private void setExceptionInterrupt(boolean exceptionInterrupt) {
		this.exceptionInterrupt = exceptionInterrupt;
	}

	/**
	 * 获取下载线程监听器
	 * @return 下载线程监听器
	 */
	public DownloadThreadListener getDownloadThreadListener() {
		return downloadThreadListener;
	}

	/**
	 * 设置下载线程监听器
	 * @param downloadThreadListener 下载线程监听器
	 */
	public void setDownloadThreadListener(DownloadThreadListener downloadThreadListener) {
		this.downloadThreadListener = downloadThreadListener;
	}
	
	/**
	 * 获取消耗的时间
	 * @return 消耗的时间
	 */
	public long getTimeConsuming() {
		return timeConsuming;
	}

	/**
	 * 设置消耗的时间
	 * @param timeConsuming 消耗的时间
	 */
	public void setTimeConsuming(long timeConsuming) {
		this.timeConsuming = timeConsuming;
	}

	/**
	 * 判断是否是断点续传
	 * @return 是否是断点续传
	 */
	public boolean isBreakpointContinuingly() {
		return breakpointContinuingly;
	}

	/**
	 * 设置是否是断点续传
	 * @param breakpointContinuingly 是否是断点续传
	 */
	public void setBreakpointContinuingly(boolean breakpointContinuingly) {
		this.breakpointContinuingly = breakpointContinuingly;
	}
	
	/**
	 * 获取平均速度
	 * @return 平均速度
	 */
	public int getAverageSpeed() {
		return averageSpeed;
	}

	/**
	 * 设置平均速度
	 * @param averageSpeed 平均速度
	 */
	public void setAverageSpeed(int averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	/**
	 * 转换成JSON
	 * @return
	 * @throws JSONException 
	 */
	public String toJSON() throws JSONException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(KEY_THREAD_ID, getThreadId());
		jsonObject.put(KEY_URL, getUrl().toString());
		jsonObject.put(KEY_SAVE_FILE, getSaveFile().getPath());
		jsonObject.put(KEY_START_LOCATION, getStartLocation());
		jsonObject.put(KEY_END_LOCATION, getEndLocation());
		jsonObject.put(KEY_DOWNLOAD_LENGTH, getDownloadLength());
		jsonObject.put(KEY_FINISHED_LENGTH, getFinishedLength());
		return jsonObject.toString();
	}
}