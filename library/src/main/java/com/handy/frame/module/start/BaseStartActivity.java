package com.handy.frame.module.start;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.handy.frame.R;
import com.handy.frame.base.FrameActivity;


/**
 * 欢迎界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description 介绍App功能，请求用户权限
 * @date Created in 2019/4/17 5:07 PM
 * @modified By liujie
 */
public abstract class BaseStartActivity extends FrameActivity {

    {
        isCheckPermissions = true;
        isUseSwipeBackFinish = false;
    }

    ImageView ivBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hf_activity_start);
        ivBg = findViewById(R.id.iv_bg);
    }

    @Override
    public void onPermissionSuccessHDB() {
        super.onPermissionSuccessHDB();
        doNext();
    }

    //============================================================
    //  子类需要实现的抽象方法
    //============================================================

    @DrawableRes
    public abstract int setBackground();

    public abstract void doNext();
}
