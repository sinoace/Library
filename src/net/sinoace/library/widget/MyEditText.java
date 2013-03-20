package net.sinoace.library.widget;

import net.sinoace.library.utils.Colors;
import net.sinoace.library.utils.TextUtils;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * <h2>自定义文本编辑器，可以在编辑器的左边加上名称比如（账号：）以及在右边加上一个清除图标，点击清除图标即可清除编辑器已输入的内容</h2>
 * <br>使用方式：
 * <br>在布局中通过android:contentDescription属性指定左边的名称，通过android:drawableRight属性指定右边的清除图标即可
 */
public class MyEditText extends EditText {
	private boolean fromMe;
	private Drawable clearDrawable;//清除图标
	private MyTextWatcher myTextWatcher;
	private OnTouchListener onTouchListener;
	
	public MyEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public MyEditText(Context context) {
		super(context);
		init(null);
	}
	
	/**
	 * 初始化
	 * @param attrs
	 */
	private void init(AttributeSet attrs){
		//初始化触摸监听器
		fromMe = true;
		setOnTouchListener(new MyOnTouchListener());

		//尝试初始化编辑器名称
		tryInitEditName((String) getContentDescription());
		
		//尝试初始化清除图标
		tryInitClearDrawable(getCompoundDrawables()[2]);	
	}
	
	/**
	 * 尝试初始化编辑器名称
	 */
	private void tryInitEditName(String editName){
		//名字不为null也不为空就执行初始化
		if(editName != null && !"".equals(editName)){
			Drawable nameDrawable = new BitmapDrawable(getContext().getResources(), TextUtils.getTextBitmap(getContext(), editName, Colors.GRAY_DARK, getTextSize()));
			Drawable[] drawables = getCompoundDrawables();
			setCompoundDrawablesWithIntrinsicBounds(nameDrawable, drawables[1], drawables[2], drawables[3]);
		}
	}
	
	/**
	 * 尝试初始化清除图标
	 */
	private void tryInitClearDrawable(Drawable clearDrawable){
		this.clearDrawable = clearDrawable;
		if(this.clearDrawable != null){
			//添加文本改变监听器
			if(myTextWatcher == null){
				myTextWatcher = new MyTextWatcher();
			}
			addTextChangedListener(myTextWatcher);
			//刺激一下刚刚添加的内容改变监听器，这样做的效果是当在布局中设置的有默认值时，会立即显示出清除图标
			setText(getText().toString());
		}else{
			if(myTextWatcher != null){
				removeTextChangedListener(myTextWatcher);
			}
		}
	}
	
	/**
	 * 内容改变监听器，当文本编辑器的内容为空时不显示清除图标，反之显示清除图标
	 */
	private class MyTextWatcher implements TextWatcher{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		@Override
		public void afterTextChanged(Editable s) {
			Drawable[] drawables = getCompoundDrawables();
			if(s.toString().length() > 0) {
				if(drawables[2] == null){
					setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], clearDrawable, drawables[3]);
				}
			} else {
				if(drawables[2] != null){
					setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null, drawables[3]);
				}
			}
		}
	}
	
	/**
	 * 当触摸了文本编辑器的时候，会判断是否触摸到了清除图标上，是的话就会尝试清除文本编辑器的内容
	 */
	private class MyOnTouchListener implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			boolean result = false;
			if(clearDrawable != null && getCompoundDrawables()[2] != null){
				switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						int curX = (int) event.getX();//转换触摸位置的X坐标
						int curY = (int) event.getY();//转换触摸位置的Y坐标
						int rightDrawableTop = (getHeight() - clearDrawable.getIntrinsicHeight()) / 2;//计算清除图标的Top值
						//如果点击范围正好在清除图标上并且文本编辑器的内容不为空
						if(curX >= getWidth() - (clearDrawable.getIntrinsicWidth() + getPaddingRight()) && 
							curX <= getWidth() - getPaddingRight() && 
							curY >= rightDrawableTop && 
							curY <= getHeight() - rightDrawableTop && 
							getText().toString().length() > 0) {
							setText("");
						}	
					break;
				}
			}
			if(!result && onTouchListener != null){
				result = onTouchListener.onTouch(v, event);
			}
			return result;
		}
	}
	
	@Override
	public void setOnTouchListener(OnTouchListener l) {
		if(fromMe){
			super.setOnTouchListener(l);
			fromMe = false;
		}else{
			this.onTouchListener = l;
		}
	}

	public String getEditName() {
		return (String) getContentDescription();
	}

	public void setEditName(String editName) {
		tryInitEditName(editName);
	}

	public Drawable getClearDrawable() {
		return clearDrawable;
	}

	public void setClearDrawable(Drawable clearDrawable) {
		tryInitClearDrawable(clearDrawable);
	}
}