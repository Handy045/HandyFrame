package com.handy.frame.app.module.detail.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handy.frame.app.R;
import com.handy.frame.base.FrameFragment;

/**
 * FileName
 *
 * @author LiuJie https://github.com/Handy045
 * @description File Description
 * @date Created in 2019-05-31 15:57
 * @modified By liujie
 */
public class ListFragment extends FrameFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, null);
    }
}
