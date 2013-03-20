package net.sinoace.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 点击加载更多列表尾
 */
public abstract class AbsClickLoadMoreListFooter extends LinearLayout {
	private LinearLayout contentView;
	private State state = State.NORMAL;
	
	public AbsClickLoadMoreListFooter(Context context) {
		super(context);
		init();
	}

	public AbsClickLoadMoreListFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		//设置流向
		setOrientation(LinearLayout.VERTICAL);
		//设置内容视图
		setContentView(onGetContentView());
		//将内容视图添加到下拉刷新布局上
		addView(getContentView());
	}
	
	/**
	 * 当需要获取获取内容视图
	 * @return 内容视图，就是在显示在列表头头或者尾的视图，之后改变高度也是要改变内容视图的高度
	 */
	protected abstract LinearLayout onGetContentView();
	
	/**
	 * 当进入加载中状态
	 * @param contentView 内容视图
	 */
	protected abstract void onIntoLoadingState(LinearLayout contentView);
	
	/**
	 * 当进入正常状态
	 * @param contentView 内容视图
	 */
	protected abstract void onIntoNormalState(LinearLayout contentView);
	
	/**
	 * 进入加载中状态
	 */
	public void intoLoadingState(){
		onIntoLoadingState(getContentView());
		setState(State.LOADING);
	}
	
	/**
	 * 进入正常状态
	 */
	public void intoNormalState(){
		onIntoNormalState(getContentView());
		setState(State.NORMAL);
	}
	
	public LinearLayout getContentView() {
		return contentView;
	}

	public void setContentView(LinearLayout contentView) {
		this.contentView = contentView;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	/**
	 * 点击加载更多监听器 
	 */
	public interface ClickLoadMoreListener{
		public void onLoadMore(ClickLoadMoreFinishListener clickLoadMoreFinishListener);
	}
	
	/**
	 * 点击加载更多终止监听器
	 */
	public interface ClickLoadMoreFinishListener{
		/**
		 * 终止点击加载
		 */
		public void finishClickLoadMore();
	}

	/**
	 * 状态
	 */
	public enum State{
		/**
		 * 正常
		 */
		NORMAL,
		/**
		 * 加载中
		 */
		LOADING;
	}
}