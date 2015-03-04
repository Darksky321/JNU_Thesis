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
	//���췽��
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
		//�����Ӳ˵������õ���¼�������
		submenu.add("����Ⱥ��").setIcon(R.drawable.action_group_chat).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(context, "������ˡ�����Ⱥ�ġ�ѡ��", 5000).show();
				return true;
			}
		});
		
		submenu.add("�������").setIcon(R.drawable.action_add_contacts).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(context, "������ˡ�������ѡ�ѡ��", 5000).show();
				return true;
			}
		});
		
		submenu.add("ɨһɨ").setIcon(R.drawable.action_scan_qr_code).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(context, "������ˡ�ɨһɨ��ѡ��", 5000).show();
				return true;
			}
		});
		
		submenu.add("�������").setIcon(R.drawable.action_feedback).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(context, "������ˡ����������ѡ��", 5000).show();
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