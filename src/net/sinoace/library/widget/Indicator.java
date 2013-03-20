package net.sinoace.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 指示器抽象类，定义了初始化以及选中的接口
 */
public abstract class Indicator extends LinearLayout{

	public Indicator(Context context) {
		super(context);
	}
	
	public Indicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

//	public Indicator(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//	}

	/**
	 * 当需要初始化的时候
	 * @param size 元素个数
	 */
	public abstract void onInit(int size);
	
	/**
	 * 当有选项被选中的时候
	 * @param position 被选中选项的位置
	 */
	public abstract void onItemSelected(int position);
}