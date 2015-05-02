package com.jnu.thesis.fragment;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.activity.EditNotificationActivity;
import com.jnu.thesis.activity.NotificationActivity;
import com.jnu.thesis.bean.NotificationBean;
import com.jnu.thesis.dao.NotificationDao;
import com.jnu.thesis.dao.impl.NotificationDaoImpl;
import com.jnu.thesis.view.MessageListViewAdapter;

public class TeacherContactsFragment extends Fragment {

	private ListView listViewMessage;
	private MessageListViewAdapter adapter;
	/**
	 * 监听消息接受
	 */
	private MsgReceiver updateListViewReceiver;
	private static Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_t_contacts, container,
				false);
		context = getActivity();
		initView(v);
		NotificationDao dao = NotificationDaoImpl.getInstance(getActivity()
				.getApplicationContext());
		List<NotificationBean> messages = dao.findAllNotifications();
		adapter = new MessageListViewAdapter(messages, getActivity());
		listViewMessage.setAdapter(adapter);
		/**
		 * 点击查看详情
		 */
		listViewMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				NotificationBean msg = adapter.getMessages().get(position);
				Intent intent = new Intent();
				intent.putExtra("message", msg);
				intent.setClass(getActivity(), NotificationActivity.class);
				startActivity(intent);
			}
		});

		// 初始化广播接收器
		updateListViewReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.jnu.thesis.activity.UPDATE_LISTVIEW");
		getActivity().registerReceiver(updateListViewReceiver, intentFilter);
		return v;
	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		getActivity().unregisterReceiver(updateListViewReceiver);
		super.onDestroy();
	}

	private void initView(View v) {
		v.findViewById(R.id.button_newNoti).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						Intent intent = new Intent();
						intent.setClass(getActivity(),
								EditNotificationActivity.class);
						getActivity().startActivity(intent);
					}
				});
		listViewMessage = (ListView) v.findViewById(R.id.listView_message);
	}

	/**
	 * 数据更改后更新列表
	 */
	public void notifyRefresh() {
		if (adapter != null) {
			NotificationDao dao = NotificationDaoImpl.getInstance(context
					.getApplicationContext());
			List<NotificationBean> msgs = dao.findAllNotifications(Parameter
					.getCurrentUser());
			adapter.refresh(msgs);
		}
	}

	public class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			notifyRefresh();
		}
	}
}