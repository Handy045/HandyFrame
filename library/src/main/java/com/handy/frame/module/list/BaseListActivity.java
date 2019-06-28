package com.handy.frame.module.list;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.handy.frame.R;
import com.handy.frame.base.FrameActivity;
import com.handy.widget.titlebar.HandyTitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;


/**
 * 简单的列表界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description File Description
 * @date Created in 2019-04-25 09:48
 * @modified By liujie
 */
public abstract class BaseListActivity<T> extends FrameActivity {

    protected enum SrlState {
        /**
         * 无状态
         */
        NONE,
        /**
         * 刷新
         */
        REFRESH,
        /**
         * 加载
         */
        LOADMORE
    }

    HandyTitleBar titlebar;
    RelativeLayout rlTop;
    RecyclerView rvList;
    SmartRefreshLayout srlRefresh;
    RelativeLayout rlBottom;

    public boolean isAutoRefresh = false;
    public boolean isAutoLoadMore = false;
    public boolean isNeedRefresh = true;
    public boolean isNeedLoadMore = true;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private BaseQuickAdapter<T, BaseViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewHDB(resetContentView());
        titlebar = findViewById(R.id.titlebar);
        rlTop = findViewById(R.id.rl_top);
        rvList = findViewById(R.id.rv_list);
        srlRefresh = findViewById(R.id.srl_refresh);
        rlBottom = findViewById(R.id.rl_bottom);

        if (ObjectUtils.isEmpty(adapter = setAdapter())) {
            LogUtils.e("please set the Adapter first!");
            ToastUtils.showShort("Please set the Adapter first");
            finish();
        }
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
        super.initViewHDB(savedInstanceState);
        initActionBar(titlebar, "资源列表");

        srlRefresh.setEnableRefresh(isNeedRefresh);
        if (isNeedRefresh) {
            srlRefresh.setRefreshHeader(new ClassicsHeader(context));
            srlRefresh.setOnRefreshListener(refreshLayout -> {
                setAdapterData(new ArrayList<>());
                onSlidingListener(SrlState.REFRESH, refreshLayout);
            });
        }

        srlRefresh.setEnableLoadMore(isNeedLoadMore);
        if (isNeedLoadMore) {
            srlRefresh.setEnableAutoLoadMore(isAutoLoadMore);
            srlRefresh.setRefreshFooter(new ClassicsFooter(context));
            srlRefresh.setOnLoadMoreListener(refreshLayout -> {
                onSlidingListener(SrlState.LOADMORE, refreshLayout);
            });
        }

        if (isAutoRefresh) {
            srlRefresh.autoRefresh();
        }

        rvList.setAdapter(adapter);
        rvList.setLayoutManager(initLayoutManager());
        if (ObjectUtils.isNotEmpty(initItemDecoration())) {
            rvList.addItemDecoration(initItemDecoration());
        }
        adapter.setOnItemClickListener((mAdapter, view, position) -> onItemClickListener(view, adapter.getData(), position));

        setTopMenuLayout(rlTop);
        setBottomMenuLayout(rlBottom);
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
    //  SmartRefreshLayout 功能开放
    //============================================================

    /**
     * 结束刷新/加载动画
     */
    protected void stopAnimation() {
        if (srlRefresh.isRefreshing()) {
            srlRefresh.finishRefresh();
        } else if (srlRefresh.isLoading()) {
            srlRefresh.finishLoadMore();
        }
    }

    /**
     * 刷新列表
     */
    protected void doRefresh() {
        srlRefresh.autoRefresh();
    }

    /**
     * 加载更多
     */
    protected void doLoadMore() {
        srlRefresh.autoLoadMore();
    }

    /**
     * 关闭刷新功能
     */
    protected void setEnableRefresh(boolean enable) {
        srlRefresh.setEnableRefresh(enable);
    }

    /**
     * 关闭加载功能
     */
    protected void setEnableLoadMore(boolean enable) {
        srlRefresh.setEnableLoadMore(enable);
    }

    //============================================================
    //  RecyclerView 功能开放
    //============================================================

    /**
     * 设置List数据
     */
    protected void setAdapterData(List<T> adapterData) {
        this.adapter.replaceData(adapterData);
    }

    /**
     * 添加List数据
     */
    protected void addAdapterData(List<T> adapterData) {
        this.adapter.addData(adapterData);
    }

    /**
     * 设置RecyclerView排版方式
     */
    @NonNull
    protected RecyclerView.LayoutManager initLayoutManager() {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    /**
     * 设置RecyclerView分割线
     */
    protected RecyclerView.ItemDecoration initItemDecoration() {
        return new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
    }

    /**
     * 设置列表Item点击事件
     *
     * @param view     Item布局
     * @param position 点击的Item的坐标
     * @param position 点击的Item的数据
     */
    protected void onItemClickListener(@NonNull View view, List<T> adapterData, int position) {

    }

    //============================================================
    //  功能开放
    //============================================================

    @LayoutRes
    protected int resetContentView() {
        return R.layout.hf_activity_list;
    }

    //============================================================
    //  子类需要实现的抽象方法
    //============================================================

    /**
     * 设置RecyclerView适配器
     */
    @NonNull
    protected abstract BaseQuickAdapter<T, BaseViewHolder> setAdapter();

    /**
     * 列表数据监听
     *
     * @param srlState      SrlState.REFRESH：刷新，SrlState.LOADMORE：加载更多
     * @param refreshLayout 刷新加载控件
     */
    protected abstract void onSlidingListener(SrlState srlState, RefreshLayout refreshLayout);
}
