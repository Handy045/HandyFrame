package com.handy.frame.app.module.list;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.handy.basic.utils.IntentUtils;
import com.handy.frame.api.BaseResultListener;
import com.handy.frame.app.R;
import com.handy.frame.module.list.BaseListActivity;
import com.handy.widget.titlebar.entity.Action;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 简单列表界面（多级查询）
 *
 * @author LiuJie https://github.com/Handy045
 * @description 可在同一个列表中实现多级查询的功能
 * @date Created in 2019-04-25 10:36
 * @modified By liujie
 */
public class ListMultistageActivity extends BaseListActivity<Map<String, FieldInfo>> {

    private int type = 0;

    private FieldQuery fieldQuery;
    private IrmsEnumApi irmsEnumApi;
    private List<Map<String, FieldInfo>> cacheAdapterData;

    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, ListMultistageActivity.class, isFinish);
    }

    @NonNull
    @Override
    protected BaseQuickAdapter<Map<String, FieldInfo>, BaseViewHolder> setAdapter() {
        return new BaseQuickAdapter<Map<String, FieldInfo>, BaseViewHolder>(R.layout.item_list_multistage_rv) {
            @Override
            protected void convert(BaseViewHolder helper, Map<String, FieldInfo> item) {
                if (type == 0) {
                    helper.setText(R.id.tv_name, Objects.requireNonNull(item.get("LABEL_CN")).getLabelCn());
                    helper.getView(R.id.tv_content).setVisibility(View.GONE);
                } else {
                    helper.setText(R.id.tv_name, Objects.requireNonNull(item.get("LABEL_CN")).getLabelCn());
                    helper.getView(R.id.tv_content).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_content, Objects.requireNonNull(item.get("CUID")).getLabelCn());
                }
            }
        };
    }

    @Override
    protected void onSlidingListener(SrlState srlState, RefreshLayout refreshLayout) {
        if (type == 0) {
            irmsEnumApi = new IrmsEnumApi(activity, "SYS_USER-8a380d9d4da6009101510e183dda6de0", "LOCAL_DISTRICT_CITY");
        } else {
            irmsEnumApi = new IrmsEnumApi(activity, "SYS_USER-8a380d9d4da6009101510e183dda6de0", "LOCAL_DISTRICT_TOWN", fieldQuery);
        }

        irmsEnumApi.setResultListener(new BaseResultListener<List<Map<String, FieldInfo>>>() {
            @Override
            public void onSuccess(@NonNull List<Map<String, FieldInfo>> maps) {
                stopAnimation();
                if (srlState == SrlState.REFRESH) {
                    setAdapterData(maps);
                } else if (srlState == SrlState.LOADMORE) {
                    addAdapterData(maps);
                }
            }

            @Override
            public void onFailed(@NonNull Throwable throwable) {
                super.onFailed(throwable);
                stopAnimation();
            }
        }).execute();
    }

    @Override
    protected void onItemClickListener(@NonNull View view, List<Map<String, FieldInfo>> adapterData, int position) {
        super.onItemClickListener(view, adapterData, position);
        if (type == 0) {
            type = 1;
            cacheAdapterData = new ArrayList<>(adapterData);
            fieldQuery = new FieldQuery("RELATED_SPACE_CUID", OperatorType.EQ, Objects.requireNonNull(adapterData.get(position).get("CUID")).getLabelCn());

            getTitlebar().addRightAction(new Action() {
                {
                    this.setText("返回上级", R.color.hf_black0, R.color.hf_orange100);
                }

                @Override
                public void onClick() {
                    type = 0;
                    fieldQuery = null;
                    setAdapterData(cacheAdapterData);
                    getTitlebar().removeRightActions();
                }
            });

            doRefresh();
        } else {
            ToastUtils.showShort(JSONObject.toJSONString(adapterData.get(position)));
        }
    }
}
