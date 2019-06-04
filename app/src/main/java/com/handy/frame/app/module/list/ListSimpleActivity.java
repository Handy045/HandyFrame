package com.handy.frame.app.module.list;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.handy.basic.utils.IntentUtils;
import com.handy.frame.app.R;
import com.handy.frame.module.list.BaseListActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单列表界面（单级查询）
 *
 * @author LiuJie https://github.com/Handy045
 * @description 可在同一个列表中实现多级查询的功能
 * @date Created in 2019-04-25 10:36
 * @modified By liujie
 */
public class ListSimpleActivity extends BaseListActivity<String> {

    private List<String> listData1 = new ArrayList<String>() {{
        add("AAA");
        add("BBB");
        add("CCC");
        add("DDD");
        add("EEE");
        add("FFF");
        add("GGG");
        add("HHH");
        add("III");
        add("JJJ");
        add("KKK");
        add("LLL");
        add("MMM");
        add("NNN");
    }};

    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, ListSimpleActivity.class, isFinish);
    }

    @NonNull
    @Override
    protected BaseQuickAdapter<String, BaseViewHolder> setAdapter() {
        return new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_list_simple_rv) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
            }
        };
    }

    @Override
    protected void onSlidingListener(SrlState srlState, RefreshLayout refreshLayout) {
        stopAnimation();
        if (srlState == SrlState.REFRESH) {
            setAdapterData(listData1);
        } else if (srlState == SrlState.LOADMORE) {
            addAdapterData(listData1);
        }
    }

    @Override
    protected void onItemClickListener(@NonNull View view, List<String> adapterData, int position) {
        super.onItemClickListener(view, adapterData, position);
        ToastUtils.showShort(adapterData.get(position));
    }
}