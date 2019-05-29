package com.handy.frame.api;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.ObservableEmitter;

/**
 * 接口调用者接口
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/2/21 4:49 PM
 * @modified By liujie
 */
public interface CreaterListener<RESPONSE, RESULT> {
    /**
     * 准备提示框控件
     */
    DialogListener initDialogBuilder(@NonNull AppCompatActivity activity);

    /**
     * 调用接口
     */
    RESPONSE callInterface(@NonNull ObservableEmitter<RESPONSE> emitter) throws Exception;

    /**
     * 校验接口返回数据
     */
    void checkResponse(@NonNull ObservableEmitter<RESPONSE> emitter, @NonNull RESPONSE response) throws Exception;

    /**
     * 解析接口返回数据
     */
    RESULT analyzeResponse(@NonNull RESPONSE response) throws Exception;

    /**
     * 执行RxJava
     */
    void execute();
}
