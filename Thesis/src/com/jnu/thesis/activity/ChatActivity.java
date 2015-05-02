package com.jnu.thesis.activity;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.bean.MessageBean;
import com.jnu.thesis.dao.MessageDao;
import com.jnu.thesis.dao.impl.MessageDaoImpl;
import com.jnu.thesis.util.XingePush;
import com.jnu.thesis.view.FinishListener;
import com.jnu.thesis.view.MessageListAdapter;

public class ChatActivity extends Activity {

	private ListView listView;
	private MessageListAdapter adapter;
	private ImageButton buttonSend;
	private EditText editText;
	private ChatReceiver chatReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		initView();
		chatReceiver = new ChatReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.jnu.thesis.NEW_MSG");
		registerReceiver(chatReceiver, intentFilter);
		MessageDao dao = MessageDaoImpl.getInstance(this
				.getApplicationContext());
		List<MessageBean> msgList = dao.findUnRead();
		adapter = new MessageListAdapter(this, msgList);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		this.unregisterReceiver(chatReceiver);
		super.onDestroy();
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
				msg.setFromName(Parameter.getCurrentUserName());
				adapter.addMessage(msg);
				final String tmp = editText.getText().toString();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO 自动生成的方法存根
						XingePush.pushMessageByTag("tagForTest", tmp,
								Parameter.getCurrentUserName());
					}

				}).start();
				editText.setText("");
			}
		});
	}

	public class ChatReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals("com.jnu.thesis.NEW_MSG")) {
				String content = intent.getStringExtra("content");
				String fromName = intent.getStringExtra("fromName");
				if (fromName.equals(Parameter.getCurrentUserName()))
					return;
				MessageBean msg = new MessageBean();
				msg.setContent(content);
				msg.setFromName(fromName);
				adapter.addMessage(msg);
			}
		}
	}
}
