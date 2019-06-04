package com.handy.frame.app.module.guide;

import android.app.Activity;

import com.handy.basic.utils.IntentUtils;
import com.handy.frame.app.R;
import com.handy.frame.app.module.login.LoginActivity;
import com.handy.frame.module.guide.BaseGuideActivity;

/**
 * 引导界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description File Description
 * @date Created in 2019-04-24 14:47
 * @modified By liujie
 */
public class GuideActivity extends BaseGuideActivity {

    public static void doIntent(Activity activity, boolean isFinish) {
        IntentUtils.openActivity(activity, GuideActivity.class, isFinish);
    }

    @Override
    public int[] setBackgroundRes() {
        return new int[]{R.drawable.boco_img_guide_bg1, R.drawable.boco_img_guide_bg2, R.drawable.boco_img_guide_bg3};
    }

    @Override
    public int[] setForegroundRes() {
        return new int[]{R.drawable.boco_img_guide_fg1, R.drawable.boco_img_guide_fg2, R.drawable.boco_img_guide_fg3};
    }

    @Override
    protected void onStartListener() {
        LoginActivity.doIntent(activity, true);
    }
}
