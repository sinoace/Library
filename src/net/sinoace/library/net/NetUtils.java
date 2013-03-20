package net.sinoace.library.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

import net.sinoace.library.io.CheckingUtils;
import net.sinoace.library.io.IOUtils;

/**
 * <h2>网络方面相关的工具类，提供网络有关的便捷方法</h2>
 * 
 * <br><b>1、Http方面</b>
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.01)、通过一个URL对象获取一个网络连接对象：static URLConnection getURLConnFromURL(URL url)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.02)、从一个URL里获取一个网络连接的字节输入流：static InputStream getInputFromURL(URL url)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.03)、从一个URL里获取一个网络连接的字节输出流：static OutputStream getOutputFromURL(URL url)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.04)、获取一个手机号码的归属地：static String getMobileAddress(String mobileNumber)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.05)、获取一个网络文件的大小：static int getFileSizeFromURL(URL url, int connTimeout)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.06)、(1.06)、通过解析URL地址获取一个文件名：static String getFileNameFromParseURL(URL url)
 * <br>
 * <br><b>2、Socket方面</b>
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.01)、判断指定的数字是否是合法的端口号，取值范围为1000——65532：static boolean isHefaPort(int port)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.02)、判断指定的字符串是否是合法的端口号，取值范围为1000——65532：static boolean isHefaPort(String port)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.03)、从指定的端口开始，获取一个可用的端口号：static int getPort(int port)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.04)、判断指定的数字是否是一个可用的端口号：static boolean isKeYongPort(int port)
 * <br>
 * <br><b>3、其它</b>
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(3.01)、根据指定的文件的大小，以及线程数，计算每个线程所对应的开始与结束位置：static int[][] countThread(int fileSize, int threadNumber)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(3.02)、从一个url中获取字节数据：static byte[] getDataFromUrl(URL url, int connTimeout, int length)
 * 
 * 
 */

public class NetUtils {
	/**
	 * 网络连接超时时间，默认5秒
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5000;
	/**
	 * 读取数据超时时间，默认5秒
	 */
	public static final int DEFAULT_READ_TIMEOUT = 5000;

	/* *****************************************************1、Http方面start******************************************************************* */
	/**
	 * (1.01).通过一个URL对象获取一个网络连接对象
	 * @param url URL
	 * @param connTimeout 连接超时时间
	 * @return URLConnection URL连接
	 * @throws IOException 当参数url为null时，或当发生某种 I/O 异常时，抛出此异常
	 */
	public static URLConnection getURLConnFromURL(URL url, int connTimeout) throws IOException{
		URLConnection urlc = url.openConnection();
		urlc.setConnectTimeout(connTimeout);
		return urlc;
	}
	

	/**
	 * (1.02).从一个URL中获取一个网络连接的字节输入流
	 * @param url URL
	 * @param connTimeout 连接超时时间
	 * @return 字节输入流
	 * @throws IOException 当参数url为null，或当发生某种 I/O 异常时，抛出此异常
	 */
	public static InputStream getInputFromURL(URL url, int connTimeout) throws IOException{
		HttpURLConnection hurlc = (HttpURLConnection)getURLConnFromURL(url, connTimeout);
		hurlc.connect();
		return hurlc.getInputStream();
	}
	
	
	/**
	 * (1.03).从一个URL中获取一个网络连接的字节输出流
	 * @param url URL
	 * @param connTimeout 连接超时时间
	 * @return 字节输出流
	 * @throws IOException 当参数url为null，或当发生某种 I/O 异常时，抛出此异常
	 */
	public static OutputStream getOutputFromURL(URL url, int connTimeout) throws IOException{
		HttpURLConnection hurlc = (HttpURLConnection)getURLConnFromURL(url, connTimeout);
		hurlc.connect();
		return hurlc.getOutputStream();
	}
	
	
	/**
	 * (1.04).获取一个手机号码的归属地
	 * @param mobileNumber 书记号码
	 * @return
	 */
	public static String getMobileAddress(String mobileNumber){
		return null;
	}
	
	
	/**
	 * (1.05).获取一个网络文件的大小
	 * @param url 网络文件的URL地址
	 * @return 大小，以字节为单位
	 * @throws IOException 当发生某种 I/O 异常时，抛出此异常
	 */
	public static int getFileLengthFromURL(URL url, int connTimeout) throws IOException{
		HttpURLConnection hurlc = (HttpURLConnection)getURLConnFromURL(url, connTimeout);
		hurlc.connect();
		return hurlc.getContentLength();
	}
	
	/**
	 * (1.06)、通过解析URL地址获取一个文件名
	 * @param url 网络文件的URL地址
	 * @return 文件的名字
	 */
	public static String getFileNameFromURL(URL url){
		return url.toString().substring(url.toString().lastIndexOf('/')+1);
	}
	
	/* *****************************************************1、Http方面over******************************************************************* */

	
	
	
	/* *****************************************************2、socket方面start******************************************************************* */
	/**
	 * (2.01)、判断指定的数字是否是合法的端口号，取值范围为1000——65532
	 * @param port 指定的数字
	 * @return true：是
	 */
	public static boolean isHefaPort(int port) {
		return port >= 1000 && port <= 65532;
	}
	
	
	/**
	 * (2.02)、判断指定的字符串是否是合法的端口号，取值范围为1000——65532
	 * @param port 指定的字符串
	 * @return true：是
	 */
	public static boolean isHefaPort(String port) {
		int port2 = Integer.parseInt(port);
		return isHefaPort(port2);
	}
	
	
	/**
	 * (2.03)、从指定的端口开始，获取一个可用的端口号
	 * @param port 从此开始
	 * @return 可用的端口号，若为-1说明传入的端口号不在1000——65532范围内
	 */
	public static int getPort(int port) {
		if(isHefaPort(port)){
			boolean jixu = true;
			while (jixu){
				try {
					DatagramSocket ds = new DatagramSocket(port);
					ds.close();
					jixu = false;
				} catch (SocketException e) {
					port++;
				}
			}
			return port;
		}else{
			return -1;
		}
	}
	
	
	/**
	 * (2.04)、判断指定的数字是否是一个可用的端口号
	 * @param port 端口号
	 * @return true：可用
	 */
	public static boolean isKeYongPort(int port) {
		if(isHefaPort(port)){
			try {
				DatagramSocket ds = new DatagramSocket(port);
				ds.close();
				return true;
			} catch (SocketException e) {
				return false;
			}
		}else{
			return false;
		}
	}
	/* *****************************************************2、socket方面over******************************************************************* */





	/* *****************************************************3、其它start******************************************************************* */
	/**
	 * (3.01)、根据指定的文件长度，以及线程数，计算每个线程所对应的开始与结束位置
	 * @param fileLength 文件长度，范围【1————Integer.MAX_VALUE】
	 * @param threadNumber 线程数，范围【1————fileSize】
	 */
	public static long[][] countThread(long fileLength, int threadNumber){
		CheckingUtils.valiLongMinValue(fileLength, 1, "fileLength");
		CheckingUtils.valiLongValue(threadNumber, 1, fileLength, "threadNumber");
		long length = fileLength%threadNumber == 0 ? fileLength/threadNumber : fileLength/threadNumber + 1;
		long[][] threads = new long[threadNumber][2];
		for(int w = 0; w < threads.length; w++){
			threads[w][0] = w*length;
			threads[w][1] = (w+1)*length-1;			
		}
		threads[threads.length-1][1] = fileLength-1;	
		return threads;
	}
	
	/**
	 * (3.02)、从一个url中获取字节数据
	 * @param url 网络文件的URL地址
	 * @param connTimeout 连接超时时间，范围【1-60*1000】
	 * @param length 每次读取的字节长度
	 * @return 字节数组
	 * @throws IOException IO异常
	 */
	public static byte[] getDataFromUrl(URL url, int connTimeout, int length) throws IOException{			
		//参数验证
		CheckingUtils.valiIntValue(connTimeout, 1, 60*1000, "connTimeout");
		CheckingUtils.valiIntValue(length, 1, 1024*1024, "length");
		
		//正文部分
		HttpURLConnection hurlc = (HttpURLConnection) NetUtils.getURLConnFromURL(url, connTimeout);	//建立远程连接对象				
		hurlc.connect();																					//打开网络连接			
		return IOUtils.read(new BufferedInputStream(hurlc.getInputStream()));
	}
	/* *****************************************************3、其它over******************************************************************* */
}