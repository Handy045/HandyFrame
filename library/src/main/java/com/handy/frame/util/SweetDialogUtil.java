package com.handy.frame.util;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cazaea.sweetalert.SweetAlertDialog;

/**
 * 提示框创建工具类
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/4/17 8:49 AM
 * @modified By liujie
 */
public class SweetDialogUtil {

    private static SweetDialogUtil sweetDialogUtil = null;

    private boolean isShowLater = false;
    private SweetAlertDialog sweetAlertDialog;

    /**
     * 点击手机返回键可否关闭对话框
     */
    private boolean backDismissOthers = true;
    /**
     * 点击手机返回键可否关闭进度框
     */
    private boolean backDismissProgress = false;
    /**
     * 点击外部屏幕可否关闭对话框
     */
    private boolean touchDismissOthers = true;
    /**
     * 点击外部屏幕可否关闭进度框
     */
    private boolean touchDismissProgress = false;

    private SweetDialogUtil() {
    }

    public synchronized static SweetDialogUtil getInstance() {
        if (sweetDialogUtil == null) {
            sweetDialogUtil = new SweetDialogUtil();
        }
        sweetDialogUtil.backDismissOthers = true;
        sweetDialogUtil.backDismissProgress = false;
        sweetDialogUtil.touchDismissOthers = true;
        sweetDialogUtil.touchDismissProgress = false;
        return sweetDialogUtil;
    }

    /**
     * 设置是否要手动显示Dialog
     *
     * @param isShowLater true:手动显示(.show())
     */
    public SweetDialogUtil setShowLater(boolean isShowLater) {
        this.isShowLater = isShowLater;
        return this;
    }

    /**
     * 设置进度框是否可以手动取消
     *
     * @param backFinishProgress   返回键
     * @param touchDismissProgress 进度框外部点击
     */
    public SweetDialogUtil setProgressCancelable(boolean backFinishProgress, boolean touchDismissProgress) {
        this.backDismissProgress = backFinishProgress;
        this.touchDismissProgress = touchDismissProgress;
        return this;
    }

    /**
     * 设置其他对话框框是否可以手动取消
     *
     * @param backDismissOthers  返回键
     * @param touchDismissOthers 进度框外部点击
     */
    public SweetDialogUtil setOthersCancelable(boolean backDismissOthers, boolean touchDismissOthers) {
        this.backDismissOthers = backDismissOthers;
        this.touchDismissOthers = touchDismissOthers;
        return this;
    }

    /**
     * 显示进度框
     *
     * @param title 标题内容
     */
    public SweetAlertDialog showProgress(String title) {
        dismiss();
        return showDialog(SweetAlertDialog.PROGRESS_TYPE, title, null, null, null, null, null);
    }

    /**
     * 显示成功提示框
     *
     * @param title        标题内容
     * @param confirmText  确认按钮内容
     * @param successClick 确认按钮点击事件
     */
    public SweetAlertDialog showSuccess(String title, String confirmText, SweetAlertDialog.OnSweetClickListener successClick) {
        dismiss();
        return showDialog(SweetAlertDialog.SUCCESS_TYPE, title, null, confirmText, null, successClick, null);
    }

    /**
     * 警告提示框
     *
     * @param title       标题内容
     * @param content     正文内容
     * @param confirmText 确认按钮内容
     * @param errorClick  确认按钮点击事件
     */
    public SweetAlertDialog showWarning(String title, String content, String confirmText, SweetAlertDialog.OnSweetClickListener errorClick) {
        dismiss();
        return showDialog(SweetAlertDialog.WARNING_TYPE, title, content, confirmText, null, errorClick, null);
    }

    /**
     * 失败提示框
     *
     * @param title       标题内容
     * @param content     正文内容
     * @param confirmText 确认按钮内容
     * @param errorClick  确认按钮点击事件
     */
    public SweetAlertDialog showError(String title, String content, String confirmText, SweetAlertDialog.OnSweetClickListener errorClick) {
        dismiss();
        return showDialog(SweetAlertDialog.ERROR_TYPE, title, content, confirmText, null, errorClick, null);
    }

    /**
     * 显示默认提示框
     *
     * @param title        标题内容
     * @param content      正文内容
     * @param confirmText  确认按钮内容
     * @param cancelText   取消按钮内容
     * @param confirmClick 确认按钮点击事件
     * @param cancelClick  取消按钮点击事件
     */
    public SweetAlertDialog showNormal(String title, String content, String confirmText, String cancelText, SweetAlertDialog.OnSweetClickListener confirmClick, SweetAlertDialog.OnSweetClickListener cancelClick) {
        dismiss();
        return showDialog(SweetAlertDialog.NORMAL_TYPE, title, content, confirmText, cancelText, confirmClick, cancelClick);
    }

    /**
     * 显示进度框
     */
    private SweetAlertDialog showDialog(int dialogType, String title, String content, String confirmText, String cancelText, SweetAlertDialog.OnSweetClickListener confirmClick, SweetAlertDialog.OnSweetClickListener cancelClick) {
        if (SweetDialogClient.getActivityNow() != null) {
            if (!SweetDialogClient.getActivityNow().isFinishing()) {
                switch (dialogType) {
                    case SweetAlertDialog.PROGRESS_TYPE:
                        sweetAlertDialog = new SweetAlertDialog(SweetDialogClient.getActivityNow(), SweetAlertDialog.PROGRESS_TYPE);
                        break;
                    case SweetAlertDialog.SUCCESS_TYPE:
                        sweetAlertDialog = new SweetAlertDialog(SweetDialogClient.getActivityNow(), SweetAlertDialog.SUCCESS_TYPE);
                        break;
                    case SweetAlertDialog.WARNING_TYPE:
                        sweetAlertDialog = new SweetAlertDialog(SweetDialogClient.getActivityNow(), SweetAlertDialog.WARNING_TYPE);
                        break;
                    case SweetAlertDialog.ERROR_TYPE:
                        sweetAlertDialog = new SweetAlertDialog(SweetDialogClient.getActivityNow(), SweetAlertDialog.ERROR_TYPE);
                        break;
                    case SweetAlertDialog.NORMAL_TYPE:
                        sweetAlertDialog = new SweetAlertDialog(SweetDialogClient.getActivityNow(), SweetAlertDialog.NORMAL_TYPE);
                        break;
                    default:
                        break;
                }
                if (dialogType == SweetAlertDialog.PROGRESS_TYPE) {
                    sweetAlertDialog.setCancelable(backDismissProgress);
                    sweetAlertDialog.setCanceledOnTouchOutside(touchDismissProgress);
                } else {
                    sweetAlertDialog.setCancelable(backDismissOthers);
                    sweetAlertDialog.setCanceledOnTouchOutside(touchDismissOthers);
                }

                sweetAlertDialog.setTitleText(StringUtils.isEmpty(title) ? null : title);
                sweetAlertDialog.setContentText(StringUtils.isEmpty(content) ? null : content);
                sweetAlertDialog.setCancelText(StringUtils.isEmpty(cancelText) ? null : cancelText);
                sweetAlertDialog.setConfirmText(StringUtils.isEmpty(confirmText) ? null : confirmText);

                if (confirmClick != null) {
                    sweetAlertDialog.setConfirmClickListener(confirmClick);
                }
                if (cancelClick != null) {
                    sweetAlertDialog.setCancelClickListener(cancelClick);
                }
                if (!isShowLater) {
                    sweetAlertDialog.show();
                }
                return sweetAlertDialog;
            }
        } else {
            LogUtils.e("SweetDialogClient未连接，请在Activity的onCreate中执行SweetDialogClient.register()");
        }
        return null;
    }

    /**
     * 关闭全部进度框
     */
    public void dismiss() {
        if (SweetDialogClient.getActivityNow() != null) {
            if (!SweetDialogClient.getActivityNow().isFinishing()) {
                try {
                    if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
                        try {
                            sweetAlertDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            sweetAlertDialog = null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            LogUtils.e("SweetDialogClient未连接，请在Activity的onCreate中执行SweetDialogClient.register()");
        }
    }
}
