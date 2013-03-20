package net.sinoace.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;

/**
 * 反弹列表
 */
public class ReboundListView extends PullListView {

	public ReboundListView(Context context) {
		super(context);
	}

	public ReboundListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ReboundListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		openListHeaderReboundMode();//打开列表头反弹模式
		openListFooterReboundMode();//打开列表尾反弹模式
		super.setAdapter(adapter);
	}
}