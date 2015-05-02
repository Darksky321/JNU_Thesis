package com.jnu.thesis.view;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.bean.MessageBean;

public class MessageListAdapter extends BaseAdapter {

	private List<MessageBean> msgList;
	private Activity activity;

	public MessageListAdapter(Activity activity, List<MessageBean> msgList) {
		// TODO 自动生成的构造函数存根
		this.activity = activity;
		this.msgList = msgList;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return msgList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return msgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		if (!msgList.get(position).getFromName()
				.equals(Parameter.getCurrentUserName())) {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.view_left_dialog, parent, false);
		} else {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.view_right_dialog, parent, false);
		}
		TextView tvName = (TextView) convertView.findViewById(R.id.name);
		TextView tvContent = (TextView) convertView.findViewById(R.id.content);
		tvName.setText(msgList.get(position).getFromName());
		tvContent.setText(msgList.get(position).getContent());

		return convertView;
	}

	public List<MessageBean> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<MessageBean> msgList) {
		this.msgList = msgList;
		notifyDataSetChanged();
	}

	public void addMessage(MessageBean msg) {
		msgList.add(msg);
		notifyDataSetChanged();
	}
}
