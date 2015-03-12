package com.jnu.thesis.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.ThesisBean;

public class EmbeddedListViewAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private List<String> group;
	private List<List<String>> child;

	public EmbeddedListViewAdapter(Activity activity, List<ThesisBean> theses) {
		super();
		this.activity = activity;
		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();
		for (ThesisBean tb : theses) {
			group.add(tb.getName());
			List<String> tmp = new ArrayList<String>();
			tmp.add(tb.getTeacherName());
			tmp.add(tb.getCount() + "");
			tmp.add(tb.getDetail());
			child.add(tmp);
		}
	}

	@Override
	public int getGroupCount() {
		// TODO 自动生成的方法存根
		return group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO 自动生成的方法存根
		return child.get(0).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO 自动生成的方法存根
		return group.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO 自动生成的方法存根
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO 自动生成的方法存根
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		GroupHolder groupHolder = null;
		if (convertView == null) {
			activity.getLayoutInflater();
			convertView = (View) LayoutInflater.from(activity).inflate(
					R.layout.view_thesis_group, parent, false);
			groupHolder = new GroupHolder();
			groupHolder.textViewGroup = (TextView) convertView
					.findViewById(R.id.textViewGroup);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		groupHolder.textViewGroup.setText(group.get(groupPosition));
		// 展开ExpandableListView时文字滚动
		if (isExpanded)
			groupHolder.textViewGroup.setSelected(true);
		else
			groupHolder.textViewGroup.setSelected(false);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ChildHolder childHolder = null;
		if (convertView == null) {
			activity.getLayoutInflater();
			convertView = (View) LayoutInflater.from(activity).inflate(
					R.layout.view_thesis_child, parent, false);
			childHolder = new ChildHolder();
			childHolder.textViewChild = (TextView) convertView
					.findViewById(R.id.textViewChild);
			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		String str = "";
		if (childPosition == 0)
			str = "导师：";
		else if (childPosition == 1)
			str = "人数：";
		else if (childPosition == 2)
			str = "详情：";
		childHolder.textViewChild.setText(str
				+ child.get(groupPosition).get(childPosition));
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return false;
	}

	class GroupHolder {
		public TextView textViewGroup;
	}

	class ChildHolder {
		public TextView textViewChild;
	}

}
