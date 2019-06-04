package com.handy.frame.app.module.start;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.handy.frame.app.R;
import com.handy.frame.app.module.guide.GuideActivity;
import com.handy.frame.base.FrameActivity;


/**
 * 欢迎界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description 介绍App功能，请求用户权限
 * @date Created in 2019/4/17 5:07 PM
 * @modified By liujie
 */
public class StartActivity extends FrameActivity {

    {
        isCheckPermissions = true;
        isUseSwipeBackFinish = false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public void onPermissionSuccessHDB() {
        super.onPermissionSuccessHDB();
        GuideActivity.doIntent(activity, true);
    }
}
