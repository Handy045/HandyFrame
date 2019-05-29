package com.handy.frame.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.dou361.dialogui.DialogUIUtils;
import com.handy.base.app.BaseApplication;
import com.handy.base.utils.PermissionsUtils;
import com.handy.frame.util.GlideAlbumLoader;
import com.handy.frame.util.SweetDialogClient;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.rey.material.app.ThemeManager;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Application基本类
 *
 * @author LiuJie https://github.com/Handy045
 * @description Application基本类
 * @date Created in 2018/12/3 16:21
 * @modified By LiuJie
 */
public class LocalBaseApplication extends BaseApplication {

    {
        buglyAppId = "";

        //更新权限扫描的权限集合
        PermissionsUtils.setPermissions(new ArrayList<String>() {{
            add(Manifest.permission.INTERNET);
            add(Manifest.permission.READ_PHONE_STATE);
            add(Manifest.permission.ACCESS_WIFI_STATE);
            add(Manifest.permission.ACCESS_NETWORK_STATE);
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }});
    }

    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            SweetDialogClient.register(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            SweetDialogClient.register(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            SweetDialogClient.register(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (activity != null && activity.isFinishing()) {
                SweetDialogClient.unregister();
                KeyboardUtils.hideSoftInput(activity);
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 2019-05-08 注册Activity生命周期事件回调接口
        try {
            registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 2019-05-08 ARouter 初始化
        try {
            if (AppUtils.isAppDebug()) {
                ARouter.openLog();
                ARouter.openDebug();
            }
            ARouter.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 2019-05-08 DBFlow 初始化
        try {
            FlowManager.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 2019-05-08 DialogUI 初始化
        try {
            DialogUIUtils.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 2019-05-08 BaiduMap 初始化
        try {
            SDKInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 2019-05-08 Material 初始化
        try {
            ThemeManager.init(this, 2, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 2019-05-08 Album 初始化
        try {
            Album.initialize(AlbumConfig.newBuilder(this).setAlbumLoader(new GlideAlbumLoader()).setLocale(Locale.getDefault()).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
