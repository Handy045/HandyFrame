package com.handy.frame.api;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ObjectUtils;
import com.handy.frame.util.SweetDialogUtil;

/**
 * 接口提示弹框建造者
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/2/22 11:16 AM
 * @modified By liujie
 */
public class BaseDialogBuilder implements DialogListener {

    private Dialog dialog;
    private AppCompatActivity activity;

    public BaseDialogBuilder(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showProgress(String progressInfo) {
        if (activity != null && !activity.isFinishing()) {
            dismiss();
            dialog = SweetDialogUtil.getInstance().showProgress(progressInfo);
        }
    }

    @Override
    public void showSuccess(String successInfo) {
        if (activity != null && !activity.isFinishing()) {
            dismiss();
            dialog = SweetDialogUtil.getInstance().showSuccess(successInfo, "关闭", Dialog::dismiss);
        }
    }

    @Override
    public void showError(String errorInfo) {
        if (activity != null && !activity.isFinishing()) {
            dismiss();
            dialog = SweetDialogUtil.getInstance().showWarning("提示", errorInfo, "关闭", Dialog::dismiss);
        }
    }

    @Override
    public void dismiss() {
        if (ObjectUtils.isNotEmpty(dialog) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
