package com.jnu.thesis.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.MessageBean;
import com.jnu.thesis.view.FinishListener;
import com.jnu.thesis.view.MessageListAdapter;

public class ChatActivity extends Activity {

	private ListView listView;
	private MessageListAdapter adapter;
	private ImageButton buttonSend;
	private EditText editText;

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
		findViewById(R.id.img).setOnClickListener(new FinishListener(this));
		findViewById(R.id.imageView_back).setOnClickListener(
				new FinishListener(this));
		listView = (ListView) findViewById(R.id.list_chat);
		buttonSend = (ImageButton) findViewById(R.id.button_send);
		editText = (EditText) findViewById(R.id.editText);
		buttonSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				MessageBean msg = new MessageBean();
				msg.setContent(editText.getText().toString());
				msg.setFromName("me");
				adapter.addMessage(msg);
				editText.setText("");
			}
		});
	}
}
