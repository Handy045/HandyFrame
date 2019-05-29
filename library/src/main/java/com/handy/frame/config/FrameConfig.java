package com.handy.frame.config;

/**
 * Application Static Constant&Variable
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/2/27 4:53 PM
 * @modified By liujie
 */
public class FrameConfig {

    //============================================================
    //  接口调用结果提示
    //============================================================

    public static final String PROMPT_LOADING = "数据获取中，请稍候";
    public static final String PROMPT_PREPARING = "数据准备中，请稍候";
    public static final String PROMPT_SUBMITTING = "数据提交中，请稍候";

    public static final String PROMPT_ERROR_TIMEOUT = "连接超时，请稍后再试";
    public static final String PROMPT_ERROR_REQUEST = "请求数据错误，请联系管理员";
    public static final String PROMPT_ERROR_ANALYSIS = "返回数据解析失败，请联系管理员";
    public static final String PROMPT_ERROR_RESPONSE = "返回数据错误，请联系管理员";
    public static final String PROMPT_ERROR_SERVER = "服务器连接失败，请联系管理员";

    public static final String PROMPT_EMPTY_RESPONSE = "返回数据为空";
    public static final String PROMPT_EMPTY_RESPONSE_NOMORE = "暂无更多数据";

    public static final String PROMPT_NULL_NETWORK = "网络连接不可用，请检查网络设置";
    public static final String PROMPT_NULL_LOCATION = "未获取到当前位置信息，请稍后再试";
    public static final String PROMPT_NULL_FAILEDMESSAGES = "接口调用失败原因为空，请联系管理员";

    //============================================================
    //  带回调的Intent跳转编码配置
    //============================================================

    /**
     * 跳转至系统设置界面请求编码
     */
    public static final int REQUESTCODE_SYSTEM_SETTING = 999;

    //============================================================
    //  SPUtils缓存配置
    //============================================================

    /**
     * Sharepreference缓存文件名
     */
    public static final String SP_NAME_COMMON = "SP_NAME_COMMON";

    //============================================================
    //  SPUtils相关配置键值
    //============================================================

    /**
     * 引导界面初始化标记
     */
    public static final String SP_KEY_APPLICATION_STARTED = "SP_KEY_APPLICATION_STARTED";
}
