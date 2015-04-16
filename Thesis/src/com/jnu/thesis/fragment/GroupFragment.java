package com.jnu.thesis.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jnu.thesis.R;
import com.jnu.thesis.activity.ChatActivity;
import com.jnu.thesis.activity.UploadActivity;

public class GroupFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment__group, container, false);
		initView(v);
		return v;
	}

	private void initView(View v) {
		v.findViewById(R.id.button_chat).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						Intent intent = new Intent();
						intent.setClass(getActivity(), ChatActivity.class);
						getActivity().startActivity(intent);
					}
				});
		v.findViewById(R.id.button_upload).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						Intent intent = new Intent();
						intent.setClass(getActivity(), UploadActivity.class);
						getActivity().startActivity(intent);
					}
				});

	}
}