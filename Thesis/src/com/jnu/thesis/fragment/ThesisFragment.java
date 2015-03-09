package com.jnu.thesis.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jnu.thesis.R;

public class ThesisFragment extends Fragment {
	private Button buttonViewSelect;
	private Button buttonViewResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View v = inflater.inflate(R.layout.thesis_fragment, null);
		View v = inflater.inflate(R.layout.thesis_fragment, container, false);
		buttonViewSelect = (Button) v.findViewById(R.id.button_view_select);
		buttonViewResult = (Button) v.findViewById(R.id.button_view_result);
		buttonViewSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "查看课题你妹", Toast.LENGTH_SHORT)
						.show();
			}
		});
		buttonViewResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "查看选题结果你妹", Toast.LENGTH_SHORT)
						.show();
			}
		});
		return v;
	}

}