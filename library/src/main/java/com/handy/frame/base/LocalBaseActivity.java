package com.handy.frame.base;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.handy.base.app.BaseActivity;
import com.handy.frame.R;
import com.handy.titlebar.HandyTitleBar;
import com.handy.titlebar.entity.Action;

/**
 * Activity基本类
 *
 * @author LiuJie https://github.com/Handy045
 * @description Activity基本类
 * @date Created in 2018/12/3 16:21
 * @modified By LiuJie
 */
public class LocalBaseActivity extends BaseActivity {

    //============================================================
    //  初始化标题栏
    //============================================================

    public void initActionBar(HandyTitleBar handyTitleBar) {
        initActionBar(handyTitleBar, null, null);
    }

    public void initActionBar(HandyTitleBar handyTitleBar, String title) {
        initActionBar(handyTitleBar, title, null);
    }

    public void initActionBar(HandyTitleBar handyTitleBar, String title, String subTitle) {
        if (handyTitleBar != null) {
            handyTitleBar.setVisibility(View.VISIBLE);

            if (ObjectUtils.isEmpty(subTitle)) {
                if (ObjectUtils.isNotEmpty(title)) {
                    handyTitleBar.setMainText(title);
                }
            } else {
                if (ObjectUtils.isNotEmpty(title)) {
                    handyTitleBar.setTitleBarContent(title + "\n" + subTitle);
                } else {
                    handyTitleBar.setSubText(title + "\n" + subTitle);
                }
            }
            handyTitleBar.showCustomStatusBar((Activity) context);

            handyTitleBar.addLeftAction(new Action() {
                {
                    setImageSrc(R.drawable.hdb_back, R.color.hdb_white, R.color.hdb_orange500);
                }

                @Override
                public void onClick() {

                }
            });
        } else {
            LogUtils.e("Can't find handyTitleBar in this activity.");
        }
    }
}
