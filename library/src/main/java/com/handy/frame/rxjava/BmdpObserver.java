package com.handy.frame.rxjava;

import android.app.Dialog;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.handy.frame.config.FrameConfig;
import com.handy.frame.util.SweetDialogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * 针对OpDetail定制的观察者
 *
 * @author LiuJie https://github.com/Handy045
 * @description 针对OpDetail定制的观察者
 * @date Created in 2017/11/6 10:21
 * @modified By LiuJie
 */
public abstract class BmdpObserver<T> extends BaseObserver<T> {

    private SmartRefreshLayout refreshLayout;

    public BmdpObserver() {
        this.refreshLayout = null;
        setProgressInfo(null);
        initDialogListener();
    }

    public BmdpObserver(String progressInfo) {
        this.refreshLayout = null;
        setProgressInfo(progressInfo);
        initDialogListener();
    }

    public BmdpObserver(String progressInfo, SmartRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
        setProgressInfo(progressInfo);
        initDialogListener();
    }

    private void initDialogListener() {
        setDialogListener(new DialogListener() {
            @Override
            public Dialog showProgress(String progressInfo) {
                return SweetDialogUtil.getInstance().showProgress(progressInfo);
            }

            @Override
            public Dialog showError(String errorInfo) {
                if (errorInfo.equals(FrameConfig.PROMPT_EMPTY_RESPONSE)) {
                    ToastUtils.showShort("暂无更多数据");
                    return null;
                } else {
                    return SweetDialogUtil.getInstance().showWarning("提示", errorInfo, "关闭", Dialog::dismiss);
                }
            }

            @Override
            public void dismiss(Dialog dialog) {
                if (ObjectUtils.isNotEmpty(dialog)) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        if (ObjectUtils.isNotEmpty(refreshLayout)) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (ObjectUtils.isNotEmpty(refreshLayout)) {
            refreshLayout.finishRefresh(false);
            refreshLayout.finishLoadMore(false);
        }
    }
}

