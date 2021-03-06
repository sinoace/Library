package net.sinoace.library.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 视图工具箱
 */
public class ViewUtils {
	/**
	 * 默认透明度动画持续时间
	 */
	public static final long DEFAULT_ALPHA_ANIMATION_DURATION = 500;
	
	/**
	 * 获取一个LinearLayout
	 * @param context 上下文
	 * @param orientation 流向
	 * @param width 宽
	 * @param height 高
	 * @return LinearLayout
	 */
	public static LinearLayout createLinearLayout(Context context, int orientation, int width, int height){
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(orientation);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		return linearLayout;
	}
	
	/**
	 * 
	 * 获取一个LinearLayout
	 * @param context 上下文
	 * @param orientation 流向
	 * @param width 宽
	 * @param height 高
	 * @param weight 权重
	 * @return LinearLayout
	 */
	public static LinearLayout createLinearLayout(Context context, int orientation, int width, int height, int weight){
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(orientation);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
		return linearLayout;
	}
	
	/**
	 * 根据ListView的所有子项的高度设置其高度
	 * @param listView
	 */
	public static void setListViewHeightByAllChildrenViewHeight(ListView listView) {  
		ListAdapter listAdapter = listView.getAdapter();   
	    if (listAdapter != null) {  
	    	int totalHeight = 0;  
	    	for (int i = 0; i < listAdapter.getCount(); i++) {  
	    		View listItem = listAdapter.getView(i, null, listView);  
	    		listItem.measure(0, 0);  
	    		totalHeight += listItem.getMeasuredHeight();  
	    	}  
	    	
	    	ViewGroup.LayoutParams params = listView.getLayoutParams();  
	    	params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	    	((MarginLayoutParams)params).setMargins(10, 10, 10, 10);
	    	listView.setLayoutParams(params); 
	    }  
    }
	
	/**
	 * 给给定的视图注册长按提示监听器
	 * @param context 上下文
	 * @param view 给定的视图
	 * @param resourceId 提示内容的ID
	 */
	public static void setLongClickHintListener(final Context context, View view, final int resourceId){
		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AndroidUtils.toastS(context, resourceId);
				return true;
			}
		});
	}
	
	/**
	 * 给给定的图片按钮注册切换图片监听器
	 * @param imageButton 给定的图片按钮
	 * @param notStateImageResourceId 没有状态时显示的图片
	 * @param pressStateImageResourceId 按下时显示的图片
	 */
	public static void setImageButtonSwitchImageListener(final ImageButton imageButton, final int notStateImageResourceId, final int pressStateImageResourceId){
		imageButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
					case MotionEvent.ACTION_DOWN : 
						imageButton.setImageResource(pressStateImageResourceId);
						break;
					case MotionEvent.ACTION_UP : 
						imageButton.setImageResource(notStateImageResourceId);
						break;
				}
				return false;
			}
		});
	}
	
	/**
	 * 设置给定视图的高度
	 * @param view 给定的视图
	 * @param newHeight 新的高度
	 */
	public static void setViewHeight(View view, int newHeight){
		ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		layoutParams.height = newHeight; 
		view.setLayoutParams(layoutParams);
	}
	
	/**
	 * 将给定视图的高度增加一点
	 * @param view 给定的视图
	 * @param increasedAmount 增加多少
	 */
	public static void addViewHeight(View view, int increasedAmount){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.height += increasedAmount; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 设置给定视图的宽度
	 * @param view 给定的视图
	 * @param newWidth 新的宽度
	 */
	public static void setViewWidth(View view, int newWidth){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.width = newWidth; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 将给定视图的宽度增加一点
	 * @param view 给定的视图
	 * @param increasedAmount 增加多少
	 */
	public static void addViewWidth(View view, int increasedAmount){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.width += increasedAmount; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 获取流布局的底部外边距
	 * @param linearLayout
	 * @return
	 */
	public static int getLinearLayoutBottomMargin(LinearLayout linearLayout) {
		return ((LinearLayout.LayoutParams)linearLayout.getLayoutParams()).bottomMargin;
	}
	
	/**
	 * 设置流布局的底部外边距
	 * @param linearLayout
	 * @param newBottomMargin
	 */
	public static void setLinearLayoutBottomMargin(LinearLayout linearLayout, int newBottomMargin) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)linearLayout.getLayoutParams();
		lp.bottomMargin = newBottomMargin;
		linearLayout.setLayoutParams(lp);
	}
	
	/**
	 * 获取流布局的高度
	 * @param linearLayout
	 * @return
	 */
	public static int getLinearLayoutHiehgt(LinearLayout linearLayout) {
		return ((LinearLayout.LayoutParams)linearLayout.getLayoutParams()).height;
	}
	
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void invisibleViewByAlpha(final View view, long durationMillis, final AnimationListener animationListener){
		AlphaAnimation hiddenAlphaAnimation = AnimationUtils.getHiddenAlphaAnimation(durationMillis);
		hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationStart(animation);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationRepeat(animation);
				}
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.INVISIBLE);
				if(animationListener != null){
					animationListener.onAnimationEnd(animation);
				}
			}
		});
		view.startAnimation(hiddenAlphaAnimation);
	}
	
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 */
	public static void invisibleViewByAlpha(final View view, long durationMillis){
		invisibleViewByAlpha(view, durationMillis, null);
	}
	
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void invisibleViewByAlpha(final View view, final AnimationListener animationListener){
		invisibleViewByAlpha(view, DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void invisibleViewByAlpha(final View view){
		invisibleViewByAlpha(view, DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void goneViewByAlpha(final View view, long durationMillis, final AnimationListener animationListener){
		AlphaAnimation hiddenAlphaAnimation = AnimationUtils.getHiddenAlphaAnimation(durationMillis);
		hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationStart(animation);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationRepeat(animation);
				}
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
				if(animationListener != null){
					animationListener.onAnimationEnd(animation);
				}
			}
		});
		view.startAnimation(hiddenAlphaAnimation);
	}
	
	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 */
	public static void goneViewByAlpha(final View view, long durationMillis){
		goneViewByAlpha(view, durationMillis, null);
	}
	
	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void goneViewByAlpha(final View view, final AnimationListener animationListener){
		goneViewByAlpha(view, DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void goneViewByAlpha(final View view){
		goneViewByAlpha(view, DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void visibleViewByAlpha(final View view, long durationMillis, final AnimationListener animationListener){
		AlphaAnimation showAlphaAnimation = AnimationUtils.getShowAlphaAnimation(durationMillis);
		showAlphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationStart(animation);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationRepeat(animation);
				}
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.VISIBLE);
				if(animationListener != null){
					animationListener.onAnimationEnd(animation);
				}
			}
		});
		view.startAnimation(showAlphaAnimation);
	}
	
	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 */
	public static void visibleViewByAlpha(final View view, long durationMillis){
		visibleViewByAlpha(view, durationMillis, null);
	}
	
	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void visibleViewByAlpha(final View view, final AnimationListener animationListener){
		visibleViewByAlpha(view, DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void visibleViewByAlpha(final View view){
		visibleViewByAlpha(view, DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	/**
	 * 将一个编辑器和一个视图绑定，当编辑器的内容为空时隐藏视图，反之显示视图，并且点击视图的时候清空编辑器的内容，另外视图的显示或隐藏都会伴随透明度渐变动画
	 * @param editText
	 * @param clearView
	 */
	public static void editClearBindByAlpha(final EditText editText, final View clearView){
		if(editText.getEditableText().toString().length() > 0){
			if(clearView.getVisibility() != View.VISIBLE){
				visibleViewByAlpha(clearView);
			}
		}else{
			if(clearView.getVisibility() != View.INVISIBLE){
				invisibleViewByAlpha(clearView);
			}
		}
		
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().length() > 0){
					if(clearView.getVisibility() != View.VISIBLE && clearView.getTag() == null){
						clearView.setTag(false);
						visibleViewByAlpha(clearView, new Animation.AnimationListener() {
							public void onAnimationStart(Animation animation) {
								
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								clearView.setTag(null);
							}
						});
					}
				}else{
						clearView.clearAnimation();
						clearView.setTag(null);
						invisibleViewByAlpha(clearView);
				}
			}
		});
		
		clearView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editText.setText("");
			}
		});
	}
	
	/**
	 * 开启软键盘
	 * @param context 
	 * @param editText 接收文字输入的编辑器
	 */
	public static void openSoftKeyboard(Context context, EditText editText){
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}
	
	/**
	 * 关闭软键盘
	 * @param context
	 */
	public static void closeSoftKeyboard(Activity activity){
		try{
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			//如果软键盘已经开启
			if(inputMethodManager.isActive()){
				inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}