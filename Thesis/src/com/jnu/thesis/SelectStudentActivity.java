package com.jnu.thesis;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.thesis.bean.StudentBean;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.view.FinishListener;

public class SelectStudentActivity extends Activity {

	private ImageView imageViewBack;
	private ImageView logo;
	private TextView textViewTitle;
	private Button buttonSubmit;
	private ListView listView;
	private ThesisBean thesis;
	private List<StudentBean> students;
	private List<String> studentsString;
	private Context context;

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
		Intent intent = getIntent();
		thesis = intent.getParcelableExtra("thesis");
		Log.i("mytest", thesis.toString());

		textViewTitle.setText(thesis.getName());
		FinishListener finishListener = new FinishListener(this);
		imageViewBack.setOnClickListener(finishListener);
		logo.setOnClickListener(finishListener);
		students = getStudents();
		studentsString = new ArrayList<String>();
		for (StudentBean s : students) {
			String str = s.getNo() + "\n" + s.getName();
			studentsString.add(str);
		}
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				studentsString));
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		buttonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				int size = listView.getCount();
				int count = 0;
				List<Integer> selected = new ArrayList<Integer>();
				for (int i = 0; i < size; i++) {
					if (listView.isItemChecked(i)) {
						count++;
						selected.add(i);
					}
				}
				if (count != thesis.getCount()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setMessage("��ѡ��" + thesis.getCount() + "��ѧ��");
					builder.setIcon(android.R.drawable.ic_dialog_alert);
					builder.setPositiveButton("ȷ��", null);
					builder.show();
				} else {
					Toast.makeText(context, "ѡ��ͷ��", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private List<StudentBean> getStudents() {
		List<StudentBean> students = new ArrayList<StudentBean>();
		students.add(new StudentBean("2011051682", "��ˡ�˹��"));
		students.add(new StudentBean("1132123122", "����ѡ��"));
		students.add(new StudentBean("1231313422", "���ֱ�ѡ��"));
		students.add(new StudentBean("3464564655", "ѡ�Ұ�"));
		students.add(new StudentBean("2332556554", "������"));
		students.add(new StudentBean("3434532232", "���Ұ�"));
		students.add(new StudentBean("7676644434", "ɵ��"));
		students.add(new StudentBean("3534656755", "ѡ�Ҳ�"));
		students.add(new StudentBean("2323493423", "ѡ�Ұ�"));
		students.add(new StudentBean("1213678976", "�ö���"));
		return students;
	}
}
