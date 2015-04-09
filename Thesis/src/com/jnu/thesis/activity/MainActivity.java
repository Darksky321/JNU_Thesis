package com.jnu.thesis.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.dao.UserDao;
import com.jnu.thesis.dao.impl.UserDaoImpl;
import com.jnu.thesis.fragment.ContactsFragment;
import com.jnu.thesis.fragment.DiscoverFragment;
import com.jnu.thesis.fragment.MeFragment;
import com.jnu.thesis.fragment.ThesisFragment;
import com.jnu.thesis.service.CallBack;
import com.jnu.thesis.service.LoginThread;
import com.jnu.thesis.util.XingeUtil;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_FAILED = 2;
	public static final int LOGIN_NOT_EXIST = 3;
	public static final int LOGIN_ERROR = 4;

	// ViewPager�ؼ�
	private ViewPager main_viewPager;
	// RadioGroup�ؼ�
	private RadioGroup main_tab_RadioGroup;
	// RadioButton�ؼ�
	// private RadioButton radio_chats, radio_contacts, radio_discover,
	// radio_me;
	// ����ΪFragment�Ķ�̬����
	private ArrayList<Fragment> fragmentList;

	// �û���Ϣ
	private String id;

	// ������ؼ�ʱ��
	private Long clickTime = 0L;
	private MainHandler handler = new MainHandler(MainActivity.this);

	@Override
	protected void onResume() {
		// TODO �Զ����ɵķ������
		Log.i("mytest", "MainResume:" + this.getTaskId());
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("mytest", "Intent to Main: " + getIntent().toString());
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		intent.setClass(MainActivity.this, LoginActivity.class);
		// ���Ÿ�����
		if (intent.getFlags() == 0x4020000) {
			UserDao dao = UserDaoImpl.getInstance(getApplicationContext());
			Map<String, String> user = dao.findAllUser();
			// ��ѧ����½��Ϣ
			if (user != null && !user.isEmpty()
					&& user.get("status").equals("1")) {
				id = user.get("id");
				Thread loginThread = new LoginThread(user.get("id"),
						user.get("password"), 1, new CallBack() {

							@Override
							public void onFinish(String ret, Exception e) {
								// TODO �Զ����ɵķ������
								Message msg = Message.obtain();
								if (e != null) {
									msg.what = LOGIN_ERROR;
									handler.sendMessage(msg);
								} else {
									try {
										JSONObject jResult;
										jResult = new JSONObject(ret);
										String result = jResult
												.getString("result");
										if (result.equals("success")) {
											msg.what = LOGIN_SUCCESS;
										} else if (result.equals("fail"))
											msg.what = LOGIN_FAILED;
										else if (result.equals("notexist"))
											msg.what = LOGIN_NOT_EXIST;
										else
											msg.what = LOGIN_ERROR;
										handler.sendMessage(msg);
									} catch (JSONException e1) {
										// TODO �Զ����ɵ� catch ��
										msg.what = LOGIN_ERROR;
										handler.sendMessage(msg);
										e1.printStackTrace();
									}
								}
							}
						});
				loginThread.start();
			} else {
				finish();
				startActivity(intent);
				return;
			}
		}
		setContentView(R.layout.activity_main);
		// �����ʼ������������ȡ����ĸ��ؼ���Ӧ��ID
		InitView();
		// ViewPager��ʼ������
		InitViewPager();
	}

	public void InitView() {
		main_tab_RadioGroup = (RadioGroup) findViewById(R.id.main_tab_RadioGroup);

		// radio_chats = (RadioButton) findViewById(R.id.radio_chats);
		// radio_contacts = (RadioButton) findViewById(R.id.radio_contacts);
		// radio_discover = (RadioButton) findViewById(R.id.radio_discover);
		// radio_me = (RadioButton) findViewById(R.id.radio_me);

		main_tab_RadioGroup.setOnCheckedChangeListener(this);
	}

	public void InitViewPager() {
		main_viewPager = (ViewPager) findViewById(R.id.main_ViewPager);

		fragmentList = new ArrayList<Fragment>();

		Fragment thesisFragment = new ThesisFragment();
		Fragment contactsFragment = new ContactsFragment();
		Fragment discoverFragment = new DiscoverFragment();
		Fragment meFragment = new MeFragment();

		// ����Fragment����������
		fragmentList.add(thesisFragment);
		fragmentList.add(contactsFragment);
		fragmentList.add(discoverFragment);
		fragmentList.add(meFragment);

		// ����ViewPager��������
		main_viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),
				fragmentList));
		// ��ǰΪ��һ��ҳ��
		main_viewPager.setCurrentItem(0);
		// ViewPager��ҳ��ı������
		main_viewPager.setOnPageChangeListener(new MyListener());
		// ����fragment����
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
			// ��ȡ��ǰҳ�����ڸı��ӦRadioButton��״̬
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
		// ��ȡ��ǰ��ѡ�е�RadioButton��ID�����ڸı�ViewPager�ĵ�ǰҳ
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
			Toast.makeText(getApplicationContext(), "�ٰ�һ�η��ؼ����˳�����",
					Toast.LENGTH_SHORT).show();
			clickTime = System.currentTimeMillis();
		} else {
			this.finish();
		}
	}

	private static class MainHandler extends Handler {
		private final WeakReference<MainActivity> mActivity;

		MainHandler(MainActivity mActivity) {
			this.mActivity = new WeakReference<MainActivity>(mActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO �Զ����ɵķ������
			MainActivity activity = mActivity.get();
			Intent intent = new Intent();
			intent.setClass(activity, LoginActivity.class);
			switch (msg.what) {
			case LOGIN_FAILED:
				activity.finish();
				activity.startActivity(intent);
				break;
			case LOGIN_SUCCESS:
				// �Ÿ�ע���˺�
				XingeUtil.regist(activity.getApplicationContext(),
						activity.getId());
				Parameter.setCurrentUser(activity.getId());
				Parameter.setStatus(1);
				break;
			case LOGIN_NOT_EXIST:
				Toast.makeText(activity, "�û�������", Toast.LENGTH_SHORT).show();
				activity.finish();
				activity.startActivity(intent);
				break;
			case LOGIN_ERROR:
				Toast.makeText(activity, "�������", Toast.LENGTH_SHORT).show();
				activity.finish();
				activity.startActivity(intent);
				break;
			}
			super.handleMessage(msg);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}