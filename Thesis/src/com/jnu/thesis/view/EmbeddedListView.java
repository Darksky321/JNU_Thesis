package com.jnu.thesis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Ϊ����ScrollView��Ƕ��ListView, ����onMeasure����, ȷ��ListView�ĸ߶�
 * 
 * @author Deng
 *
 */
public class EmbeddedListView extends ExpandableListView {

	public EmbeddedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO �Զ����ɵĹ��캯�����
	}

	public EmbeddedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO �Զ����ɵĹ��캯�����
	}

	public EmbeddedListView(Context context) {
		super(context);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
