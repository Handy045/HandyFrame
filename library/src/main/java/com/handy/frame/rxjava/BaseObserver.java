package com.handy.frame.rxjava;

import android.app.Dialog;

import com.blankj.utilcode.util.ObjectUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 针对BMDP定制的观察者
 *
 * @author LiuJie https://github.com/Handy045
 * @description 针对BMDP定制的观察者
 * @date Created in 2018/12/3 16:21
 * @modified By LiuJie
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private String progressInfo;
    private DialogListener dialogListener;

    private Dialog dialog;

    /**
     * 构造观察者
     */
    protected BaseObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (ObjectUtils.isNotEmpty(dialogListener) && ObjectUtils.isNotEmpty(progressInfo)) {
            dialogListener.dismiss(dialog);
            dialog = dialogListener.showProgress(progressInfo);
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        if (ObjectUtils.isNotEmpty(dialogListener)) {
            dialogListener.dismiss(dialog);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (ObjectUtils.isNotEmpty(dialogListener)) {
            dialogListener.dismiss(dialog);
            dialog = dialogListener.showError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        if (ObjectUtils.isNotEmpty(dialogListener)) {
            dialogListener.dismiss(dialog);
        }
    }

    public void onFinish() {

    }

    public String getProgressInfo() {
        return progressInfo;
    }

    public BaseObserver<T> setProgressInfo(String progressInfo) {
        this.progressInfo = progressInfo;
        return this;
    }

    public DialogListener getDialogListener() {
        return dialogListener;
    }

    public BaseObserver<T> setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }

    public interface DialogListener {
        Dialog showProgress(String progressInfo);

        Dialog showError(String errorInfo);

        void dismiss(Dialog dialog);
    }
}
