package com.handy.frame.module.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.handy.adapter.FragmentPagerAdapter2;
import com.handy.frame.R;
import com.handy.frame.base.FrameActivity;
import com.handy.widget.titlebar.HandyTitleBar;

import java.util.List;


/**
 * 详情界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description 包含TabLayout的详情界面
 * @date Created in 2019-05-31 15:11
 * @modified By liujie
 */
public abstract class BaseDetailActivity extends FrameActivity {

    HandyTitleBar titlebar;
    TabLayout tablayout;
    ViewPager viewpager;
    RelativeLayout rlBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hf_activity_detail);
        titlebar = findViewById(R.id.titlebar);
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        rlBottom = findViewById(R.id.rl_bottom);
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
        super.initViewHDB(savedInstanceState);
        initActionBar(titlebar, "资源列表");
    }

    @Override
    public void initDataHDB() {
        super.initDataHDB();
        List<TabItemEntity> tabItemEntities = setTabItems();
        if (ObjectUtils.isEmpty(tabItemEntities)) {
            LogUtils.e("lease set the abItems first.");
            return;
        }

        for (TabItemEntity entity : tabItemEntities) {
            tablayout.addTab(entity.getTabItem(tablayout));
        }

        viewpager.setOffscreenPageLimit(tabItemEntities.size() - 1);
        viewpager.setAdapter(new FragmentPagerAdapter2(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return tabItemEntities.get(i).getFragment();
            }

            @Override
            public int getCount() {
                return tabItemEntities.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabItemEntities.get(position).getTabName();
            }
        });
        tablayout.setupWithViewPager(viewpager);
    }

    @NonNull
    protected abstract List<TabItemEntity> setTabItems();

    protected class TabItemEntity {
        private String tabName;
        private Fragment fragment;

        public TabItemEntity(@NonNull String tabName, @NonNull Fragment fragment) {
            this.tabName = tabName;
            this.fragment = fragment;
        }

        String getTabName() {
            return tabName;
        }

        Fragment getFragment() {
            return fragment;
        }

        TabLayout.Tab getTabItem(TabLayout tabLayout) {
            return tabLayout.newTab().setText(this.tabName);
        }
    }
}
