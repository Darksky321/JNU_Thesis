package com.jnu.thesis.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.MessageBean;
import com.jnu.thesis.view.FinishListener;

public class MessageActivity extends Activity {

	private TextView textViewTitle;
	private TextView textViewName;
	private TextView textViewTime;
	private TextView textViewContent;
	private MessageBean msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		msg = getIntent().getParcelableExtra("message");
		Log.i("mytest", msg.toString());
		initView();
		textViewTitle.setText(msg.getTitle());
		textViewName.setText(msg.getFromName());
		textViewTime.setText(msg.getUpdate_time());
		textViewContent.setText(msg.getContent());
	}

	private void initView() {
		textViewTitle = (TextView) findViewById(R.id.textView_msg_title);
		textViewName = (TextView) findViewById(R.id.textView_msg_name);
		textViewTime = (TextView) findViewById(R.id.textView_msg_time);
		textViewContent = (TextView) findViewById(R.id.textView_msg_content);
		findViewById(R.id.img).setOnClickListener(new FinishListener(this));
		findViewById(R.id.imageView_back).setOnClickListener(
				new FinishListener(this));
	}
}
