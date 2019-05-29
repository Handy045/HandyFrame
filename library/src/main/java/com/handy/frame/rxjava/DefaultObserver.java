package com.handy.frame.rxjava;

import android.app.Dialog;

import com.blankj.utilcode.util.ObjectUtils;
import com.handy.frame.util.SweetDialogUtil;

/**
 * 默认的的观察者
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/1/11 2:46 PM
 * @modified By liujie
 */
public abstract class DefaultObserver<T> extends BaseObserver<T> {

    public DefaultObserver(String progressInfo) {
        setProgressInfo(progressInfo);
        setDialogListener(new DialogListener() {
            @Override
            public Dialog showProgress(String progressInfo) {
                return SweetDialogUtil.getInstance().showProgress(progressInfo);
            }

            @Override
            public Dialog showError(String errorInfo) {
                return SweetDialogUtil.getInstance().showWarning("提示", errorInfo, "关闭", Dialog::dismiss);
            }

            @Override
            public void dismiss(Dialog dialog) {
                if (ObjectUtils.isNotEmpty(dialog)) {
                    dialog.dismiss();
                }
            }
        });
    }
}


