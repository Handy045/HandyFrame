package com.handy.frame.util;

import android.app.Activity;

import com.blankj.utilcode.util.LogUtils;

/**
 * 弹出框委托管理类
 *
 * @author LiuJie https://github.com/Handy045
 * @description 在Activity的onCreate方法中进行连接，在Activiy的onPause方法中断开连接
 * @date Created in 2019/4/17 8:49 AM
 * @modified By liujie
 */
public class SweetDialogClient {
    private static Activity activityNow = null;

    public static void register(Activity activity) {
        activityNow = activity;
    }

    public static void unregister() {
        if (activityNow != null && activityNow.isFinishing()) {
            SweetDialogUtil.getInstance().dismiss();
            activityNow = null;
        } else {
            LogUtils.e("SweetDialogClient未连接，请在Activity的onCreate中执行SweetDialogClient.register(activity);");
        }
    }

    public static Activity getActivityNow() {
        return activityNow;
    }
}
