package com.jnu.thesis;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jnu.thesis.bean.StudentBean;
import com.jnu.thesis.bean.ThesisBean;

public class SelectStudentActivity extends Activity {

	private ImageView imageViewBack;
	private TextView textViewTitle;
	private ListView listView;
	private List<StudentBean> students;
	private List<String> studentsString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_student);
		imageViewBack = (ImageView) findViewById(R.id.imageView_back);
		textViewTitle = (TextView) findViewById(R.id.textView_title);
		listView = (ListView) findViewById(R.id.listView_select_student);
		Intent intent = getIntent();
		ThesisBean thesis = intent.getParcelableExtra("thesis");
		Log.i("mytest", thesis.toString());

		textViewTitle.setText(thesis.getName());
		imageViewBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
		students = getStudents();
		studentsString = new ArrayList<String>();
		for (StudentBean s : students) {
			String str = s.getNo() + "\n" + s.getName();
			studentsString.add(str);
		}
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, studentsString));
	}

	private List<StudentBean> getStudents() {
		List<StudentBean> students = new ArrayList<StudentBean>();
		students.add(new StudentBean("2011051682", "达克·斯盖"));
		students.add(new StudentBean("1132123122", "辣鸡选我"));
		students.add(new StudentBean("1231313422", "有种别选我"));
		students.add(new StudentBean("3464564655", "选我啊"));
		return students;
	}
}
