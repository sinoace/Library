package net.sinoace.library.utils;

import java.io.File;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 图片加载对象
 */
public class ImageLoad {
	/**
	 * 下载地址
	 */
	private String url;
	/**
	 * 本地源文件
	 */
	private File localSourceFile;
	/**
	 * 加载结果
	 */
	private Bitmap loadResult;
	/**
	 * 加载方式
	 */
	private LoadWay loadWay;
	/**
	 * 图片视图
	 */
	private ImageView imageView;
	/**
	 * 是否缓存
	 */
	private boolean isCache;
	/**
	 * 是否保存
	 */
	private boolean isSave;
	/**
	 * 加载次数
	 */
	private int loads;
	
	/**
	 * 从网络加载
	 * @param imageUrl 图片地址
	 * @param imageView 显示图片的视图
	 * @param isCache 是否缓存到内存中
	 * @return
	 */
	public static ImageLoad fromNetwork(String imageUrl, ImageView imageView, boolean isCache){
		ImageLoad imageLoad = new ImageLoad();
		imageLoad.setUrl(imageUrl);
		imageLoad.setImageView(imageView);
		imageLoad.setCache(isCache);
		imageLoad.setLoadWay(LoadWay.FROM_NET);
		return imageLoad;
	}
	
	/**
	 * 从本地加载
	 * @param sourceFile 源文件
	 * @param imageView 显示视图
	 * @param isCache 是否缓存到内存中
	 * @return
	 */
	public static ImageLoad fromLocal(File localSourceFile, ImageView imageView, boolean isCache){
		ImageLoad imageLoad = new ImageLoad();
		imageLoad.setLocalSourceFile(localSourceFile);
		imageLoad.setImageView(imageView);
		imageLoad.setCache(isCache);
		imageLoad.setLoadWay(LoadWay.FROM_NET);
		return imageLoad;
	}
	
	/**
	 * 如果源文件存在就从源文件中读取数据，否则就从网络地址上下载
	 * @param sourceFile 源文件
	 * @param imageView 显示视图
	 * @param imageUrl 网络地址
	 * @param isSave 当需要从网络上下载图片时，是否需要将图片保存到源文件中
	 * @param isCache 是否缓存到内存中
	 * @return 
	 */
	public static ImageLoad fromLocalByPriority(File localSourceFile, ImageView imageView, String imageUrl, boolean isSave, boolean isCache){
		ImageLoad imageLoad = new ImageLoad();
		imageLoad.setLocalSourceFile(localSourceFile);
		imageLoad.setImageView(imageView);
		imageLoad.setUrl(imageUrl);
		imageLoad.setSave(isSave);
		imageLoad.setCache(isCache);
		imageLoad.setLoadWay(LoadWay.FROM_LOCAL_BY_PRIORITY);
		return imageLoad;
	}
	
	/**
	 * 获取下载地址
	 * @return 下载地址
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * 设置下载地址
	 * @param url 下载地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 获取本地源文件
	 * @return 本地源文件
	 */
	public File getLocalSourceFile() {
		return localSourceFile;
	}
	
	/**
	 * 设置本地源文件
	 * @param localSourceFile 本地源文件
	 */
	public void setLocalSourceFile(File localSourceFile) {
		this.localSourceFile = localSourceFile;
	}
	
	/**
	 * 获取加载结果
	 * @return 加载结果
	 */
	public Bitmap getLoadResult() {
		return loadResult;
	}

	/**
	 * 设置加载结果
	 * @param loadResult 加载结果
	 */
	public void setLoadResult(Bitmap loadResult) {
		this.loadResult = loadResult;
	}

	/**
	 * 获取加载方式
	 * @return 加载方式
	 */
	public LoadWay getLoadWay() {
		return loadWay;
	}
	
	/**
	 * 设置加载方式
	 * @param loadWay 加载方式
	 */
	public void setLoadWay(LoadWay loadWay) {
		this.loadWay = loadWay;
	}

	/**
	 * 获取图片视图
	 * @return 图片视图
	 */
	public ImageView getImageView() {
		return imageView;
	}

	/**
	 * 设置图片视图
	 * @param imageView 图片视图
	 */
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	/**
	 * 判断是否缓存
	 * @return 是否缓存
	 */
	public boolean isCache() {
		return isCache;
	}

	/**
	 * 设置是否缓存
	 * @param isCache 是否缓存
	 */
	public void setCache(boolean isCache) {
		this.isCache = isCache;
	}

	/**
	 * 判断是否保存
	 * @return 是否保存
	 */
	public boolean isSave() {
		return isSave;
	}

	/**
	 * 设置是否保存
	 * @param isSave 是否保存
	 */
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	/**
	 * 获取加载次数
	 * @return 加载次数
	 */
	public int getLoads() {
		return loads;
	}

	/**
	 * 设置加载次数
	 * @param loads 加载次数
	 */
	public void setLoads(int loads) {
		this.loads = loads;
	}
}