package net.sinoace.library.widget;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 滑动视图适配器
 */
public class ViewPagerAdapter extends PagerAdapter {

	private List<View> viewList;
	
	public ViewPagerAdapter(List<View> viewList){
		setViewList(viewList);
	} 
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(viewList.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(viewList.get(position), 0);
		return viewList.get(position);
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	public List<View> getViewList() {
		return viewList;
	}

	public void setViewList(List<View> viewList) {
		this.viewList = viewList;
	}
}