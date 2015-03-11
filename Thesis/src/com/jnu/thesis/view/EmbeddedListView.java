package com.jnu.thesis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 为了在ScrollView中嵌套ListView, 覆盖onMeasure方法, 确定ListView的高度
 * 
 * @author Deng
 *
 */
public class EmbeddedListView extends ExpandableListView {

	public EmbeddedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

	public EmbeddedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
	}

	public EmbeddedListView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
