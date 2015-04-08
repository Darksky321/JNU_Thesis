package com.jnu.thesis.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.MessageBean;
import com.jnu.thesis.dao.MessageDao;
import com.jnu.thesis.dao.impl.MessageDaoImpl;
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
		findViewById(R.id.button_delete).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						AlertDialog.Builder builder = new AlertDialog.Builder(
								MessageActivity.this);
						builder.setTitle(R.string.button_select);
						builder.setMessage("确定要删除吗？");
						builder.setIcon(android.R.drawable.ic_dialog_alert);
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO 自动生成的方法存根
										MessageDao dao = MessageDaoImpl
												.getInstance(getApplicationContext());
										if (dao.delete(msg.getId() + ""))
											Toast.makeText(
													MessageActivity.this,
													"删除成功", Toast.LENGTH_SHORT)
													.show();
										else
											Toast.makeText(
													MessageActivity.this,
													"删除失败", Toast.LENGTH_SHORT)
													.show();
										Intent intent = new Intent(
												"com.jnu.thesis.activity.UPDATE_LISTVIEW");
										sendBroadcast(intent);
										MessageActivity.this.finish();
									}
								});
						builder.setNegativeButton("取消", null);
						builder.show();
					}
				});
	}
}
