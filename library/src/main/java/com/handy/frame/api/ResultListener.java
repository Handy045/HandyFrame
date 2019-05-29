package com.handy.frame.api;

import android.support.annotation.NonNull;

/**
 * 接口结果返回接口
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/2/21 4:49 PM
 * @modified By liujie
 */
public interface ResultListener<RESULT> {

    void setDialogListener(DialogListener dialogBuilder);

    /**
     * 正常处理
     */
    void onSuccess(@NonNull RESULT result);

    /**
     * 异常处理
     */
    void onFailed(@NonNull Throwable throwable);

    /**
     * 处理结束
     */
    void onFinish();
}
