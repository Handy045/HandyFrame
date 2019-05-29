package com.handy.frame.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 跑马灯TextView
 *
 * @author LiuJie https://github.com/Handy045
 * @description 自定义控件
 * @date Created in 2018/12/3 16:21
 * @modified By LiuJie
 */
public class MarqueeTextView extends AppCompatTextView {

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context) {
        super(context);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
