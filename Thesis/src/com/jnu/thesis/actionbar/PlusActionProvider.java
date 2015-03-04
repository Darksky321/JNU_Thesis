package com.jnu.thesis.actionbar;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.jnu.thesis.R;


public class PlusActionProvider extends ActionProvider
{
	private Context context ;
	//构造方法
	public PlusActionProvider(Context context) {
		super(context);
		this.context = context ;
	}

	@Override
	public View onCreateActionView() {
		return null;
	}
	
	@Override
	public void onPrepareSubMenu(SubMenu submenu)
	{
		submenu.clear();
		//增加子菜单并设置点击事件监听器
		submenu.add("发起群聊").setIcon(R.drawable.action_group_chat).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(context, "您点击了“发起群聊”选项", 5000).show();
				return true;
			}
		});
		
		submenu.add("添加朋友").setIcon(R.drawable.action_add_contacts).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(context, "您点击了“添加朋友”选项", 5000).show();
				return true;
			}
		});
		
		submenu.add("扫一扫").setIcon(R.drawable.action_scan_qr_code).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(context, "您点击了“扫一扫”选项", 5000).show();
				return true;
			}
		});
		
		submenu.add("意见反馈").setIcon(R.drawable.action_feedback).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(context, "您点击了“意见反馈”选项", 5000).show();
				return true;
			}
		});
		
	}
	
	@Override
	public boolean hasSubMenu()
	{
		return true;
	}
	
}