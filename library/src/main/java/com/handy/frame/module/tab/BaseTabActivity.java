package com.handy.frame.module.tab;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
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
public abstract class BaseTabActivity extends FrameActivity {

    HandyTitleBar titlebar;
    RelativeLayout rlTop;
    TabLayout tablayout;
    ViewPager viewpager;
    RelativeLayout rlBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hf_activity_tab);
        titlebar = findViewById(R.id.titlebar);
        rlTop = findViewById(R.id.rl_top);
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        rlBottom = findViewById(R.id.rl_bottom);
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
        super.initViewHDB(savedInstanceState);
        initActionBar(titlebar, "资源列表");

        setTopMenuLayout(rlTop);
        setBottomMenuLayout(rlBottom);
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
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

        for (int i = 0; i < tabItemEntities.size(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            TabItemEntity entity = tabItemEntities.get(i);
            if (ObjectUtils.isNotEmpty(tab)) {
                tab.setTag(entity);
            }
            if (ObjectUtils.isNotEmpty(entity.getViewNormal()) && ObjectUtils.isNotEmpty(tab)) {
                //获得每一个tab
                tab.setCustomView(null);
                tab.setCustomView(entity.getViewNormal());
                if (i == 0 && ObjectUtils.isNotEmpty(entity.getViewSelected())) {
                    tab.setCustomView(null);
                    tab.setCustomView(entity.getViewSelected());
                }
            }
        }

        tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabItemEntity entity = (TabItemEntity) tab.getTag();
                if (ObjectUtils.isNotEmpty(entity) && ObjectUtils.isNotEmpty(entity.getViewNormal())) {
                    if (ObjectUtils.isNotEmpty(entity.getViewSelected())) {
                        LogUtils.d("getViewSelected");
                        tab.setCustomView(null);
                        tab.setCustomView(entity.getViewSelected());
                    } else {
                        LogUtils.d("getViewNormal");
                        tab.setCustomView(null);
                        tab.setCustomView(entity.getViewNormal());
                    }
                }

                onTabSelectedListener(true, tab, entity);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabItemEntity entity = (TabItemEntity) tab.getTag();
                if (ObjectUtils.isNotEmpty(entity) && ObjectUtils.isNotEmpty(entity.getViewNormal())) {
                    tab.setCustomView(null);
                    tab.setCustomView(entity.getViewNormal());
                }

                onTabSelectedListener(false, tab, entity);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    protected class TabItemEntity {
        private String tabName;
        private Fragment fragment;
        private View viewNormal;
        private View viewSelected;

        public TabItemEntity(@NonNull String tabName, @NonNull Fragment fragment) {
            this.tabName = tabName;
            this.fragment = fragment;
        }

        public TabItemEntity(@NonNull View viewNormal, @NonNull Fragment fragment) {
            this.viewNormal = viewNormal;
            this.fragment = fragment;
        }

        public TabItemEntity(@NonNull View viewNormal, @NonNull View viewSelected, @NonNull Fragment fragment) {
            this.viewNormal = viewNormal;
            this.viewSelected = viewSelected;
            this.fragment = fragment;
        }

        String getTabName() {
            return tabName;
        }

        Fragment getFragment() {
            return fragment;
        }

        View getViewNormal() {
            return viewNormal;
        }

        View getViewSelected() {
            return viewSelected;
        }

        TabLayout.Tab getTabItem(TabLayout tabLayout) {
            return tabLayout.newTab().setText(this.tabName);
        }
    }

    //============================================================
    //  UI 功能开放
    //============================================================

    /**
     * 设置顶部菜单布局控件，可填充过滤条件
     */
    protected void setTopMenuLayout(RelativeLayout relayout) {

    }

    /**
     * 设置顶部菜单布局控件，可填充过滤条件
     */
    protected void setBottomMenuLayout(RelativeLayout relayout) {

    }

    //============================================================
    //  HandyTitleBar 功能开放
    //============================================================

    /**
     * 获取标题栏控件
     */
    protected HandyTitleBar getTitlebar() {
        return titlebar;
    }

    /**
     * 设置标题栏文本内容
     */
    protected void setTitleBarText(@NonNull String mainText) {
        titlebar.setMainText(mainText);
    }

    //============================================================
    //  TabLayout 功能开放
    //============================================================

    /**
     * 获取标签栏控件
     */
    public TabLayout setTabLayoutHeight(int height) {
        if (height >= 0) {
            ViewGroup.LayoutParams layoutParams = tablayout.getLayoutParams();
            layoutParams.height = ConvertUtils.dp2px(height);
            tablayout.setLayoutParams(layoutParams);
        }
        return tablayout;
    }

    public TabLayout setTabLayoutBackground(@DrawableRes int background) {
        try {
            tablayout.setBackgroundResource(background);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tablayout;
    }

    /**
     * 设置TabLayou模式（TabLayout.MODE_FIXED、TabLayout.MODE_SCROLLABLE）
     */
    protected void setTabLayoutMode(int tabMode) {
        if (tabMode == TabLayout.MODE_FIXED || tabMode == TabLayout.MODE_SCROLLABLE) {
            tablayout.setTabMode(tabMode);
        }
    }

    //============================================================
    //  子类需要实现的抽象方法
    //============================================================

    @NonNull
    protected abstract List<TabItemEntity> setTabItems();

    protected abstract void onTabSelectedListener(boolean isSelected, TabLayout.Tab tab, TabItemEntity entity);
}
