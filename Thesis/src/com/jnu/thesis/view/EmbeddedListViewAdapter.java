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
		// TODO �Զ����ɵķ������
		return group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO �Զ����ɵķ������
		return child.get(0).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO �Զ����ɵķ������
		return group.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO �Զ����ɵķ������
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO �Զ����ɵķ������
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO �Զ����ɵķ������
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO �Զ����ɵķ������
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
		// չ��ExpandableListViewʱ���ֹ���
		if (isExpanded)
			groupHolder.textViewGroup.setSelected(true);
		else
			groupHolder.textViewGroup.setSelected(false);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO �Զ����ɵķ������
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
			str = "��ʦ��";
		else if (childPosition == 1)
			str = "������";
		else if (childPosition == 2)
			str = "���飺";
		childHolder.textViewChild.setText(str
				+ child.get(groupPosition).get(childPosition));
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO �Զ����ɵķ������
		return false;
	}

	class GroupHolder {
		public TextView textViewGroup;
	}

	class ChildHolder {
		public TextView textViewChild;
	}

}
