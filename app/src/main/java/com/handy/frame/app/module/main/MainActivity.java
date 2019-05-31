package com.handy.frame.app.module.main;

import android.os.Bundle;
import android.widget.Button;

import com.handy.frame.app.R;
import com.handy.frame.app.module.detail.DetailActivity;
import com.handy.frame.base.FrameActivity;

public class MainActivity extends FrameActivity {

    Button btnDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDetail = findViewById(R.id.btn_detail);
        btnDetail.setOnClickListener(v -> DetailActivity.doIntent(activity, false));
    }
}
