package net.sinoace.library.widget;

import net.sinoace.library.utils.ViewUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author xiaopan
 */
public abstract class AbsPullListHeaderAndFoooter extends LinearLayout {
	/**
	 * 内容视图
	 */
	private LinearLayout contentView;
	/**
	 * 上次加载时间
	 */
	private long lastLoadTime;
	/**
	 * 原始高度
	 */
	private int originalHeight;
	/**
	 * 最小高度
	 */
	private int minHeight;
	/**
	 * 状态
	 */
	private State state;
	/**
	 * 状态改变监听器
	 */
	private OnStateChangeListener onStateChangeListener;
	/**
	 * 是列表头
	 */
	private boolean isListHeader;
	
	public AbsPullListHeaderAndFoooter(Context context) {
		super(context);
		init();
	}

	public AbsPullListHeaderAndFoooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 初始化
	 */
	private void init(){
		//设置流向
		setOrientation(LinearLayout.VERTICAL);
		//初始化状态
		setState(State.NORMAL);
		//初始化内容视图
		setContentView(onGetContentView());
		//设置视图的高度为最小高度
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, getMinHeight());
		getContentView().setLayoutParams(layoutParams);
		//初始化原始高度
		setOriginalHeight(onGetOriginalHeight(getContentView()));
		//将内容视图添加到下拉刷新布局上
		addView(getContentView());
	}
	
	/**
	 * 当需要获取获取内容视图
	 * @return 内容视图，就是在显示在列表头头或者尾的视图，之后改变高度也是要改变内容视图的高度
	 */
	protected abstract LinearLayout onGetContentView();
	
	/**
	 * 当从正常状态进入准备加载状态
	 * @param contentView 内容视图
	 */
	protected abstract void onNormalToReadyLoadState(LinearLayout contentView);
	
	/**
	 * 当从准备加载状态进入正常状态
	 * @param contentView 内容视图
	 */
	protected abstract void onReadyLoadToNormalState(LinearLayout contentView);
	
	/**
	 * 当从准备加载状态进入加载中状态
	 * @param contentView 内容视图
	 */
	protected abstract void onReadyLoadToLoadingState(LinearLayout contentView);
	
	/**
	 * 当从加载中状态到正常状态
	 * @param contentView 内容视图
	 */
	protected abstract void onLoadingToNormalState(LinearLayout contentView);
	
	/**
	 * 当更新上次刷新时间
	 * @param contentView 内容视图
	 * @param lastLoadTime 上次刷新时间
	 */
	protected abstract void onUpdateLastLoadTime(LinearLayout contentView, long lastLoadTime);
	
	/**
	 * 当需要获取原始高度
	 */
	protected abstract int onGetOriginalHeight(LinearLayout contentView);
	
	public void onRecoveryMinHeight(){
		
	}
	
	/**
	 * 进入加载中状态
	 */
	public void intoLoadingState(){
		//设置状态为正在加载
		setState(State.LOADING);
		//调用相应回调方法修改界面显示
		onReadyLoadToLoadingState(getContentView());
		//调用相应状态监听器
		if(getOnStateChangeListener() != null){
			getOnStateChangeListener().onReadyLoadToLoadingState();
		}
		//设置加载时间
		setLastLoadTime(System.currentTimeMillis());
		//调用相应回调函数更改界面中的加载时间
		onUpdateLastLoadTime(getContentView(), getLastLoadTime());
	}
	
	/**
	 * 进入正在加载变为正常状态
	 */
	public void intoLoadingToNormalState(){
		//设置状态为由加载中转为正常
		setState(State.LOADING_TO_NORMAL);
		//调用相应状态监听器
		if(getOnStateChangeListener() != null){
			getOnStateChangeListener().onLoadingToNormalState();
		}
	}
	
	/**
	 * 设置内容视图的高度
	 * @param newHeight 新的高度
	 */
	public void setVisiableHeight(int newHeight) {
		//如果新的高度小于回滚的最终高度就将其设置为回滚的最终高度
		if(newHeight < getRollbackFinalHeight()){
			newHeight = getRollbackFinalHeight();
		}
		
		//如果当前状态是正常
		if(isNormalState()){
			//如果新的高度大于原始高度，说明是要从正常状态进入准备加载状态了
			if(newHeight >= getOriginalHeight()){
				//设置状态为准备加载
				setState(State.READY_LOAD);
				//调用相应回调方法修改界面显示
				onNormalToReadyLoadState(getContentView());
				//调用相应状态监听器
				if(getOnStateChangeListener() != null){
					getOnStateChangeListener().onNormalToReadyLoadState();
				}
			}
		//如果当前状态是准备加载
		}else if(isReadyLoadState()){
			//如果新的高度小于原始高度，说明是要从准备加载状态变为正常状态了
			if(newHeight < getOriginalHeight()){
				//设置状态为正常
				setState(State.NORMAL);
				//调用相应回调方法修改界面显示
				onReadyLoadToNormalState(getContentView());
				//调用相应状态监听器
				if(getOnStateChangeListener() != null){
					getOnStateChangeListener().onReadyLoadToNormalState();
				}
			}
		//如果正在加载
		}else if(isLoadingState()){
			
		//正在由加载中变为正常
		}else if(isLoadingToNormalState()){
			//如果已经回滚到最小高度了
			if(newHeight <= getMinHeight()){
				//设置状态为正常
				setState(State.NORMAL);
				//调用相应回调方法修改界面显示
				onLoadingToNormalState(getContentView());
				//调用相应状态监听器
				if(getOnStateChangeListener() != null){
					getOnStateChangeListener().onLoadingToNormalState();
				}
			}
		}
		
		//更新高度
		ViewUtils.setViewHeight(getContentView(), newHeight);
		
		//如果新的高度等于最小高度，那么就调用状态改变监听器
		if(newHeight == getMinHeight()){
			onRecoveryMinHeight();
			if(getOnStateChangeListener() != null){
				getOnStateChangeListener().onRecoveryMinHeight();
			}
		}
	}
	
	/**
	 * 获取内容视图的高度
	 * @return 内容视图的高度
	 */
	public int getVisiableHeight() {
		return contentView.getHeight();
	}
	
	/**
	 * 获取回滚高度
	 * @return 回滚高度，当是加载中状态时回滚高度是原始高度，否则一律是最小高度
	 */
	public int getRollbackFinalHeight(){
		//如果正在加载就回滚到原始高度，否则回滚到最小高度
		return isLoadingState()?getOriginalHeight():getMinHeight();
	}
	
	/**
	 * 是否是正常状态
	 * @return
	 */
	public boolean isNormalState(){
		return getState() == State.NORMAL;
	}
	
	/**
	 * 是否是准备加载状态
	 * @return
	 */
	public boolean isReadyLoadState(){
		return getState() == State.READY_LOAD;
	}
	
	/**
	 * 是否加载中状态
	 * @return
	 */
	public boolean isLoadingState(){
		return getState() == State.LOADING;
	}
	
	/**
	 * 是否由加载中变为正常状态
	 * @return
	 */
	public boolean isLoadingToNormalState(){
		return getState() == State.LOADING_TO_NORMAL;
	}
	
	/**
	 * 获取内容视图
	 * @return 内容视图
	 */
	public LinearLayout getContentView() {
		return contentView;
	}

	/**
	 * 设置内容视图
	 * @param contentView 内容视图
	 */
	public void setContentView(LinearLayout contentView) {
		this.contentView = contentView;
	}

	/**
	 * 获取原始高度
	 * @return 原始高度
	 */
	public int getOriginalHeight() {
		return originalHeight;
	}

	/**
	 * 设置原始高度
	 * @param originalHeight 原始高度
	 */
	public void setOriginalHeight(int originalHeight) {
		this.originalHeight = originalHeight;
	}
	
	/**
	 * 获取状态
	 * @return 状态
	 */
	public State getState() {
		return state;
	}

	/**
	 * 设置状态
	 * @param state 状态
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * 获取上次加载时间
	 * @return 上次加载时间
	 */
	public long getLastLoadTime() {
		return lastLoadTime;
	}

	/**
	 * 设置上次加载时间
	 * @param lastLoadTime
	 */
	public void setLastLoadTime(long lastLoadTime) {
		this.lastLoadTime = lastLoadTime;
	}

	/**
	 * 获取最小高度
	 * @return 最小高度
	 */
	public int getMinHeight() {
		return minHeight;
	}

	/**
	 * 设置最小高度
	 * @param minHeight 最小高度
	 */
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	/**
	 * 获取状态改变监听器
	 * @return 状态改变监听器
	 */
	public OnStateChangeListener getOnStateChangeListener() {
		return onStateChangeListener;
	}

	/**
	 * 设置状态改变监听器
	 * @param onStateChangeListener 状态改变监听器
	 */
	public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
		this.onStateChangeListener = onStateChangeListener;
	}

	/**
	 * 是否是列表头
	 * @return 是否是列表头
	 */
	public boolean isListHeader() {
		return isListHeader;
	}

	/**
	 * 设置是否是列表头
	 * @param isListHeader 是否是列表头
	 */
	public void setListHeader(boolean isListHeader) {
		this.isListHeader = isListHeader;
	}

	/**
	 * 状态
	 * @author xiaopan
	 */
	public enum State{
		/**
		 * 正常
		 */
		NORMAL,
		/**
		 * 准备加载
		 */
		READY_LOAD,
		/**
		 * 加载中
		 */
		LOADING,
		/**
		 * 加载中到正常
		 */
		LOADING_TO_NORMAL;
	}
	
	/**
	 * 状态改变监听器
	 * @author xiaopan
	 *
	 */
	public abstract static class OnStateChangeListener{
		/**
		 * 当恢复到最小高度
		 */
		public void onRecoveryMinHeight(){
			
		}
		
		/**
		 * 当从正常状态进入准备加载状态
		 */
		public void onNormalToReadyLoadState(){
			
		}
		
		/**
		 * 当从准备加载状态进入正常状态
		 */
		public void onReadyLoadToNormalState(){
			
		}
		
		/**
		 * 当从准备加载状态进入加载中状态
		 */
		public void onReadyLoadToLoadingState(){
			
		}
		
		/**
		 * 当从加载中状态到正常状态
		 */
		public void onLoadingToNormalState(){
			
		}
	}
}