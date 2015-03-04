package com.jnu.thesis.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jnu.thesis.R;
import com.jnu.thesis.fragment.ChatsFragment;
import com.jnu.thesis.fragment.ContactsFragment;
import com.jnu.thesis.fragment.DiscoverFragment;
import com.jnu.thesis.fragment.MeFragment;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 界面初始函数，用来获取定义的各控件对应的ID
		InitView();
		// ViewPager初始化函数
		InitViewPager();
		// 信鸽初始化
		InitXinge();
	}

	public void InitXinge() {
		// 开启logcat输出，方便debug，发布时请关闭
		// XGPushConfig.enableDebug(this, true);
		// 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(),
		// XGIOperateCallback)带callback版本
		// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
		// 具体可参考详细的开发指南
		// 传递的参数为ApplicationContext
		Context context = getApplicationContext();
		XGPushManager.registerPush(context);

		// 2.36（不包括）之前的版本需要调用以下2行代码
		Intent service = new Intent(context, XGPushService.class);
		context.startService(service);

		// 其它常用的API：
		// 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account,
		// XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
		// 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
		// 反注册（不再接收消息）：unregisterPush(context)
		// 设置标签：setTag(context, tagName)
		// 删除标签：deleteTag(context, tagName)
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

		Fragment chatsFragment = new ChatsFragment();
		Fragment contactsFragment = new ContactsFragment();
		Fragment discoverFragment = new DiscoverFragment();
		Fragment meFragment = new MeFragment();

		// 将各Fragment加入数组中
		fragmentList.add(chatsFragment);
		fragmentList.add(contactsFragment);
		fragmentList.add(discoverFragment);
		fragmentList.add(meFragment);

		// 设置ViewPager的设配器
		main_viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),
				fragmentList));
		// 当前为第一个页面
		main_viewPager.setCurrentItem(0);
		// ViewPager的页面改变监听器
		main_viewPager.setOnPageChangeListener(new MyListner());
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

	public class MyListner implements OnPageChangeListener {

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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
}