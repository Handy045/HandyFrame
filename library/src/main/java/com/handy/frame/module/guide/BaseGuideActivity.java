package com.handy.frame.module.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.handy.frame.R;
import com.handy.frame.base.LocalBaseActivity;

import java.lang.reflect.Field;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

/**
 * 引导界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description 介绍App功能
 * @date Created in 2019/4/17 5:07 PM
 * @modified By liujie
 */
public abstract class BaseGuideActivity extends LocalBaseActivity {

    {
        isUseSwipeBackFinish = false;
    }

    int[] backgroundResId;
    BGABanner bannerBackground;

    int[] foregroundResId;
    BGABanner bannerForeground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.frame_activity_guide);
        bannerBackground = findViewById(R.id.banner_background);
        bannerForeground = findViewById(R.id.banner_foreground);

        backgroundResId = setBackgroundRes();
        if (ObjectUtils.isEmpty(backgroundResId)) {
            LogUtils.e("Please set the backgroundResId first");
            ToastUtils.showShort("Please set the backgroundResId first");
            finish();
        }

        foregroundResId = setForegroundRes();
        if (ObjectUtils.isEmpty(foregroundResId)) {
            foregroundResId = new int[backgroundResId.length];
        }
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
        super.initViewHDB(savedInstanceState);
        // TODO: 2019-04-19 由于原方法使用有BUG，此处通过反射进行替换。（BUG：View.getContext方法返回的不是Activity，而是ContextThemeWrapper）
        /* bannerForeground.setEnterSkipViewIdAndDelegate(R.id.btn_start, R.id.tv_start, () -> IntentUtils.openActivity(activity, clazz, true)); */
        try {
            // 绑定跳过按钮
            View vSkipView = findViewById(R.id.tv_skip);
            vSkipView.setOnClickListener(setStartUpListener());
            Field fmSkipView = bannerForeground.getClass().getDeclaredField("mSkipView");
            fmSkipView.setAccessible(true);
            fmSkipView.set(bannerForeground, vSkipView);

            // 绑定即刻使用按钮
            View vmEnterView = findViewById(R.id.tv_start);
            vmEnterView.setOnClickListener(setStartUpListener());
            Field fEnterView = bannerForeground.getClass().getDeclaredField("mEnterView");
            fEnterView.setAccessible(true);
            fEnterView.set(bannerForeground, vmEnterView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        bannerBackground.setBackgroundResource(android.R.color.white);

        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 320, 640);

        // 设置数据源
        bannerBackground.setData(localImageSize, ImageView.ScaleType.CENTER_CROP, backgroundResId);
        bannerForeground.setData(localImageSize, ImageView.ScaleType.CENTER_CROP, foregroundResId);
    }

    protected abstract int[] setBackgroundRes();

    protected abstract int[] setForegroundRes();

    protected abstract View.OnClickListener setStartUpListener();

    /**
     * 屏蔽返回键功能
     */
    @Override
    public void onBackPressed() {
    }
}
