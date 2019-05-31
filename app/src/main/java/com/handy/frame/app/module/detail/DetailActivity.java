package com.handy.frame.app.module.detail;

import android.app.Activity;
import android.support.annotation.NonNull;

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

    @NonNull
    @Override
    protected List<TabItemEntity> setTabItems() {
        return new ArrayList<TabItemEntity>() {{
            add(new TabItemEntity("基本信息", new InfoFragment()));
            add(new TabItemEntity("关联资源", new ListFragment()));
            add(new TabItemEntity("其他信息", new OthersFragment()));
        }};
    }

}
