package com.jnu.thesis.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class FinishListener implements OnClickListener {
	private Activity activity;

	public FinishListener(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		activity.finish();
	}

}
