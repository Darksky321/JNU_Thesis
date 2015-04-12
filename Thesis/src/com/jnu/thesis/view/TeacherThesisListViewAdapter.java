package com.jnu.thesis.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.ThesisBean;

public class TeacherThesisListViewAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<String> theses;

	public TeacherThesisListViewAdapter(Activity activity,
			ArrayList<String> theses) {
		super();
		this.activity = activity;
		this.theses = theses;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return theses.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return theses.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = (View) LayoutInflater.from(activity).inflate(
					R.layout.view_t_thesis, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(theses.get(position));
		return convertView;
	}

	private class ViewHolder {
		public TextView textView;
	}

	public void setData(List<ThesisBean> thesisBeans) {
		theses = new ArrayList<String>();
		for (ThesisBean tb : thesisBeans) {
			theses.add(tb.getName());
		}
	}
}
