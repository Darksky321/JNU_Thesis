package com.jnu.thesis.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.util.HttpUtil;
import com.jnu.thesis.view.FinishListener;

public class EditThesisActivity extends Activity {

	private static final int SUBMIT_FAIL = 0;
	private static final int SUBMIT_SUCCESS = 1;

	private EditText editTextName;
	private EditText editTextCount;
	private EditText editTextDetail;
	private ImageButton buttonSend;

	private Activity context;
	private Thread editThread;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch (msg.what) {
			case SUBMIT_FAIL:
				Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
				break;
			case SUBMIT_SUCCESS:
				Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
				context.finish();
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_thesis);
		context = this;
		initView();
		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		if (type != null && type.equals("edit")) {
			String name = intent.getStringExtra("name");
			String count = intent.getStringExtra("count");
			String detail = intent.getStringExtra("detail");
			if (name != null && count != null && detail != null) {
				editTextName.setText(name);
				editTextCount.setText(count);
				editTextDetail.setText(detail);
			}
		}
	}

	private void initView() {
		findViewById(R.id.img).setOnClickListener(new FinishListener(this));
		findViewById(R.id.imageView_back).setOnClickListener(
				new FinishListener(this));
		editTextName = (EditText) findViewById(R.id.editText_thesisName);
		editTextCount = (EditText) findViewById(R.id.editText_count);
		editTextDetail = (EditText) findViewById(R.id.editText_detail);
		buttonSend = (ImageButton) findViewById(R.id.button_send);

		buttonSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (isValid()) {
					if (editThread != null && editThread.isAlive()) {
						Toast.makeText(EditThesisActivity.this, "正在提交...",
								Toast.LENGTH_SHORT).show();
					} else {
						editThread = new Thread(new EditThesisRunnable());
						editThread.start();
					}
				}
			}
		});
	}

	private boolean isValid() {
		if (editTextCount.getText().toString().trim().equals("")
				|| editTextName.getText().toString().trim().equals("")
				|| editTextDetail.getText().toString().trim().equals("")) {
			return false;
		}
		return true;
	}

	private class EditThesisRunnable implements Runnable {

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			HttpUtil httpUtil = new HttpUtil();
			Map<String, String> para = new HashMap<String, String>();
			para.put("", editTextName.getText().toString());
			para.put("", editTextDetail.getText().toString());
			para.put("", editTextCount.getText().toString());
			try {
				String s = httpUtil.doPost(Parameter.host, para);
				JSONObject jo = new JSONObject(s);
				String res = jo.getString("result");
				Message msg = Message.obtain();
				if (res.equals("success"))
					msg.what = SUBMIT_SUCCESS;
				else if (res.equals("fail"))
					msg.what = SUBMIT_FAIL;
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				Message msg = Message.obtain();
				msg.what = SUBMIT_FAIL;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}

	}
}
