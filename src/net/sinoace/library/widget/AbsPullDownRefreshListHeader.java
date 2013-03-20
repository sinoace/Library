package net.sinoace.library.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 下拉刷新列表头
 * @author xiaopan
 */
public abstract class AbsPullDownRefreshListHeader extends AbsPullListHeaderAndFoooter {
	public AbsPullDownRefreshListHeader(Context context) {
		super(context);
	}

	public AbsPullDownRefreshListHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 下拉刷新监听器 
	 * @author xiaopan
	 */
	public interface PullDownRefreshListener{
		/**
		 * 当开始刷新
		 * @param pullDownRefreshFinishListener
		 */
		public void onRefresh(PullDownRefreshFinishListener pullDownRefreshFinishListener);
	}
	
	/**
	 * 下拉刷新终止监听器
	 * @author xiaopan
	 *
	 */
	public interface PullDownRefreshFinishListener{
		/**
		 * 终止刷新
		 */
		public void finishRefresh();
	}
}