package com.handy.frame.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自计算高度的Gridview
 *
 * @author LiuJie https://github.com/Handy045
 * @description 自定义控件
 * @date Created in 2018/12/3 16:21
 * @modified By LiuJie
 */
public class NoSlideGridView extends GridView {
    public NoSlideGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
