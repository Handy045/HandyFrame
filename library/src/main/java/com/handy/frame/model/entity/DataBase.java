package com.handy.frame.model.entity;

import com.handy.frame.BuildConfig;
import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 数据库配置
 *
 * @author LiuJie https://github.com/Handy045
 * @description functional description.
 * @date Created in 2019/2/27 5:09 PM
 * @modified By liujie
 */
@Database(version = DataBase.VERSION, name = DataBase.NAME)
public class DataBase {
    static final int VERSION = 1;
    static final String NAME = BuildConfig.HandyFrame;
}
