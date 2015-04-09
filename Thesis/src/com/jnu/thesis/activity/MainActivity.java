package com.jnu.thesis.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.jnu.thesis.R;
import com.jnu.thesis.fragment.ContactsFragment;
import com.jnu.thesis.fragment.DiscoverFragment;
import com.jnu.thesis.fragment.MeFragment;
import com.jnu.thesis.fragment.ThesisFragment;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {
	// ViewPager控件
	private ViewPager main_viewPager;
	// RadioGroup控件
	private RadioGroup main_tab_RadioGroup;
	// RadioButton控件
	private RadioButton radio_chats, radio_contacts, radio_discover, radio_me;
	// 类型为Fragment的动态数组
	private ArrayList<Fragment> fragmentList;
	// 点击返回键时间
	private Long clickTime = 0L;

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		Log.i("mytest", "MainResume:" + this.getTaskId());
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("mytest", "Intent to Main: " + getIntent().toString());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 界面初始函数，用来获取定义的各控件对应的ID
		InitView();
		// ViewPager初始化函数
		InitViewPager();
	}

	public void InitView() {
		main_tab_RadioGroup = (RadioGroup) findViewById(R.id.main_tab_RadioGroup);

		radio_chats = (RadioButton) findViewById(R.id.radio_chats);
		radio_contacts = (RadioButton) findViewById(R.id.radio_contacts);
		radio_discover = (RadioButton) findViewById(R.id.radio_discover);
		radio_me = (RadioButton) findViewById(R.id.radio_me);

		main_tab_RadioGroup.setOnCheckedChangeListener(this);
	}

	public void InitViewPager() {
		main_viewPager = (ViewPager) findViewById(R.id.main_ViewPager);

		fragmentList = new ArrayList<Fragment>();

		Fragment thesisFragment = new ThesisFragment();
		Fragment contactsFragment = new ContactsFragment();
		Fragment discoverFragment = new DiscoverFragment();
		Fragment meFragment = new MeFragment();

		// 将各Fragment加入数组中
		fragmentList.add(thesisFragment);
		fragmentList.add(contactsFragment);
		fragmentList.add(discoverFragment);
		fragmentList.add(meFragment);

		// 设置ViewPager的设配器
		main_viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),
				fragmentList));
		// 当前为第一个页面
		main_viewPager.setCurrentItem(0);
		// ViewPager的页面改变监听器
		main_viewPager.setOnPageChangeListener(new MyListener());
		// 设置fragment缓存
		main_viewPager.setOffscreenPageLimit(fragmentList.size());
	}

	public class MyAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyAdapter(FragmentManager fm, ArrayList<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}

	public class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			// 获取当前页面用于改变对应RadioButton的状态
			int current = main_viewPager.getCurrentItem();
			switch (current) {
			case 0:
				main_tab_RadioGroup.check(R.id.radio_chats);
				break;
			case 1:
				main_tab_RadioGroup.check(R.id.radio_contacts);
				break;
			case 2:
				main_tab_RadioGroup.check(R.id.radio_discover);
				break;
			case 3:
				main_tab_RadioGroup.check(R.id.radio_me);
				break;
			}
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
		// 获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页
		int current = 0;
		switch (CheckedId) {
		case R.id.radio_chats:
			current = 0;
			break;
		case R.id.radio_contacts:
			current = 1;
			break;
		case R.id.radio_discover:
			current = 2;
			break;
		case R.id.radio_me:
			current = 3;
			break;
		}
		if (main_viewPager.getCurrentItem() != current) {
			main_viewPager.setCurrentItem(current);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if ((System.currentTimeMillis() - clickTime) > 1000) {
			Toast.makeText(getApplicationContext(), "再按一次返回键键退出程序",
					Toast.LENGTH_SHORT).show();
			clickTime = System.currentTimeMillis();
		} else {
			this.finish();
		}
	}
}