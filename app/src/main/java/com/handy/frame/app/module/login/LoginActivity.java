package com.handy.frame.app.module.login;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.handy.basic.utils.IntentUtils;
import com.handy.frame.app.module.main.MainActivity;
import com.handy.frame.module.login.BaseLoginActivity;

/**
 * 登录界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description File Description
 * @date Created in 2019-04-24 16:31
 * @modified By liujie
 */
public class LoginActivity extends BaseLoginActivity {

    {
        isNeedPassword = false;
    }

    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, LoginActivity.class, isFinish);
    }

    @Override
    protected void onLoginListener(String username, String password) {
        if ("admin".equals(username)) {
            MainActivity.doIntent(activity, true);
        } else {
            ToastUtils.showShort("登录名或密码有误");
        }
    }
}
