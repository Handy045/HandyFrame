package com.handy.frame.app.module.list;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.boco.groupclient.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.handy.basic.utils.IntentUtils;
import com.handy.frame.module.list.BaseListActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 可扩展的列表界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description File Description
 * @date Created in 2019-04-26 09:50
 * @modified By liujie
 */
public class ListExpansionActivity extends BaseListActivity<ListExpansionActivity.ItemClass> {

    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, ListExpansionActivity.class, isFinish);
    }

    @NonNull
    @Override
    protected BaseQuickAdapter<ItemClass, BaseViewHolder> setAdapter() {
        return new BaseMultiItemQuickAdapter<ItemClass, BaseViewHolder>(new ArrayList<>()) {

            {
                addItemType(0, R.layout.item_list_expansion_rv);
                addItemType(1, R.layout.item_list_simple_rv);
            }

            @Override
            protected void convert(BaseViewHolder helper, ItemClass item) {
                if (item.getLevel() == 0) {
                    helper.setText(R.id.tv_content, item.getName());
                    helper.itemView.setOnClickListener(v -> {
                        if (item.isExpanded()) {
                            collapse(helper.getAdapterPosition());
                        } else {
                            expand(helper.getAdapterPosition());
                        }
                    });
                } else if (item.getLevel() == 1) {
                    helper.setText(R.id.tv_content, item.getName());
                }
            }
        };
    }

    @Override
    protected void onSlidingListener(SrlState srlState, RefreshLayout refreshLayout) {
        stopAnimation();

        ItemClass itemA = new ItemClass(0, "AAAAAA");
        ItemClass itemB = new ItemClass(0, "BBBBBB");
        ItemClass itemC = new ItemClass(0, "CCCCCC");
        for (int i = 0; i < 5; i++) {
            itemA.addSubItem(new ItemClass(1, String.valueOf(Math.random() * 500)));
            itemB.addSubItem(new ItemClass(1, String.valueOf(Math.random() * 500)));
            itemC.addSubItem(new ItemClass(1, String.valueOf(Math.random() * 500)));
        }

        List<ItemClass> adapterData = new ArrayList<ItemClass>();
        adapterData.add(itemA);
        adapterData.add(itemB);
        adapterData.add(itemC);

        if (srlState == SrlState.REFRESH) {
            setAdapterData(adapterData);
        } else {
            addAdapterData(adapterData);
        }
    }

    class ItemClass extends AbstractExpandableItem<ItemClass> implements MultiItemEntity {

        String name;
        int itemType;

        ItemClass(int itemType, String name) {
            this.name = name;
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        @Override
        public int getLevel() {
            return itemType;
        }

        String getName() {
            return name;
        }
    }
}
