package com.handy.frame.app.module.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.handy.basic.utils.IntentUtils;
import com.handy.frame.app.R;
import com.handy.frame.app.module.detail.DetailActivity;
import com.handy.frame.app.module.list.ListExpansionActivity;
import com.handy.frame.app.module.list.ListMultistageActivity;
import com.handy.frame.app.module.list.ListSimpleActivity;
import com.handy.frame.app.module.list.ListSpinnerActivity;
import com.handy.frame.base.FrameActivity;
import com.handy.widget.titlebar.HandyTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description File Description
 * @date Created in 2019-04-24 14:47
 * @modified By liujie
 */
public class MainActivity extends FrameActivity {

    @BindView(R.id.common_titlebar)
    HandyTitleBar commonTitlebar;
    @BindView(R.id.btn_detail)
    Button btnDetail;
    @BindView(R.id.btn_list_simple)
    Button btnListSimple;
    @BindView(R.id.btn_list_spinner)
    Button btnListSpinner;
    @BindView(R.id.btn_list_multistage)
    Button btnListMultistage;
    @BindView(R.id.btn_list_expansion)
    Button btnListExpansion;

    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, MainActivity.class, isFinish);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
        super.initViewHDB(savedInstanceState);
        initActionBar(commonTitlebar, "主界面");
        btnDetail.setOnClickListener(v -> DetailActivity.doIntent(activity, false));
        btnListSimple.setOnClickListener(v -> ListSimpleActivity.doIntent(activity, false));
        btnListSpinner.setOnClickListener(v -> ListSpinnerActivity.doIntent(activity, false));
        btnListMultistage.setOnClickListener(v -> ListMultistageActivity.doIntent(activity, false));
        btnListExpansion.setOnClickListener(v -> ListExpansionActivity.doIntent(activity, false));
    }
}
