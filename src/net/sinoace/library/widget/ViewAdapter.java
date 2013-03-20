package net.sinoace.library.widget;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

/**
 * View适配器
 */
public class ViewAdapter extends MyBaseAdapter {

	private List<View> viewList;
	
	public ViewAdapter(List<View> viewList){
		this.viewList = viewList;
	}
	
	@Override
	public int getRealCount() {
		return viewList.size();
	}

	@Override
	public Object getItem(int position) {
		return viewList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getRealView(int realPosition, View convertView, ViewGroup parent) {
		return viewList.get(realPosition);
	}
}