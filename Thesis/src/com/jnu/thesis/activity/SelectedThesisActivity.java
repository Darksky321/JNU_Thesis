package com.jnu.thesis.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.view.FinishListener;

public class SelectedThesisActivity extends Activity {

	private TextView firstTitle;
	private TextView firstContent;
	private TextView secondTitle;
	private TextView secondContent;
	private TextView thirdTitle;
	private TextView thirdContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selected_thesis);
		initView();
		Intent intent = getIntent();
		List<ThesisBean> myList = intent.getParcelableArrayListExtra("myList");
		if (myList != null && !myList.isEmpty()) {
			ThesisBean tb = myList.get(0);
			firstTitle.setText(tb.getName());
			StringBuffer sb = new StringBuffer();
			sb.append("导师：").append(tb.getTeacherName()).append("\n人数：")
					.append(tb.getCount()).append("\n详情：")
					.append(tb.getDetail()).append("\n发布时间：")
					.append(tb.getPostTime());
			firstContent.setText(sb.toString());

			tb = myList.get(1);
			secondTitle.setText(tb.getName());
			sb = new StringBuffer();
			sb.append("导师：").append(tb.getTeacherName()).append("\n人数：")
					.append(tb.getCount()).append("\n详情：")
					.append(tb.getDetail()).append("\n发布时间：")
					.append(tb.getPostTime());
			secondContent.setText(sb.toString());

			tb = myList.get(2);
			thirdTitle.setText(tb.getName());
			sb = new StringBuffer();
			sb.append("导师：").append(tb.getTeacherName()).append("\n人数：")
					.append(tb.getCount()).append("\n详情：")
					.append(tb.getDetail()).append("\n发布时间：")
					.append(tb.getPostTime());
			thirdContent.setText(sb.toString());
		}
	}

	private void initView() {
		findViewById(R.id.img).setOnClickListener(new FinishListener(this));
		findViewById(R.id.imageView_back).setOnClickListener(
				new FinishListener(this));
		firstTitle = (TextView) findViewById(R.id.firstTitle);
		firstContent = (TextView) findViewById(R.id.firstContent);
		secondTitle = (TextView) findViewById(R.id.secondTitle);
		secondContent = (TextView) findViewById(R.id.secondContent);
		thirdTitle = (TextView) findViewById(R.id.thirdTitle);
		thirdContent = (TextView) findViewById(R.id.thirdContent);
	}
}
