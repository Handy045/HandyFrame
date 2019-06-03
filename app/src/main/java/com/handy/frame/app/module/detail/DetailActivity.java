package com.handy.frame.app.module.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.widget.TextView;

import com.handy.basic.utils.IntentUtils;
import com.handy.frame.app.module.detail.fragments.InfoFragment;
import com.handy.frame.app.module.detail.fragments.ListFragment;
import com.handy.frame.app.module.detail.fragments.OthersFragment;
import com.handy.frame.module.detail.BaseDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName
 *
 * @author LiuJie https://github.com/Handy045
 * @description File Description
 * @date Created in 2019-05-31 15:55
 * @modified By liujie
 */
public class DetailActivity extends BaseDetailActivity {
    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, DetailActivity.class, isFinish);
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
        super.initViewHDB(savedInstanceState);
        setTabLayoutMode(TabLayout.MODE_FIXED);
    }

    @NonNull
    @Override
    protected List<TabItemEntity> setTabItems() {
        TextView textView0 = new TextView(context);
        textView0.setText("OOO");
        textView0.setGravity(Gravity.CENTER);
        TextView textView1 = new TextView(context);
        textView1.setText("AAA");
        textView1.setGravity(Gravity.CENTER);
        TextView textView2 = new TextView(context);
        textView2.setText("BBB");
        textView2.setGravity(Gravity.CENTER);
        TextView textView3 = new TextView(context);
        textView3.setText("CCC");
        textView3.setGravity(Gravity.CENTER);
        TextView textView4 = new TextView(context);
        textView4.setText("DDD");
        textView4.setGravity(Gravity.CENTER);
        TextView textView5 = new TextView(context);
        textView5.setText("EEE");
        textView5.setGravity(Gravity.CENTER);
        return new ArrayList<TabItemEntity>() {{
            add(new TabItemEntity(textView1, textView0, new InfoFragment()));
            add(new TabItemEntity(textView2, new ListFragment()));
            add(new TabItemEntity(textView3, new OthersFragment()));
            add(new TabItemEntity(textView4, textView0, new InfoFragment()));
            add(new TabItemEntity(textView5, new ListFragment()));
        }};
    }

    @Override
    protected void onTabSelectedListener(boolean isSelected, TabLayout.Tab tab, TabItemEntity entity) {

    }
}
