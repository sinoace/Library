package net.sinoace.library.net;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sinoace.library.io.FileUtils;
import net.sinoace.library.io.IntegerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 下载器
 */
public class Downloader{
	private static final String KEY_URL = "KEY_URL";
	private static final String KEY_SAVE_FILE = "KEY_SAVE_FILE";
	private static final String KEY_THREAD_NUMBER = "KEY_THREAD_NUMBER";
	private static final String KEY_FILE_LENGTH = "KEY_FILE_LENGTH";
	private static final String KEY_FINISHED_LENGTH = "KEY_FINISHED_LENGTH";
	private static final String KEY_DOWNLOAD_THREADS = "KEY_DOWNLOAD_THREADS";
	/**
	 * URL
	 */
	private URL url;
	/**
	 * 保存文件
	 */
	private File saveFile;
	/**
	 * 线程数
	 */
	private int threadNumber = 1;
	/**
	 * 文件长度
	 */
	private long fileLength;
	/**
	 * 已完成长度
	 */
	private long finishedLength;
	/**
	 * 下载监听器
	 */
	private DownloadListener downloadListener;
	/**
	 * 下载线程监听器
	 */
	private DownloadThreadListener downloadThreadListener;
	/**
	 * 下载线程Map
	 */
	private Map<Integer, DownloadThread> downloadThreadMap;
	/**
	 * 是否是断点续传
	 */
	private boolean breakpointContinuingly;
	/**
	 * 耗时
	 */
	private long timeConsuming;
	/**
	 * 上次更新进度的时间
	 */
	private long lastUpdateProgressTime;
	/**
	 * 上一次完成的长度
	 */
	private long lastFinishedLength;
	/**
	 * 是否创建了保存文件
	 */
	private boolean createSaveFile;
	/**
	 * 平均速度
	 */
	private int averageSpeed;
	
	/**
	 * 创建一个下载器
	 * @param url 下载地址
	 * @param saveFile 保存文件
	 * @throws IOException
	 */
	public Downloader(URL url, File saveFile) throws IOException{
		setUrl(url);
		setSaveFile(saveFile);
		setDownloadThreadLinstener();
	}
	
	/**
	 * 创建下载器
	 * @param url 下载地址
	 * @param saveFilePath 保存文件的路径
	 * @throws IOException
	 */
	public Downloader(String url, String saveFilePath) throws IOException{
		this(new URL(url), new File(saveFilePath));
	}
	
	/**
	 * 创建下载器
	 * @param json 数据JSON，此JSON必须是Downloader.toJSON()方法返回的
	 * @throws JSONException
	 * @throws IOException
	 */
	public Downloader(String json) throws JSONException, IOException{
		JSONObject jsonObject = new JSONObject(json);
		setUrl(new URL(jsonObject.getString(KEY_URL)));
		setSaveFile(new File(jsonObject.getString(KEY_SAVE_FILE)));
		setThreadNumber(jsonObject.getInt(KEY_THREAD_NUMBER));
		setFileLength(jsonObject.getLong(KEY_FILE_LENGTH));
		setFinishedLength(jsonObject.getLong(KEY_FINISHED_LENGTH));
		
		JSONArray jsonArray = jsonObject.getJSONArray(KEY_DOWNLOAD_THREADS);
		setDownloadThreadLinstener();
		setDownloadThreadMap(new HashMap<Integer, DownloadThread>(jsonArray.length()));
		for(int w = 0, length = jsonArray.length(); w < length; w++){
			DownloadThread downloadThread = new DownloadThread(jsonArray.getString(w));
			downloadThread.setDownloadThreadListener(getDownloadThreadListener());
			getDownloadThreadMap().put(downloadThread.getThreadId(), downloadThread);
		}
		setBreakpointContinuingly(true);
	}
	
	/**
	 * 开始下载
	 */
	public void start(){
		//记录开始时间，以及上次更新进度的时间
		setTimeConsuming(System.currentTimeMillis());
		setLastUpdateProgressTime(getTimeConsuming());
		
		//如果不是断点续传
		if(!isBreakpointContinuingly()){
			//首先，获取文件大小
			HttpRequest httpRequest = new HttpRequest(url);
			HttpClient.sendRequest(httpRequest, new HttpListener() {
				@Override
				public void onStart() {
					if(getDownloadListener() != null){
						getDownloadListener().onStart();
					}
				}
				
				@Override
				public void onHandleResponse(HttpResponse httpResponse) throws Exception {
					//如果消息标记识OK，状态码是200，并且内容长度大于0
					if(httpResponse.messageIsOk() && httpResponse.codeIs200() && httpResponse.getContentLength().getLength() > 0){
						//设置文件大小
						setFileLength(httpResponse.getContentLength().getLength());
						
						//根据文件长度以及线程个数计算每个线程应该负责的范围
						long[][] threadBlock = NetUtils.countThread(getFileLength(), getThreadNumber());
						
						//创建线程
						setDownloadThreadMap(new HashMap<Integer, DownloadThread>(threadBlock.length));
						for(int w = 0, length = threadBlock.length; w < length; w++){
							DownloadThread downloadThread = new DownloadThread(w, getUrl(), getSaveFile(), threadBlock[w][0], threadBlock[w][1]);
							downloadThread.setDownloadThreadListener(getDownloadThreadListener());
							getDownloadThreadMap().put(w, downloadThread);
						}
						
						//启动线程
						startThread();
					}else{
						if(getDownloadListener() != null){
							getDownloadListener().onError();
						}
					}
				}

				@Override
				public void onException(Exception e) {
					tryDeleteSaveFile();
					if(getDownloadListener() != null){
						getDownloadListener().onException(e);
					}
				}
				
				@Override
				public void onEnd() {
				}
			});
		}else{
			//启动线程
			startThread();
		}
	}
	
	/**
	 * 重启下载线程
	 * @param downloadThread
	 * @throws MalformedURLException
	 * @throws JSONException
	 */
	public void restartDownloadThread(DownloadThread downloadThread) throws MalformedURLException, JSONException{
		DownloadThread downloadThread2 = new DownloadThread(downloadThread.toJSON());
		downloadThread2.start();
		getDownloadThreadMap().put(downloadThread.getThreadId(), downloadThread2);
	}

	/**
	 * 转换成JSON
	 * @return
	 * @throws JSONException 
	 */
	public String toJSON() throws JSONException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(KEY_URL, getUrl().toString());
		jsonObject.put(KEY_SAVE_FILE, getSaveFile().getPath());
		jsonObject.put(KEY_THREAD_NUMBER, getThreadNumber());
		jsonObject.put(KEY_FILE_LENGTH, getFileLength());
		jsonObject.put(KEY_FINISHED_LENGTH, getFinishedLength());
		JSONArray jsonArray = new JSONArray();
		for(Entry<Integer, DownloadThread> downloadThreadEntry : getDownloadThreadMap().entrySet()){
			jsonArray.put(downloadThreadEntry.getValue().toJSON());
		}
		jsonObject.put(KEY_DOWNLOAD_THREADS, jsonArray.toString());
		return jsonObject.toString();
	}
	
	/**
	 * 启动线程
	 */
	private void startThread(){
		for(Entry<Integer, DownloadThread> downloadThreadEntry : getDownloadThreadMap().entrySet()){
			downloadThreadEntry.getValue().start();
		}
	}
	
	/**
	 * 中断下载
	 */
	public void interruptDownload(){
		for(Entry<Integer, DownloadThread> downloadThreadEntry : getDownloadThreadMap().entrySet()){
			downloadThreadEntry.getValue().interruptDownload();
		}
	}
	
	/**
	 * 尝试删除保存文件
	 */
	private void tryDeleteSaveFile(){
		if(isCreateSaveFile() && getSaveFile() != null && getSaveFile().exists()){
			getSaveFile().delete();
		}
	}
	
	/**
	 * 设置下载下城监听器
	 */
	private void setDownloadThreadLinstener(){
		setDownloadThreadListener(new DownloadThreadListener() {
			@Override
			public synchronized void onStart(DownloadThread downloadThread) {
				if(getDownloadListener() != null){
					getDownloadListener().onDownloadThreadStart(downloadThread);
				}
			}
			
			@Override
			public synchronized void onUpdateProgress(DownloadThread downloadThread, long downloadLength, long finishedLength, String percentage) {
				setFinishedLength(getFinishedLength() + downloadThread.getThisFinishedLength());
				//如果上一次更新的时间距现在已超越1秒了
				long currentTimeMillis = System.currentTimeMillis();
				if(currentTimeMillis - getLastUpdateProgressTime() > 999){
					setLastUpdateProgressTime(currentTimeMillis);
					int speed = (int) (getFinishedLength() - getLastFinishedLength());
					setLastFinishedLength(getFinishedLength());
					getDownloadListener().onUpdateSpeed(speed);
				}
				if(getDownloadListener() != null){
					getDownloadListener().onDownloadThreadUpdateProgresss(downloadThread, downloadLength, finishedLength, percentage);
					getDownloadListener().onUpdateProgresss(downloadThread, getFileLength(), getFinishedLength(), IntegerUtils.countPercent(getFinishedLength(), getFileLength()));
				}
			}
			
			@Override
			public synchronized void onFinished(DownloadThread downloadThread, long timeConsuming, int averageSpeed, String averageSpeedByKB) {
				if(getDownloadListener() != null){
					getDownloadListener().onDownloadThreadFinished(downloadThread, timeConsuming, averageSpeed, averageSpeedByKB);
				}
				
				//检查所有的线程是否已经全部下载完成
				boolean allFinished = true;
				for(Entry<Integer, DownloadThread> downloadThreadEntry : getDownloadThreadMap().entrySet()){
					if(!downloadThreadEntry.getValue().isFinished()){
						allFinished = false;
						break;
					}
				}
				
				//如果已经全部下载完成了
				if(allFinished){
					if(getDownloadListener() != null){
						countTimeConsumingAndAverageDownloadSpeed();
						getDownloadListener().onFinished(getTimeConsuming(), getAverageSpeed(), (getAverageSpeed()/1024)+"kb/s");
					}
				}
			}
			
			@Override
			public synchronized void onInterrupt(DownloadThread downloadThread, long timeConsuming, int averageSpeed, String averageSpeedByKB) {
				if(getDownloadListener() != null){
					getDownloadListener().onDownloadThreadInterrupt(downloadThread, timeConsuming, averageSpeed, averageSpeedByKB);
				}
				
				//检查所有的线程是否已经全部中断
				boolean allInterrupt = true;
				for(Entry<Integer, DownloadThread> downloadThreadEntry : getDownloadThreadMap().entrySet()){
					if(!downloadThreadEntry.getValue().isInterrupt()){
						allInterrupt = false;
						break;
					}
				}
				
				//如果已经全部中断了
				if(allInterrupt){
					if(getDownloadListener() != null){
						countTimeConsumingAndAverageDownloadSpeed();
						getDownloadListener().onInterrupt(getTimeConsuming(), getAverageSpeed(), (getAverageSpeed()/1024)+"kb/s");
					}
				}
			}
			
			@Override
			public synchronized void onExceptionInterrupt(DownloadThread downloadThread, Exception e, long timeConsuming, int averageSpeed, String averageSpeedByKB) {
				if(getDownloadListener() != null){
					getDownloadListener().onDownloadThreadExceptionInterrupt(downloadThread, e, timeConsuming, averageSpeed, averageSpeedByKB);
				}
				
				//检查所有的线程是否已经全部异常中断
				boolean allExceptionInterrupt = true;
				for(Entry<Integer, DownloadThread> downloadThreadEntry : getDownloadThreadMap().entrySet()){
					if(!downloadThreadEntry.getValue().isExceptionInterrupt()){
							allExceptionInterrupt = false;
						break;
					}
				}
				
				//如果已经全部异常中断了
				if(allExceptionInterrupt){
					if(getDownloadListener() != null){
						countTimeConsumingAndAverageDownloadSpeed();
						getDownloadListener().onExceptionInterrupt(getTimeConsuming(), getAverageSpeed(), (getAverageSpeed()/1024)+"kb/s");
					}
				}
			}

			@Override
			public synchronized void onException(DownloadThread downloadThread, Exception e) {
				if(getDownloadListener() != null){
					getDownloadListener().onDownloadThreadException(downloadThread, e);
				}
			}
			
			@Override
			public synchronized void onError(DownloadThread downloadThread) {
				if(getDownloadListener() != null){
					getDownloadListener().onDownloadThreadError(downloadThread);
				}
			}
			
			@Override
			public synchronized void onEnd(DownloadThread downloadThread) {
				if(getDownloadListener() != null){
					getDownloadListener().onDownloadThreadEnd(downloadThread);
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
	 * 开启多线程，线程数量默认为5
	 */
	public void openMultipleThread(){
		setThreadNumber(5);
	}
	
	/**
	 * 开启多线程
	 * @param threadNumber 线程数量
	 */
	public void openMultipleThread(int threadNumber){
		setThreadNumber(threadNumber);
	}
	
	/**
	 * 关闭多线程，关闭后线程数默认是1
	 */
	public void closeMultipleThread(){
		setThreadNumber(1);
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
		if(url == null){
			throw new NullPointerException("url is null");
		}
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
	 * @throws IOException 
	 */
	public void setSaveFile(File saveFile) throws IOException {
		if(saveFile == null){
			throw new NullPointerException("saveFile is null");
		}
		
		//如果文件不存在
		if(!saveFile.exists()){
			//获取其父目录
			File parentDir = saveFile.getParentFile();
			//如果其父目录不存在，就创建目录
			if(!parentDir.exists()){
				parentDir.mkdirs();
			}
			//创建文件
			if(saveFile.createNewFile()){
				createSaveFile = true;
			}else{
				throw new IOException("saveFile create fail");
			}
		}
		
		this.saveFile = saveFile;
	}

	/**
	 * 获取线程数
	 * @return 线程数
	 */
	public int getThreadNumber() {
		return threadNumber;
	}

	/**
	 * 设置线程数
	 * @param threadNumber 线程数
	 */
	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	/**
	 * 获取文件长度
	 * @return 文件长度
	 */
	public long getFileLength() {
		return fileLength;
	}

	/**
	 * 设置文件长度
	 * @param fileLength 文件长度
	 * @throws IOException 
	 */
	private void setFileLength(long fileLength) throws IOException {
		this.fileLength = fileLength;
		FileUtils.setFileLength(getSaveFile(), fileLength);
	}

	/**
	 * 获取已完成长度
	 * @return 已完成长度
	 */
	public long getFinishedLength() {
		return finishedLength;
	}

	/**
	 * 设置已完成长度
	 * @param finishedLength 已完成长度
	 */
	private void setFinishedLength(long finishedLength) {
		this.finishedLength = finishedLength;
	}

	/**
	 * 获取下载监听器
	 * @return 下载监听器
	 */
	public DownloadListener getDownloadListener() {
		return downloadListener;
	}

	/**
	 * 设置下载监听器
	 * @param downloadListener 下载监听器
	 */
	public void setDownloadListener(DownloadListener downloadListener) {
		this.downloadListener = downloadListener;
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
	private void setDownloadThreadListener(DownloadThreadListener downloadThreadListener) {
		this.downloadThreadListener = downloadThreadListener;
	}

	/**
	 * 获取下载线程Map
	 * @return 下载线程Map
	 */
	public Map<Integer, DownloadThread> getDownloadThreadMap() {
		return downloadThreadMap;
	}

	/**
	 * 设置下载线程Map
	 * @param downloadThreadMap 下载线程Map
	 */
	private void setDownloadThreadMap(Map<Integer, DownloadThread> downloadThreadMap) {
		this.downloadThreadMap = downloadThreadMap;
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
	 * 获取时间消耗
	 * @return 时间消耗
	 */
	public long getTimeConsuming() {
		return timeConsuming;
	}

	/**
	 * 设置时间消耗
	 * @param timeConsuming 时间消耗
	 */
	public void setTimeConsuming(long timeConsuming) {
		this.timeConsuming = timeConsuming;
	}

	/**
	 * 获取上次更新进度的时间
	 * @return 上次更新进度的时间
	 */
	public long getLastUpdateProgressTime() {
		return lastUpdateProgressTime;
	}

	/**
	 * 设置上次更新进度的时间
	 * @param lastUpdateProgressTime 上次更新进度的时间
	 */
	public void setLastUpdateProgressTime(long lastUpdateProgressTime) {
		this.lastUpdateProgressTime = lastUpdateProgressTime;
	}

	/**
	 * 获取上次已完成的长度
	 * @return 上次已完成的长度
	 */
	public long getLastFinishedLength() {
		return lastFinishedLength;
	}

	/**
	 * 设置上次已完成的长度
	 * @param lastFinishedLength 上次已完成的长度
	 */
	public void setLastFinishedLength(long lastFinishedLength) {
		this.lastFinishedLength = lastFinishedLength;
	}

	/**
	 * 判断是否创建的保存数据的文件
	 * @return 是否创建的保存数据的文件
	 */
	public boolean isCreateSaveFile() {
		return createSaveFile;
	}

	/**
	 * 设置是否创建的保存数据的文件
	 * @param createSaveFile 是否创建的保存数据的文件
	 */
	public void setCreateSaveFile(boolean createSaveFile) {
		this.createSaveFile = createSaveFile;
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
}