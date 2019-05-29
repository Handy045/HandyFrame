package com.handy.frame.api;

/**
 * 接口提示弹框接口
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/2/21 4:49 PM
 * @modified By liujie
 */
public interface DialogListener {
    void showProgress(String progressInfo);

    void showSuccess(String successInfo);

    void showError(String errorInfo);

    void dismiss();
}
