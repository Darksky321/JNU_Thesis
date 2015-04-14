package com.jnu.thesis.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.MessageBean;
import com.jnu.thesis.view.MessageListAdapter;

public class ChatActivity extends Activity {

	private ListView listView;
	private MessageListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		initView();
		List<MessageBean> msgList = new ArrayList<MessageBean>();
		MessageBean msg = new MessageBean();
		msg.setContent("hehe");
		msg.setFromName("me");
		msgList.add(msg);
		msg = new MessageBean();
		msg.setContent("dasfasdfdasfadfsadfadsfagasdfasdfagfgaddsfghjuytrdffghffafdasgrashsfdsadgasdfasdfsadgah");
		msg.setFromName("you");
		msgList.add(msg);
		msg = new MessageBean();
		msg.setFromName("it");
		msg.setContent("what the hey");
		msgList.add(msg);
		adapter = new MessageListAdapter(this, msgList);
		listView.setAdapter(adapter);
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.list_chat);
	}
}
