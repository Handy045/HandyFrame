package com.handy.frame.base;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.handy.basic.app.BaseActivity;
import com.handy.basic.mvp.BasePresenter;
import com.handy.frame.R;
import com.handy.widget.titlebar.HandyTitleBar;
import com.handy.widget.titlebar.entity.Action;

/**
 * Activity基本类
 *
 * @author LiuJie https://github.com/Handy045
 * @description Activity基本类
 * @date Created in 2018/12/3 16:21
 * @modified By LiuJie
 */
public class FrameActivity<P extends BasePresenter> extends BaseActivity<P> {

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
                    setImageSrc(R.drawable.google_arrow_back_ios, R.color.hf_grey0, R.color.hf_orange50);
                }

                @Override
                public void onClick() {
                    finish();
                }
            });
        } else {
            LogUtils.e("Can't find handyTitleBar in this activity.");
        }
    }
}
