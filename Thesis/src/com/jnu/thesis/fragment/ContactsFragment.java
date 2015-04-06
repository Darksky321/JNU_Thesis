package com.jnu.thesis.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.MessageBean;
import com.jnu.thesis.dao.MessageDao;
import com.jnu.thesis.dao.impl.MessageDaoImpl;
import com.jnu.thesis.view.MessageListViewAdapter;

public class ContactsFragment extends Fragment {

	private ListView listViewMessage;
	private List<MessageBean> messages;
	private static MessageListViewAdapter adapter;
	private static Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_contacts, null);
		context = getActivity();
		initView(v);
		MessageDao dao = MessageDaoImpl.getInstance(getActivity()
				.getApplicationContext());
		messages = dao.findAllMessage();
		adapter = new MessageListViewAdapter(messages, getActivity());
		listViewMessage.setAdapter(adapter);
		return v;
	}

	private void initView(View v) {
		v.findViewById(R.id.button_check).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根

					}
				});

		v.findViewById(R.id.button_delete).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根

					}
				});

		listViewMessage = (ListView) v.findViewById(R.id.listView_message);
	}

	public static void notifyRefresh() {
		if (adapter != null) {
			MessageDao dao = MessageDaoImpl.getInstance(context
					.getApplicationContext());
			List<MessageBean> msgs = dao.findAllMessage();
			adapter.refresh(msgs);
		}
	}
}