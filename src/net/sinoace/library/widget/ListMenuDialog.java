package net.sinoace.library.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sinoace.library.utils.AndroidUtils;
import net.sinoace.library.utils.ArrayUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 列表时菜单对话框
 */
public class ListMenuDialog extends AlertDialog {
	private String[] items;
	private Context context;
	private OnItemClickListener onItemClickListener;
	private int layoutResId, listViewId;
	private ListView listView;
	
	public ListMenuDialog(Context context, int layoutResId, int listViewId, String... items) {
		super(context);
		this.context = context;
		this.layoutResId = layoutResId;
		this.listViewId = listViewId;
		this.items = items;
	}
	
	public ListMenuDialog(Context context, int theme, int layoutResId, int listViewId, String... items) {
		super(context, theme);
		this.context = context;
		this.layoutResId = layoutResId;
		this.listViewId = listViewId;
		this.items = items;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	setContentView(layoutResId);
    	listView = (ListView) findViewById(listViewId);
		
    	List<Map<String, String>> listInfoMap = new ArrayList<Map<String, String>>();
		for(String item : items){
			Map<String, String> info = new HashMap<String, String>();
			info.put("text", item);
			listInfoMap.add(info);
		}
		listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismiss();
				if(onItemClickListener != null){
					onItemClickListener.onItemClick(parent, view, position, id);
				}
			}
		});
		
		//设置窗体显示位置
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.y = AndroidUtils.getScreenSize(getContext()).getHeight() - lp.height;
		window.setAttributes(lp);
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String... items) {
		this.items = items;
	}

	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public int getLayoutResId() {
		return layoutResId;
	}

	public void setLayoutResId(int layoutResId) {
		this.layoutResId = layoutResId;
	}

	public int getListViewId() {
		return listViewId;
	}

	public void setListViewId(int listViewId) {
		this.listViewId = listViewId;
	}
	
	/**
	 * 设置窗体显示动画
	 * @param styleResId 样式资源ID
	 */
	public void setWindowShowAnim(int styleResId){
		getWindow().setWindowAnimations(styleResId);
	}
}