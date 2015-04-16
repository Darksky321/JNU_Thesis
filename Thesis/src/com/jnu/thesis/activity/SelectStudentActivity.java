package com.jnu.thesis.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.bean.StudentBean;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.util.HttpUtil;
import com.jnu.thesis.view.FinishListener;
import com.jnu.thesis.view.SelectStudentListViewAdapter;

public class SelectStudentActivity extends Activity {

	private ImageView imageViewBack;
	private ImageView logo;
	private TextView textViewTitle;
	private Button buttonSubmit;
	private ListView listView;
	private SelectStudentListViewAdapter adapter;
	private List<StudentBean> students;
	private List<StudentBean> formerList;
	private Context context;

	private Thread stuListThread;
	private Thread sureThread;
	private ThesisBean thesis;
	private int getThesisNo;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch (msg.what) {
			case 1:
				String s = (String) msg.obj;
				try {
					JSONObject jo = new JSONObject(s);
					JSONArray ja;
					ja = jo.getJSONArray("list1");
					for (int i = 0; i < ja.length(); ++i) {
						students.add(StudentBean.createFromJSON(ja
								.getJSONObject(i)));
					}
					ja = jo.getJSONArray("list2");
					for (int i = 0; i < ja.length(); ++i) {
						students.add(StudentBean.createFromJSON(ja
								.getJSONObject(i)));
					}
					ja = jo.getJSONArray("list3");
					for (int i = 0; i < ja.length(); ++i) {
						students.add(StudentBean.createFromJSON(ja
								.getJSONObject(i)));
					}
					adapter.setData(students);
					if (thesis.getStatus() == 1) {
						ja = jo.getJSONArray("formerList");
						for (int i = 0; i < ja.length(); ++i) {
							formerList.add(StudentBean.createFromJSON(ja
									.getJSONObject(i)));
						}
						AlertDialog.Builder builder = new AlertDialog.Builder(
								SelectStudentActivity.this);
						StringBuffer sb = new StringBuffer();
						sb.append("已选学生：\n");
						for (StudentBean stb : formerList)
							sb.append("\n").append(stb.getName());
						builder.setMessage(sb.toString());
						builder.setPositiveButton("继续", null);
						builder.setNegativeButton("放弃", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO 自动生成的方法存根
								dialog.dismiss();
								SelectStudentActivity.this.finish();
							}
						});
						builder.show();
					}

				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Toast.makeText(SelectStudentActivity.this, "数据获取失败",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 0:
				Toast.makeText(SelectStudentActivity.this, "数据获取失败",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				String str = (String) msg.obj;
				JSONObject jo;
				try {
					jo = new JSONObject(str);
					if (jo.getString("result").equals("success")) {
						Toast.makeText(SelectStudentActivity.this, "提交成功",
								Toast.LENGTH_SHORT).show();
						SelectStudentActivity.this.finish();
					}
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Toast.makeText(SelectStudentActivity.this, "提交失败",
							Toast.LENGTH_SHORT).show();
				}

				break;
			case 4:
				Toast.makeText(SelectStudentActivity.this, "提交失败",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_student);
		context = this;
		imageViewBack = (ImageView) findViewById(R.id.imageView_back);
		logo = (ImageView) findViewById(R.id.img);
		textViewTitle = (TextView) findViewById(R.id.textView_title);
		listView = (ListView) findViewById(R.id.listView_select_student);
		buttonSubmit = (Button) findViewById(R.id.button_submit);

		students = new ArrayList<StudentBean>();
		formerList = new ArrayList<StudentBean>();

		Intent intent = getIntent();
		thesis = intent.getParcelableExtra("thesis");
		getThesisNo = intent.getIntExtra("getThesisNo", -1);

		textViewTitle.setText(thesis.getName());
		FinishListener finishListener = new FinishListener(this);
		imageViewBack.setOnClickListener(finishListener);
		logo.setOnClickListener(finishListener);
		List<String> names = new ArrayList<String>();
		List<String> nos = new ArrayList<String>();
		adapter = new SelectStudentListViewAdapter(names, nos, this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				adapter.checkChange(position);
				adapter.notifyDataSetChanged();
			}
		});

		buttonSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				int count = adapter.getCheckedCount();
				if (count != thesis.getCount()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setMessage("请选择" + thesis.getCount() + "个学生");
					builder.setIcon(android.R.drawable.ic_dialog_alert);
					builder.setPositiveButton("确定", null);
					builder.show();
				} else {
					if (sureThread == null || !sureThread.isAlive()) {
						sureThread = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO 自动生成的方法存根
								HttpUtil httpUtil = new HttpUtil();
								Map<String, String> para = new HashMap<String, String>();
								StringBuffer sb = new StringBuffer();
								for (int i = 0; i < adapter.getCheckedNos()
										.size(); ++i) {
									if (i == 0)
										sb.append(adapter.getCheckedNos()
												.get(i));
									else
										sb.append("&teacherfinalpick1=")
												.append(adapter.getCheckedNos()
														.get(i));
								}
								para.put("teacherfinalpick1", sb.toString());
								try {
									String s = httpUtil.doPost(Parameter.host
											+ Parameter.teacherSure, para);
									Message msg = Message.obtain();
									msg.what = 3;
									msg.obj = s;
									handler.sendMessage(msg);
								} catch (Exception e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
									Message msg = Message.obtain();
									msg.what = 4;
									msg.obj = 4;
									handler.sendMessage(msg);
								}
							}

						});
						sureThread.start();
					} else {
						Toast.makeText(SelectStudentActivity.this, "正在提交",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		stuListThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				Map<String, String> para = new HashMap<String, String>();
				para.put("getThesisNo", getThesisNo + "");
				HttpUtil httpUtil = new HttpUtil();
				try {
					String s = httpUtil.doGet(Parameter.host
							+ Parameter.teacherSelect, para);
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = s;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = 0;
					handler.sendMessage(msg);
				}
			}

		});
		stuListThread.start();
	}
}
