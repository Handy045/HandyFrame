package com.handy.frame.rxjava;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;
import com.handy.frame.config.FrameConfig;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 针对BMDP定制的被观察者
 *
 * @author LiuJie https://github.com/Handy045
 * @description 针对BMDP定制的被观察者
 * @date Created in 2018/12/3 16:21
 * @modified By LiuJie
 */
public abstract class BaseObservable<O> extends Observable<O> {

    protected BaseObservable() {
    }

    @Override
    protected void subscribeActual(Observer<? super O> observer) {
    }

    /**
     * 处理接口传入参数及通过getBO调用接口方法
     *
     * @param e 发射器
     * @return 接口返回对象
     * @throws Exception 接口调用异常
     */
    protected abstract O callingInterface(ObservableEmitter<O> e) throws Exception;

    /**
     * 接口返回数据对象无误后，数据解析及处理。
     * 主要用于对接口返回的数据进行校验，可通过e.onNext()、e.onError()方法继续执行。注意：必须要有调用e.onNext()方法
     *
     * @param o 接口返回对象
     * @param e 发射器
     * @throws Exception 接口调用异常
     */
    protected abstract void analyseResultData(O o, ObservableEmitter<O> e) throws Exception;

    public Observable<O> build(@NonNull RxAppCompatActivity activity) {
        return BaseObservable
                .create(new BaseObservableOnSubscribe<>(activity, this))
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private class BaseObservableOnSubscribe<G> implements ObservableOnSubscribe<G> {

        private G g;
        private RxAppCompatActivity activity;
        private BaseObservable<G> baseObservable;

        BaseObservableOnSubscribe(RxAppCompatActivity activity, BaseObservable<G> baseObservable) {
            this.activity = activity;
            this.baseObservable = baseObservable;
        }

        @Override
        public void subscribe(ObservableEmitter<G> e) {
            try {
                if (NetworkUtils.isConnected()) {
                    g = baseObservable.callingInterface(e);
                    if (g == null) {
                        if (!activity.isFinishing()) {
                            e.onError(new Throwable(FrameConfig.PROMPT_ERROR_RESPONSE));
                        }
                    } else {
                        if (!activity.isFinishing()) {
                            baseObservable.analyseResultData(g, e);
                        }
                    }
                } else {
                    if (!activity.isFinishing()) {
                        e.onError(new Throwable(FrameConfig.PROMPT_NULL_NETWORK));
                    }
                }
            } catch (Exception exception) {
                if (!activity.isFinishing()) {
                    e.onError(new Throwable(FrameConfig.PROMPT_ERROR_SERVER + "\n" + (exception.getCause() != null ? exception.getCause().toString() : exception.getMessage())));
                }
            } finally {
                activity = null;
                baseObservable = null;
            }
        }
    }
}
