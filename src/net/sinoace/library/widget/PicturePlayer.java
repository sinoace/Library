package net.sinoace.library.widget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sinoace.library.utils.ImageLoader;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.SpinnerAdapter;

/**
 * 图片播放器
 */
public class PicturePlayer extends FrameLayout implements OnItemSelectedListener, OnItemClickListener,  OnGetViewListener{
	private DefaultGallery gallery;//画廊
	private Indicator indicator;//指示器
	private Handler switchHandler;//切换处理器
	private SwitchHandle switchHandle;//切换处理
	private boolean loadSuucess;//加载成功
	private int switchSpace = 4000;//切换间隔
	private int defaultImageResId;//默认图片ID
	private ScaleType imageScaleType = ScaleType.FIT_CENTER;
	private List<Picture> pictures;//
	private boolean loopPlayback = true;//循环播放
	private boolean currentTowardsTheRight = true;//当前向右播放
	private boolean towardsTheRight = true;//向右播放
	private OnItemSelectedListener onItemSelectedListener;
	private OnItemClickListener onItemClickListener;
	private int animationDurationMillis = 600;
	private SpinnerAdapter imageAdapter;
	private OnGetViewListener onGetViewListener;
	
	public PicturePlayer(Context context) {
		super(context);
	}
	
	public PicturePlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PicturePlayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/**
	 * 开始播放
	 */
	public void startPaly(){
		//如果之前加载失败了
		if(!isLoadSuucess()){
			if(pictures != null && pictures.size() > 0){
				setLoadSuucess(true);
				removeAllViews();
				
				//初始化自动切换处理器
				switchHandler = new Handler();
				switchHandle = new SwitchHandle();
				
				//初始化指示器
				indicator = new PointIndicator(getContext());
				indicator.onInit(pictures.size());

				/*
				 * 初始化画廊
				 */
				gallery = new DefaultGallery(getContext());
				gallery.setAnimationDuration(animationDurationMillis);//设置动画持续时间，默认是600毫秒
				gallery.setOnItemSelectedListener(this);
				gallery.setOnItemClickListener(this);
				
				/*
				 * 初始化适配器
				 */
				if(getOnGetViewListener() == null){
					setOnGetViewListener(this);
				}
				if(getGalleryAdapter() == null){		//如果没有自定义的适配器
					setGalleryAdapter(new DefaultGalleryAdapter());		//使用默认的适配器
				}
				gallery.setAdapter(getGalleryAdapter());
				
				/*
				 * 初始化默认选中项
				 */
				if(loopPlayback){		//如果要循环播放，那么就要定位到最中间，以便可以向左或向右无限滚动
					int defaultPosition = ((Integer.MAX_VALUE/pictures.size())/2)*pictures.size();		//默认选中最中间的第一张
					if(!towardsTheRight){		//如果播放方向是向左
						defaultPosition += pictures.size() -1;		//那么就选中最中间的最后一张
					} 
					gallery.setSelection(defaultPosition);
				}
				
				/*
				 * 将画廊和指示器放进布局中
				 */
				addView(gallery);
				addView(indicator);
			}else{
				setLoadSuucess(false);
			}
		}
		
		//如果加载成功了
		if(isLoadSuucess()){
			switchHandler.removeCallbacks(switchHandle);
			switchHandler.postDelayed(switchHandle, switchSpace);
		}
	}
	
	/**
	 * 停止播放
	 */
	public void stopPaly(){
		//如果加载成功了
		if(isLoadSuucess()){
			switchHandler.removeCallbacks(switchHandle);
		}
	}

	/**
	 * 设置URLS
	 * @param imageUrls
	 */
	public void setUrls(String... imageUrls){
		pictures = new ArrayList<Picture>(imageUrls.length);
		for(int w = 0; w < imageUrls.length; w++){
			pictures.add(new Picture(imageUrls[w]));
		}
	}
	
	/**
	 * 获取当前选中项的真实位置
	 * @param gallerySelectedItemPosition
	 * @return 当前选中项的真实位置
	 */
	private int getRealSelectedItemPosition(int gallerySelectedItemPosition){
		if(loopPlayback){
			return gallerySelectedItemPosition % pictures.size();
		}else{
			return gallerySelectedItemPosition;
		}
	}
	
	/**
	 * 获取选中项的位置
	 * @return 选中项的位置
	 */
	public int getSelectedItemPosition(){
		return getRealSelectedItemPosition(gallery.getSelectedItemPosition());
	}

	/**
	 * 切换处理
	 * @author xiaopan
	 */
	private class SwitchHandle implements Runnable{
		@Override
		public void run() {
			if(loopPlayback){
				if(towardsTheRight){
					//因为使用myGallery.setSelection(int , boolean);切换的时候没有动画，所以使用模拟按键事件的方式来切换
					gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				}else{
					gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
				}
			}else{
				//如果当前是向右播放
				if(currentTowardsTheRight){
					//如果到最后一个了
					if(gallery.getSelectedItemPosition() == pictures.size() -1){
						currentTowardsTheRight = false;//标记为向左
						gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
					}else{
						gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
					}
				}else{
					//如果到第一个了
					if(gallery.getSelectedItemPosition() == 0){
						currentTowardsTheRight = true;
						gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
					}else{
						gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
					}
				}
			}
			switchHandler.postDelayed(switchHandle, switchSpace);
		}
	}
	
	/**
	 * 默认的画廊
	 * @author xiaopan
	 */
	private class DefaultGallery extends Gallery {
		public DefaultGallery(Context context) {
			super(context);
			setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
			setSoundEffectsEnabled(false);//不使用切换音效
			setSpacing(-1);//设置间距为-1，因为使用按键自动切换的时候间距大于等于0会导致切换失败，所以间距只能是-1//设置当画廊选中项改变时，改变指示器
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e2.getX() > e1.getX()) {
				onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			} else {
				onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}
			return false;
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: stopPaly(); break;
				case MotionEvent.ACTION_UP: startPaly(); break;
				default: break;
			}
			return super.onTouchEvent(event);
		}
	}
	
	/**
	 * 默认的画廊适配器
	 * @author xiaopan
	 */
	private class DefaultGalleryAdapter extends BaseAdapter{
		private ImageLoader imageLoader;
		private DefaultGalleryAdapter(){
			imageLoader = new ImageLoader(getDefaultImageResId());
		}
		
		@Override
		public int getCount() {
			if(loopPlayback){
				return Integer.MAX_VALUE;
			}else{
				return pictures.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return pictures.get(getRealSelectedItemPosition(position));
		}

		@Override
		public long getItemId(int position) {
			return getRealSelectedItemPosition(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getOnGetViewListener().onGetView(convertView, parent, pictures.get(getRealSelectedItemPosition(position)), imageLoader);
		}
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		if(getOnItemSelectedListener() != null){		//回调
			getOnItemSelectedListener().onNothingSelected(parent);
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		int realSelectedItemPosition = getRealSelectedItemPosition(position);		//获取真实的位置，
		indicator.onItemSelected(realSelectedItemPosition);		//修改指示器的选中项
		if(getOnItemSelectedListener() != null){		//回调
			getOnItemSelectedListener().onItemSelected(parent, view, realSelectedItemPosition, id);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(getOnItemClickListener() != null){		//回调
			getOnItemClickListener().onItemClick(parent, view, position, id);
		}
	}
	
	@Override
	public View onGetView(View convertView, ViewGroup parent, Picture picture, ImageLoader imageLoader) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			ImageView imageView = new ImageView(getContext());
			imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
			imageView.setScaleType(getImageScaleType());
			viewHolder.imageView = imageView;
			convertView = imageView;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(picture.getFile() != null){
			imageLoader.fromLocalByPriority(picture.getFile(), viewHolder.imageView, picture.getUrl());
		}else{
			imageLoader.fromNetwork(picture.getUrl(), viewHolder.imageView);
		}
		return convertView;
	}
	
	private class ViewHolder{
		public ImageView imageView;
	}
	
	/**
	 * 判断是否加载成功
	 * @return 是否加载成功，主要受urls的影响
	 */
	public boolean isLoadSuucess() {
		return loadSuucess;
	}

	/**
	 * 设置是否加载成功
	 * @param loadSuucess 是否加载成功
	 */
	public void setLoadSuucess(boolean loadSuucess) {
		this.loadSuucess = loadSuucess;
	}

	/**
	 * 获取画廊
	 * @return 画廊
	 */
	public DefaultGallery getGallery() {
		return gallery;
	}

	/**
	 * 获取指示器
	 * @return 指示器
	 */
	public Indicator getIndicator() {
		return indicator;
	}
	
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	/**
	 * 获取切换间隔
	 * @return 切换间隔
	 */
	public int getSwitchSpace() {
		return switchSpace;
	}

	/**
	 * 设置切换间隔
	 * @param switchSpace 切换间隔
	 */
	public void setSwitchSpace(int switchSpace) {
		this.switchSpace = switchSpace;
	}

	/**
	 * 获取图片缩放类型
	 * @return 图片缩放类型
	 */
	public ScaleType getImageScaleType() {
		return imageScaleType;
	}

	/**
	 * 设置图片缩放类型
	 * @param imageScaleType 图片缩放类型
	 */
	public void setImageScaleType(ScaleType imageScaleType) {
		this.imageScaleType = imageScaleType;
	}

	/**
	 * 获取默认图片的资源ID
	 * @return 默认图片的资源ID
	 */
	public int getDefaultImageResId() {
		return defaultImageResId;
	}

	/**
	 * 设置默认图片的资源ID
	 * @param defaultImageResId 默认图片的资源ID
	 */
	public void setDefaultImageResId(int defaultImageResId) {
		this.defaultImageResId = defaultImageResId;
	}

	/**
	 * 获取要播放的图片的列表
	 * @return 要播放的图片的列表
	 */
	public List<Picture> getPictures() {
		return pictures;
	}

	/**
	 * 设置要播放的图片的列表
	 * @param pictures 要播放的图片的列表
	 */
	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	/**
	 * 判断是否循环播放
	 * @return 是否循环播放，默认是
	 */
	public boolean isLoopPlayback() {
		return loopPlayback;
	}

	/**
	 * 设置是否循环播放
	 * @param loopPlayback 是否循环播放，默认是
	 */
	public void setLoopPlayback(boolean loopPlayback) {
		this.loopPlayback = loopPlayback;
	}

	/**
	 * 判断是否往右播放
	 * @return 是否往右播放，默认是
	 */
	public boolean isTowardsTheRight() {
		return towardsTheRight;
	}

	/**
	 * 设置是否往右播放
	 * @param towardsTheRight 是否往右播放，默认是
	 */
	public void setTowardsTheRight(boolean towardsTheRight) {
		this.towardsTheRight = towardsTheRight;
	}

	/**
	 * 获取项选择监听器
	 * @return 项选择监听器
	 */
	public OnItemSelectedListener getOnItemSelectedListener() {
		return onItemSelectedListener;
	}

	/**
	 * 设置项选择监听器
	 * @param onItemSelectedListener 项选择监听器
	 */
	public void setOnItemSelectedListener(
			OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}

	/**
	 * 获取项点击监听器
	 * @return 项点击监听器
	 */
	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	/**
	 * 设置项点击监听器
	 * @param onItemClickListener 项点击监听器
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	/**
	 * 获取动画持续时间
	 * @return 动画持续时间
	 */
	public int getAnimationDurationMillis() {
		return animationDurationMillis;
	}

	/**
	 * 设置动画持续时间
	 * @param animationDurationMillis 动画持续时间
	 */
	public void setAnimationDurationMillis(int animationDurationMillis) {
		this.animationDurationMillis = animationDurationMillis;
	}

	/** 
	 * 获取画廊需要的适配器
	 * @return 画廊需要的适配器
	 */
	public SpinnerAdapter getGalleryAdapter() {
		return imageAdapter;
	}

	/**
	 * 设置画廊需要的适配器
	 * @param imageAdapter 画廊需要的适配器
	 */
	public void setGalleryAdapter(SpinnerAdapter imageAdapter) {
		this.imageAdapter = imageAdapter;
	}

	/**
	 * 获取获取视图监听器
	 * @return 获取视图监听器
	 */
	public OnGetViewListener getOnGetViewListener() {
		return onGetViewListener;
	}

	/**
	 * 设置获取视图监听器
	 * @param onGetViewListener 获取视图监听器
	 */
	public void setOnGetViewListener(OnGetViewListener onGetViewListener) {
		this.onGetViewListener = onGetViewListener;
	}
	
	/**
	 * 图片对象
	 * @author xiaopan
	 */
	public static class Picture{
		private String url;
		private File file;
		private Object object;
		
		public Picture(String url, File file, Object object){
			setUrl(url);
			setFile(file);
			setObject(object);
		}
		
		public Picture(String url, File file){
			setUrl(url);
			setFile(file);
		}
		
		public Picture(String url){
			this(url, null);
		}
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
		
		public File getFile() {
			return file;
		}
		
		public void setFile(File file) {
			this.file = file;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}
	}
}