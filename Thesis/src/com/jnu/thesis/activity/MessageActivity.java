package com.jnu.thesis.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jnu.thesis.R;
import com.jnu.thesis.view.FinishListener;

public class MessageActivity extends Activity {

	TextView textViewTitle;
	TextView textViewContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		initView();
	}

	private void initView() {
		textViewTitle = (TextView) findViewById(R.id.textView_msg_title);
		textViewContent = (TextView) findViewById(R.id.textView_msg_content);
		TextView textViewLabel = (TextView) findViewById(R.id.textView_title);
		textViewLabel.setText("¡Ù—‘œÍ«È");
		findViewById(R.id.img).setOnClickListener(new FinishListener(this));
		findViewById(R.id.imageView_back).setOnClickListener(
				new FinishListener(this));
	}
}
