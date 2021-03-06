package com.handy.frame.api;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.handy.frame.config.FrameConfig;
import com.rxjava.rxlife.RxLife;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 接口对象创建者父类
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/2/21 3:58 PM
 * @modified By liujie
 */
public abstract class BaseApiCreater<RESPONSE, RESULT> implements CreaterListener<RESPONSE, RESULT>, Serializable {

    private AppCompatActivity activity;

    private String serviceTag = "";
    private String progressInfo = null;
    private DialogListener dialogListener = null;
    private ResultListener<RESULT> resultListener = null;

    public BaseApiCreater(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        this.dialogListener = initDialogBuilder(activity);
    }

    @Override
    public void execute() {
        if (ObjectUtils.isNotEmpty(resultListener)) {
            this.resultListener.setDialogListener(this.dialogListener);
        } else {
            LogUtils.e("lease set the resultListener first.");
        }

        Observable.create((ObservableOnSubscribe<RESPONSE>) emitter -> {
            try {
                if (NetworkUtils.isConnected()) {
                    RESPONSE response = callInterface(emitter);
                    if (response != null) {
                        try {
                            if (!activity.isFinishing()) {
                                checkResponse(emitter, response);
                            }
                        } catch (Throwable exception) {
                            LogUtils.e(exception);
                            if (!activity.isFinishing()) {
                                emitter.onError(new Throwable(serviceTag + FrameConfig.PROMPT_ERROR_ANALYSIS));
                            }
                        }
                    } else {
                        if (!activity.isFinishing()) {
                            emitter.onError(new Throwable(serviceTag + FrameConfig.PROMPT_ERROR_RESPONSE));
                        }
                    }
                } else {
                    if (!activity.isFinishing()) {
                        emitter.onError(new Throwable(FrameConfig.PROMPT_NULL_NETWORK));
                    }
                }
            } catch (Exception exception) {
                LogUtils.e(exception);
                if (!activity.isFinishing()) {
                    emitter.onError(new Throwable(serviceTag + FrameConfig.PROMPT_ERROR_SERVER));
                }
            } finally {
                if (!activity.isFinishing()) {
                    emitter.onComplete();
                }
            }
        }).map(this::analyzeResponse).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(RxLife.as(activity)).subscribe(new Observer<RESULT>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (activity.isFinishing()) {
                    return;
                }
                if (ObjectUtils.isNotEmpty(dialogListener) && ObjectUtils.isNotEmpty(progressInfo)) {
                    dialogListener.showProgress(progressInfo);
                }
            }

            @Override
            public void onNext(RESULT result) {
                if (activity.isFinishing()) {
                    return;
                }
                if (ObjectUtils.isNotEmpty(dialogListener)) {
                    dialogListener.dismiss();
                }

                if (ObjectUtils.isNotEmpty(resultListener)) {
                    resultListener.onSuccess(result);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (activity.isFinishing()) {
                    return;
                }
                if (ObjectUtils.isNotEmpty(dialogListener)) {
                    dialogListener.dismiss();
                }

                if (ObjectUtils.isNotEmpty(resultListener)) {
                    resultListener.onFailed(e);
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public DialogListener initDialogBuilder(@NonNull AppCompatActivity activity) {
        return new BaseDialogBuilder(activity);
    }

    public String getServiceTag() {
        return serviceTag;
    }

    public BaseApiCreater<RESPONSE, RESULT> setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
        return this;
    }

    public String getProgressInfo() {
        return progressInfo;
    }

    public BaseApiCreater<RESPONSE, RESULT> setProgressInfo(String progressInfo) {
        this.progressInfo = progressInfo;
        return this;
    }

    public ResultListener<RESULT> getResultListener() {
        return resultListener;
    }

    public BaseApiCreater<RESPONSE, RESULT> setResultListener(ResultListener<RESULT> resultListener) {
        this.resultListener = resultListener;
        return this;
    }
}