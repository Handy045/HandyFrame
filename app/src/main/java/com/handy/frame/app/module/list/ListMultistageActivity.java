package com.handy.frame.app.module.list;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.handy.basic.utils.IntentUtils;
import com.handy.frame.app.R;
import com.handy.frame.module.list.BaseListActivity;
import com.handy.widget.titlebar.entity.Action;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单列表界面（多级查询）
 *
 * @author LiuJie https://github.com/Handy045
 * @description 可在同一个列表中实现多级查询的功能
 * @date Created in 2019-04-25 10:36
 * @modified By liujie
 */
public class ListMultistageActivity extends BaseListActivity<String> {

    private int type = 0;
    private String clickContent = "";

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
    private Map<String, List<String>> listData2 = new HashMap<String, List<String>>() {{
        put("AAA", new ArrayList<String>() {{
            add("AAA111");
            add("AAA222");
            add("AAA333");
            add("AAA444");
            add("AAA555");
        }});
        put("BBB", new ArrayList<String>() {{
            add("BBB111");
            add("BBB222");
            add("BBB333");
            add("BBB444");
            add("BBB555");
        }});
        put("CCC", new ArrayList<String>() {{
            add("CCC111");
            add("CCC222");
            add("CCC333");
            add("CCC444");
            add("CCC555");
        }});
        put("DDD", new ArrayList<String>() {{
            add("DDD111");
            add("DDD222");
            add("DDD333");
            add("DDD444");
            add("DDD555");
        }});
        put("EEE", new ArrayList<String>() {{
            add("EEE111");
            add("EEE222");
            add("EEE333");
            add("EEE444");
            add("EEE555");
        }});
    }};

    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, ListMultistageActivity.class, isFinish);
    }

    @NonNull
    @Override
    protected BaseQuickAdapter<String, BaseViewHolder> setAdapter() {
        return new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_list_multistage_rv) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                if (type == 0) {
                    helper.setText(R.id.tv_content, item);
                }
            }
        };
    }

    @Override
    protected void onSlidingListener(SrlState srlState, RefreshLayout refreshLayout) {
        stopAnimation();
        if (type == 0) {
            if (srlState == SrlState.REFRESH) {
                setAdapterData(listData1);
            } else if (srlState == SrlState.LOADMORE) {
                addAdapterData(listData1);
            }
        } else if (ObjectUtils.isNotEmpty(clickContent)) {
            List<String> tempData = listData2.get(clickContent);
            if (ObjectUtils.isEmpty(tempData)) {
                return;
            }
            if (srlState == SrlState.REFRESH) {
                setAdapterData(tempData);
            } else if (srlState == SrlState.LOADMORE) {
                addAdapterData(tempData);
            }
        }
    }

    @Override
    protected void onItemClickListener(@NonNull View view, List<String> adapterData, int position) {
        super.onItemClickListener(view, adapterData, position);
        if (type == 0) {
            clickContent = adapterData.get(position);

            getTitlebar().addRightAction(new Action() {
                {
                    this.setText("返回上级", R.color.hf_black0, R.color.hf_orange100);
                }

                @Override
                public void onClick() {
                    type = 0;
                    clickContent = "";
                    setAdapterData(listData1);
                    getTitlebar().removeRightActions();
                }
            });

            doRefresh();
        }
    }
}
