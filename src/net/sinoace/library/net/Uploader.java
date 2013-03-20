package net.sinoace.library.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sinoace.library.io.CheckingUtils;
import net.sinoace.library.io.DateTimeUtils;
import net.sinoace.library.io.IOUtils;

/**
 * <h2>文件上传器</h2>
 */
public class Uploader {
	/** 路径 */
	private String path;
	/** 连接超时时间 */
	private int connTimeout;
	/** 参数集 */
	private Map<String, String> params;
	/** 待上传的文件的集合 */
	private List<UploadFile> fileList;
	/** 监听器 */
	private UploaderListener uploaderListener;
	
	/**
	 * 构造函数，指定路径、连接超时时间、参数集、文件集
	 * @param path 路径
	 * @param connTimeout 连接超时时间，以毫秒为单位，范围是1-60*1000
	 * @param params 参数集
	 * @param fileList 文件集
	 * @throws IllegalArgumentException connTimeout 小于1或大于60000
	 */
	public Uploader(String path, int connTimeout, Map<String, String> params, List<UploadFile> fileList) throws IllegalArgumentException{
		setPath(path);
		setConnTimeout(connTimeout);
		setParams(params);
		setFileList(fileList);
	}
	
	/**
	 * 构造函数，指定路径、连接超时时间
	 * @param path 路径
	 * @param connTimeout 连接超时时间
	 */
	public Uploader(String path, int connTimeout){
		this(path, connTimeout, null, null);
	}
	
	/**
	 * 构造函数，指定路径、连接超时时间默认为NetUtil.NET_CONNECT_TIMEOUT
	 * @param path 路径
	 */
	public Uploader(String path){
		this(path, NetUtils.DEFAULT_CONNECT_TIMEOUT);
	}
	
	
	/**
	 * 以Socket方式上传文件
	 * @return true:成功
	 * @throws IOException URL创建失败
	 * @throws UnknownHostException 指示主机 IP 地址无法确定
	 */
	public boolean uploadBySocket() throws UnknownHostException, IOException{
		/*
		 * 正文部分
		 */
		final String ENTER_AND_TAB = "\r\n";																//回车换行
		final String CUTOFFRULE_SHORT = "---------------------------7da2137580612";							//短分割线
		final String CUTOFFRULE_LONG  = "--" + CUTOFFRULE_SHORT;											//长分割线
		final String END = CUTOFFRULE_LONG + "--" + ENTER_AND_TAB;											//结束标志
		
		long dataTotalLength = 0;																			//数据总长度
		byte[] entryParams = null;																			//参数数据
		List<String> fileParamList = null;																	//文件参数列表
		long fileTotalLength = 0;																			//文件总长度
		long fileTotalFinishLength = 0;																		//文件总共已完成长度
		
		//组织参数信息，并转化为字节
		if(params != null && params.size() > 0){
			StringBuilder sbParams = new StringBuilder();
			for(Map.Entry<String, String> param : params.entrySet()){
				StringBuilder sb = new StringBuilder();
				sb.append(CUTOFFRULE_LONG).append(ENTER_AND_TAB);
				sb.append("Content-Disposition: form-data; name=\"").append(param.getKey()).append("\"").append(ENTER_AND_TAB);
				sb.append(ENTER_AND_TAB);
				sb.append(param.getValue()).append(ENTER_AND_TAB);
				
				sbParams.append(sb.toString());
			}
			entryParams = sbParams.toString().getBytes();
			dataTotalLength += entryParams.length;
		}
		
		
		//组织文件的参数信息并计算总长度
		if(fileList!= null && fileList.size() > 0){
			long fileDataLength = 0;																		//文件数据的总长度	
			fileParamList =  new ArrayList<String>();
			for(UploadFile pff : fileList){
				StringBuilder sb = new StringBuilder();
				sb.append(CUTOFFRULE_LONG).append(ENTER_AND_TAB);
				sb.append("Content-Disposition: form-data; name=\"").append(pff.getParamName()).append("\"; filename=\"").append(pff.getFileName()).append("\"").append(ENTER_AND_TAB);
				sb.append("Content-Type: "+pff.getContentType()).append(ENTER_AND_TAB).append(ENTER_AND_TAB);
				
				String spff = sb.toString();
				fileDataLength += spff.getBytes().length + pff.getFileLength();
				fileTotalLength += pff.getFileLength();
				fileParamList.add(spff);
			}
			dataTotalLength += fileDataLength;
		}
		
		//组织请求头信息
		URL url = new URL(path);															
		int port = url.getPort() == -1 ? 80: url.getPort();													//如果端口号是-1就用80代替
		String hang           = "POST " + url.getPath()+" HHTP/1.1" + ENTER_AND_TAB;						//请求行信息，包括请求方式，请求的资源的路径，以及http协议的版本号
		String host           = "Host: " + url.getHost() + ":" + port + ENTER_AND_TAB;						//请求的资源所在的主机的IP和端口号
		String referer        = "Referer: " + ENTER_AND_TAB;												//请求来源
		String accept         = "Accept: text/html, application/xml;q=0.9, application/xhtml xml, image/png, image/jpeg, image/gif, image/x-xbitmap, */*;q=0.1" + ENTER_AND_TAB;//客户端所接受的数据的类型
		String acceptCharset  = "Accept-Charset: iso-8859-1, utf-8, utf-16, *;q=0.1" + ENTER_AND_TAB;		//客户端所接受的编码类型
		String acceptLanguage = "Accept-Language: zh-cn, en" + ENTER_AND_TAB;								//客户端所接受的语言类型
		String acceptEncoding = "Accept-Encoding: deflate, gzip, x-gzip, identity, *;q=0" + ENTER_AND_TAB;	//客户端所接受的编码类型
		String userAgent      = "User-Agent: Opera/9.80 (Windows NT 6.1; U; IBM EVV/3.0/EAK01AG9/LE; zh-cn) Presto/2.9.168 Version/11.50" + ENTER_AND_TAB;//客户端的浏览器信息
		String date           = "Date: "+DateTimeUtils.getCurrentDateTime() + ENTER_AND_TAB;				//发送时间																										
		String connection     = "Connection: Keep-Alive" + ENTER_AND_TAB;									//连接是否持续
		String contentType    = "Content-Type: multipart/form-data; boundary="+ CUTOFFRULE_SHORT + ENTER_AND_TAB;//内容类型
		String contentLength  = "Content-Length: "+ dataTotalLength + ENTER_AND_TAB;						//数据总长度
		
		StringBuilder sbHead = new StringBuilder();
		sbHead.append(hang).append(host).append(referer).append(accept).append(acceptCharset);
		sbHead.append(acceptLanguage).append(acceptEncoding).append(userAgent).append(date).append(connection);
		sbHead.append(contentType).append(contentLength).append(ENTER_AND_TAB);
		
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);								//建立Socket连接
		socket.setSoTimeout(connTimeout);																	//设置超时时间
		
		OutputStream os = socket.getOutputStream();															//获取输出流
		
		if(uploaderListener != null){
			uploaderListener.onStart();
		}
		
		//输出请求头信息
		os.write(sbHead.toString().getBytes());
		System.out.println(sbHead.toString());
		//输出参数信息
		if(entryParams != null){			
			os.write(entryParams);
			System.out.println(new String(entryParams));
		}
		
		//输出文件信息
		if(fileParamList != null){			
			InputStream input = null;
			int number;																						//每次实际读取的字节长度
			byte[] bytes = new byte[IOUtils.MAX_OPERATION_LENGTH];
			UploadFile file;
			for(int w = 0; w < fileList.size(); w++){
				os.write(fileParamList.get(w).getBytes());													//输出文件参数参数
				System.out.println(fileParamList.get(w));
				file = fileList.get(w);																		//获取当前要上传的文件
				input = file.getFileInput();																//获取输入流
				//输出文件内容
				while((number = input.read(bytes)) != -1){													//从输入流中读出数据并判断是否到达末尾
					os.write(bytes, 0, number);
					os.flush();																				//刷新输出流
					fileTotalFinishLength+=number;
					file.setFinishLength(file.getFinishLength()+number);
					if(uploaderListener != null){
						uploaderListener.onUploading(file, fileTotalLength, fileTotalFinishLength);
					}
				}
				input.close();
				//输出换行符
				os.write(ENTER_AND_TAB.getBytes());
				input = null;
				file = null;
			}
			bytes = null;
		}
		
		//输出结束标志
		os.write(END.getBytes());
		
		if(uploaderListener != null){
			uploaderListener.onStop();
		}
		
		//判断是否上传成功，并关闭输出流以及Socket连接
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));			//获取输入流       
//		System.out.println(new String(IOUtils.read(reader)));
		String firstLine = reader.readLine();																//读取web服务器返回的数据的第一行		
		os.flush();
		os.close();																			       			//关闭输出流
        reader.close();																						//关闭输入流
        socket.close();																						//关闭Socket连接
        if((firstLine == null) || (firstLine.indexOf("200") == -1)){										//如果不包含"200"
        	return false;																					//返回请求失败
        }else{
        	return true;    																				//返回请求成功
        }
	}

	/**
	 * 清除路径、参数集、文件集
	 */
	public void clear(){
		path = null;
		params = null;
		fileList = null;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		CheckingUtils.valiObjectIsNull(path, "path");
		this.path = path;
	}

	public int getConnTimeout() {
		return connTimeout;
	}

	/**
	 * 设置超时时间 
	 * @param connTimeout 超时时间
	 * @throws IllegalArgumentException threadNumber 小于1或大于60000
	 */
	public void setConnTimeout(int connTimeout) throws IllegalArgumentException {
		if(!(connTimeout >= 1 && connTimeout <= 60000)){
			throw new IllegalArgumentException("can shu connTimeout xiao yu 1 huo da yu 60000");
		}
		this.connTimeout = connTimeout;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		CheckingUtils.valiObjectIsNull(params, "params");
		this.params = params;
	}

	public List<UploadFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<UploadFile> fileList) {
		CheckingUtils.valiObjectIsNull(fileList, "fileList");
		this.fileList = fileList;
	}

	public UploaderListener getUploaderListener() {
		return uploaderListener;
	}

	public void setUploaderListener(UploaderListener uploaderListener) {
		CheckingUtils.valiObjectIsNull(uploaderListener, "uploaderListener");
		this.uploaderListener = uploaderListener;
	}

	/**
	 * 文件长传器监听器
	 * @author feifei
	 */
	public interface UploaderListener{
		/**
		 * 当开始
		 */
		public void onStart();
		/**
		 * 当正在上传
		 * @param file当前上传的文件
		 * @param fileTotalLength 全部需要上传的文件的总长度
		 * @param fileTotalFinishLength 全已完成的长度
		 */
		public void onUploading(UploadFile file, long fileTotalLength, long fileTotalFinishLength);
		/**
		 * 当结束
		 */
		public void onStop();
	}
}
