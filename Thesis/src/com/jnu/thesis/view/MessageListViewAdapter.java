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
import com.jnu.thesis.bean.MessageBean;

public class MessageListViewAdapter extends BaseAdapter {

	private List<MessageBean> messages;
	private Activity activity;
	private List<Boolean> check;

	public MessageListViewAdapter(List<MessageBean> messages, Activity activity) {
		super();
		this.messages = messages;
		this.activity = activity;
		check = new ArrayList<Boolean>();
		for (int i = 0; i < messages.size(); i++) {
			check.add(false);
		}
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return messages.get(position);
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
					R.layout.view_message_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textViewName = (TextView) convertView
					.findViewById(R.id.textView_name);
			viewHolder.textViewTitle = (TextView) convertView
					.findViewById(R.id.textView_title);
			viewHolder.textViewContent = (TextView) convertView
					.findViewById(R.id.textView_content);
			viewHolder.textViewTime = (TextView) convertView
					.findViewById(R.id.textView_time);
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.checkBox);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textViewName.setText(messages.get(position).getFromName());
		viewHolder.textViewTitle.setText(messages.get(position).getTitle());
		viewHolder.textViewContent.setText(messages.get(position).getContent());
		viewHolder.textViewTime.setText(messages.get(position).getUpdate_time()
				.substring(5, 10));
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
		public TextView textViewTitle;
		public TextView textViewContent;
		public TextView textViewTime;
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

	public List<MessageBean> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}

	public void refresh(List<MessageBean> msgs) {
		this.messages = msgs;
		check = new ArrayList<Boolean>();
		for (int i = 0; i < messages.size(); i++) {
			check.add(false);
		}
		notifyDataSetChanged();
	}

	public void checkAll() {
		for (int location = 0; location < check.size(); ++location)
			check.set(location, true);
		notifyDataSetChanged();
	}

	public void unCheckAll() {
		for (int location = 0; location < check.size(); ++location)
			check.set(location, false);
		notifyDataSetChanged();
	}

	public boolean checkedAll() {
		for (int location = 0; location < check.size(); ++location) {
			if (check.get(location) == false) {
				return false;
			}
		}
		return true;
	}

	public List<Boolean> getCheck() {
		return check;
	}

	public void setCheck(List<Boolean> check) {
		this.check = check;
	}
}
