package com.jnu.thesis.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.ThesisBean;

public class EmbeddedListViewAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private List<String> group;
	private List<List<String>> child;
	private int first = -1;
	private int second = -1;
	private int third = -1;

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
		return 1;
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
			groupHolder.indicator = (ImageView) convertView
					.findViewById(R.id.indicator);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		groupHolder.textViewGroup.setText(group.get(groupPosition));
		// 展开ExpandableListView时文字滚动
		if (isExpanded) {
			groupHolder.textViewGroup.setSelected(true);
			groupHolder.indicator
					.setBackgroundResource(R.drawable.ic_find_previous_holo_light);
		} else {
			groupHolder.textViewGroup.setSelected(false);
			groupHolder.indicator
					.setBackgroundResource(R.drawable.ic_find_next_holo_light);
		}
		if (first == groupPosition || second == groupPosition
				|| third == groupPosition)
			groupHolder.textViewGroup.setTextColor(activity.getResources()
					.getColor(R.color.textcolor_checked));
		else
			groupHolder.textViewGroup.setTextColor(Color.BLACK);
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,
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
			childHolder.buttonLeaveMessage = (Button) convertView
					.findViewById(R.id.buttonLeaveMessage);
			childHolder.buttonSelect = (Button) convertView
					.findViewById(R.id.buttonSelect);

			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("导师：").append(child.get(groupPosition).get(0)).append("\n")
				.append("人数：").append(child.get(groupPosition).get(1))
				.append("\n").append("详情：")
				.append(child.get(groupPosition).get(2));
		childHolder.textViewChild.setText(sb.toString());
		childHolder.buttonLeaveMessage
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根

					}
				});
		childHolder.buttonSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				showChoices(v, groupPosition);
			}
		});
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return false;
	}

	class GroupHolder {
		public TextView textViewGroup;
		public ImageView indicator;
	}

	class ChildHolder {
		public TextView textViewChild;
		public Button buttonLeaveMessage;
		public Button buttonSelect;
	}

	/**
	 * 显示志愿选项(第一第二第三志愿)
	 * 
	 * @param button
	 *            在button右下角显示
	 * @param groupPosition
	 *            所选中的button编号
	 */
	private void showChoices(View button, final int groupPosition) {
		activity.getLayoutInflater();
		View v = LayoutInflater.from(activity).inflate(R.layout.choice_item,
				null);
		final PopupWindow pw = new PopupWindow(v, button.getWidth(),
				LayoutParams.WRAP_CONTENT);
		pw.setContentView(v);
		pw.setOutsideTouchable(true);
		// 用物理键返回取消, 或者点击外面取消, 需要设置背景
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.setFocusable(true);
		pw.showAsDropDown(button, 0, -button.getHeight());
		pw.setClippingEnabled(false);
		Button buttonFirst = (Button) v.findViewById(R.id.button_first);
		Button buttonSecond = (Button) v.findViewById(R.id.button_second);
		Button buttonThird = (Button) v.findViewById(R.id.button_third);
		buttonFirst.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
				first = groupPosition;
				// embeddedListView.collapseGroup(groupPosition);
				// embeddedListView.expandGroup(groupPosition);
				notifyDataSetChanged(); // 更新ListView, 更新选中的课题的颜色
			}
		});
		buttonSecond.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
				second = groupPosition;
				notifyDataSetChanged();
			}
		});
		buttonThird.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
				third = groupPosition;
				notifyDataSetChanged();
			}
		});
	}

	public int[] getChoices() {
		int[] choices = new int[3];
		choices[0] = first;
		choices[1] = second;
		choices[2] = third;
		return choices;
	}

	public void setChoices(int[] choices) {
		first = choices[0];
		second = choices[1];
		third = choices[2];
	}
}
