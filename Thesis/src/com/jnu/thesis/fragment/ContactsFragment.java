package com.jnu.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.activity.NotificationActivity;
import com.jnu.thesis.bean.NotificationBean;
import com.jnu.thesis.dao.NotificationDao;
import com.jnu.thesis.dao.impl.NotificationDaoImpl;
import com.jnu.thesis.view.MessageListViewAdapter;

public class ContactsFragment extends Fragment {

	private ListView listViewMessage;
	private MessageListViewAdapter adapter;
	/**
	 * ������Ϣ����
	 */
	private MsgReceiver updateListViewReceiver;
	private static Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_contacts, container, false);
		context = getActivity();
		initView(v);
		NotificationDao dao = NotificationDaoImpl.getInstance(getActivity()
				.getApplicationContext());
		// List<NotificationBean> messages = dao.findAllNotifications(Parameter
		// .getCurrentUser());
		List<NotificationBean> messages = dao.findAllNotifications();
		adapter = new MessageListViewAdapter(messages, getActivity());
		listViewMessage.setAdapter(adapter);
		/**
		 * ����鿴����
		 */
		listViewMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO �Զ����ɵķ������
				NotificationBean msg = adapter.getMessages().get(position);
				Intent intent = new Intent();
				intent.putExtra("message", msg);
				intent.setClass(getActivity(), NotificationActivity.class);
				startActivity(intent);
			}
		});

		// ��ʼ���㲥������
		updateListViewReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.jnu.thesis.activity.UPDATE_LISTVIEW");
		getActivity().registerReceiver(updateListViewReceiver, intentFilter);
		return v;
	}

	@Override
	public void onDestroy() {
		// TODO �Զ����ɵķ������
		getActivity().unregisterReceiver(updateListViewReceiver);
		super.onDestroy();
	}

	private void initView(View v) {
		/**
		 * ȫѡ��ť
		 */
		v.findViewById(R.id.button_check).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO �Զ����ɵķ������
						if (adapter.checkedAll()) {
							adapter.unCheckAll();
						} else {
							adapter.checkAll();
						}
					}
				});

		/**
		 * ɾ�����ݿ��м�¼
		 */
		v.findViewById(R.id.button_delete).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO �Զ����ɵķ������
						AlertDialog.Builder builder = new AlertDialog.Builder(
								getActivity());
						builder.setTitle(R.string.button_select);
						builder.setMessage("ȷ��Ҫɾ����");
						builder.setIcon(android.R.drawable.ic_dialog_alert);
						builder.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO �Զ����ɵķ������
										NotificationDao dao = NotificationDaoImpl
												.getInstance(getActivity()
														.getApplicationContext());
										List<NotificationBean> msgs = adapter
												.getMessages();
										List<Boolean> check = adapter
												.getCheck();
										List<String> checkedItems = new ArrayList<String>();
										for (int i = 0; i < check.size(); i++) {
											if (check.get(i) == true) {
												checkedItems.add(msgs.get(i)
														.getId() + "");
											}
										}
										int count = 0;
										for (String s : checkedItems) {
											if (dao.delete(s))
												++count;
										}
										Toast.makeText(
												getActivity(),
												count + "/"
														+ checkedItems.size()
														+ " ɾ���ɹ�",
												Toast.LENGTH_SHORT).show();
										notifyRefresh();
									}
								});
						builder.setNegativeButton("ȡ��", null);
						builder.show();
					}
				});

		listViewMessage = (ListView) v.findViewById(R.id.listView_message);
	}

	/**
	 * ���ݸ��ĺ�����б�
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