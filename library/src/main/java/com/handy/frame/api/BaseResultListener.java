package com.handy.frame.api;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;

import java.io.Serializable;

/**
 * 接口结果返回对象
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/2/21 4:49 PM
 * @modified By liujie
 */
public abstract class BaseResultListener<RESULT> implements ResultListener<RESULT>, Serializable {

    private DialogListener dialogListener;

    public DialogListener getDialogListener() {
        return dialogListener;
    }

    @Override
    public void setDialogListener(@NonNull DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public void onFailed(@NonNull Throwable throwable) {
        LogUtils.e(throwable);
        if (ObjectUtils.isNotEmpty(dialogListener)) {
            dialogListener.showError(throwable.getMessage());
        }
    }

    @Override
    public void onFinish() {
    }
}
