package com.jnu.thesis.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.StudentBean;

public class SelectStudentListViewAdapter extends BaseAdapter {

	private List<String> names;
	private List<String> nos;
	private Activity activity;
	private List<Boolean> check;

	public SelectStudentListViewAdapter(List<String> names, List<String> nos,
			Activity activity) {
		super();
		this.names = names;
		this.nos = nos;
		this.activity = activity;
		check = new ArrayList<Boolean>();
		for (int i = 0; i < names.size(); i++) {
			check.add(false);
		}
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return names.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return nos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = (View) LayoutInflater.from(activity).inflate(
					R.layout.view_select_student, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textViewName = (TextView) convertView
					.findViewById(R.id.textView_Name);
			viewHolder.textViewNo = (TextView) convertView
					.findViewById(R.id.textView_No);
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.checkBox);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textViewName.setText(names.get(position));
		viewHolder.textViewNo.setText(nos.get(position));
		viewHolder.checkBox.setChecked(check.get(position));
		viewHolder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				checkChange(position);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		public TextView textViewName;
		public TextView textViewNo;
		public CheckBox checkBox;
	}

	public void checkChange(int position) {
		check.set(position, !check.get(position));
	}

	public int getCheckedCount() {
		int i = 0;
		for (boolean c : check) {
			if (c == true) {
				i++;
			}
		}
		return i;
	}

	public void setData(List<StudentBean> students) {
		names = new ArrayList<String>();
		nos = new ArrayList<String>();
		check = new ArrayList<Boolean>();
		for (StudentBean s : students) {
			names.add(s.getName());
			nos.add(s.getNo());
			check.add(false);
		}
		notifyDataSetChanged();
	}

	public List<String> getCheckedNos() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < check.size(); ++i) {
			if (check.get(i)) {
				list.add(nos.get(i));
			}
		}
		return list;
	}
}
