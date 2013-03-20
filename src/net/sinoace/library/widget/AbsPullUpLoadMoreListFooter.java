package net.sinoace.library.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 上拉加载更多列表尾
 */
public abstract class AbsPullUpLoadMoreListFooter extends AbsPullListHeaderAndFoooter {
	public AbsPullUpLoadMoreListFooter(Context context) {
		super(context);
	}

	public AbsPullUpLoadMoreListFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 上拉加载更多监听器 
	 * @author xiaopan
	 */
	public interface PullUpLoadMoreListener{
		public void onLoadMore(PullUpLoadMoreFinishListener pullUpLoadMoreFinishListener);
	}
	
	/**
	 * 上拉加载更多终止监听器
	 * @author xiaopan
	 *
	 */
	public interface PullUpLoadMoreFinishListener{
		/**
		 * 终止加载
		 */
		public void finishLoadMore();
	}
}