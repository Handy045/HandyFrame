package com.handy.frame.app.module.list;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.handy.adapter.Adapter;
import com.handy.adapter.AdapterHelper;
import com.handy.basic.utils.IntentUtils;
import com.handy.frame.app.R;
import com.handy.frame.module.list.BaseListActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView 与 Spinner 嵌套处理
 *
 * @author LiuJie https://github.com/Handy045
 * @description 1、如果RecyclerView（之后简称RV）有动态删除，或其Item中含有Spinner、ImageView、CheckBox、EditText等可修改的标记性控件，则需要在RV的数据中，每个元素里增加对应的标记属性（int:spinnerPosition，String:imagePath，Boolean:isChecked）
 * 2、在RV的适配器中，对Spinner、ImageView、CheckBox等控件设置修改监听，发生修改时需要同时改变Item的对应元素属性值。
 * 3、不建议对RV的适配器数据，写为全局变量。我们要坚守源数据唯一性原则，通过局部变量给RV的适配器设置数据。如有需要可通过RV的适配器通过getData()方法，获取当前列表呈现的所有数据。
 * @date Created in 2019-04-28 19:35
 * @modified By liujie
 */
public class ListSpinnerActivity extends BaseListActivity<ListSpinnerActivity.ItemEntity> {

    List<Integer> itemSpnData = new ArrayList<Integer>() {{
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
    }};

    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, ListSpinnerActivity.class, isFinish);
    }

    @NonNull
    @Override
    protected BaseQuickAdapter<ItemEntity, BaseViewHolder> setAdapter() {
        return new BaseQuickAdapter<ItemEntity, BaseViewHolder>(R.layout.item_list_spinner_rv) {
            @Override
            protected void convert(BaseViewHolder helper, ItemEntity item) {
                EditText edt = helper.getView(R.id.edt_type);
                edt.setText(item.getEdtContent());
                edt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO: 2019-04-29 同步修改Item中的对应元素值
                        item.setEdtContent(s.toString());
                    }
                });

                AppCompatSpinner spinner = helper.getView(R.id.spn_type);
                spinner.setAdapter(item.getSpnAdapter());
                spinner.setSelection(item.getSpnPosition());
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // TODO: 2019-04-29 同步修改Item中的对应元素值
                        item.setSpnPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                helper.getView(R.id.btn_del).setOnClickListener(v -> remove(helper.getAdapterPosition()));
            }
        };
    }

    @Override
    protected void onSlidingListener(SrlState srlState, RefreshLayout refreshLayout) {
        stopAnimation();
        // TODO: 2019-04-29 写入Adapter的数据建议使用局部变量。如果使用了全局变量的话会出现一下问题：
        //  1、例如Item中的Spinner发生选择改变后，为了防止数据丢失，需要修改RV适配器中Data的item数据。但同时会影响到全局变量的RV适配器数据。
        //  2、企图通过new ArrayList<>(RV的全局变量数据)来Copy一份新的集合数据写入RV的适配器，修改Item的Spinner后，全局变量依旧会被改变。

        List<ItemEntity> itemEntities = new ArrayList<>();
        itemEntities.add(new ItemEntity(context, itemSpnData));
        itemEntities.add(new ItemEntity(context, itemSpnData));
        itemEntities.add(new ItemEntity(context, itemSpnData));
        if (srlState == SrlState.REFRESH) {
            setAdapterData(new ArrayList<>(itemEntities));
        } else {
            addAdapterData(new ArrayList<>(itemEntities));
        }
    }

    /**
     * RecyclerView元素对象
     */
    class ItemEntity {
        private String edtContent;

        private int spnPosition;
        private List<Integer> spnData;
        private Adapter<Integer> spnAdapter;

        public ItemEntity(Context context, List<Integer> spnData) {
            this.edtContent = "";
            this.spnPosition = 0;
            this.spnData = spnData;
            this.spnAdapter = new Adapter<Integer>(context, spnData, R.layout.item_list_spinner_rv_spn) {
                @Override
                protected void convert(AdapterHelper helper, Integer item) {
                    helper.setText(R.id.tv_content, String.valueOf(item));
                }
            };
        }

        public String getEdtContent() {
            return edtContent;
        }

        public void setEdtContent(String edtContent) {
            this.edtContent = edtContent;
        }

        public int getSpnPosition() {
            return spnPosition;
        }

        public void setSpnPosition(int spnPosition) {
            this.spnPosition = spnPosition;
        }

        public List<Integer> getSpnData() {
            return spnData;
        }

        public void setSpnData(List<Integer> spnData) {
            this.spnData = spnData;
        }

        public Adapter<Integer> getSpnAdapter() {
            return spnAdapter;
        }

        public void setSpnAdapter(Adapter<Integer> spnAdapter) {
            this.spnAdapter = spnAdapter;
        }
    }
}
