package com.handy.frame.module.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.handy.frame.R;
import com.handy.frame.base.FrameActivity;
import com.handy.frame.util.VerificationCodeUtils;

import java.util.HashMap;


/**
 * 登录界面
 *
 * @author LiuJie https://github.com/Handy045
 * @description 用户名、用户名+密码、或含有验证码的登录
 * @date Created in 2019-04-18 16:26
 * @modified By liujie
 */
public abstract class BaseLoginActivity extends FrameActivity {

    {
        isUseSwipeBackFinish = false;
    }

    ImageView ivLogo;
    TextView tvAppname;
    RelativeLayout clLogo;
    ImageView ivAccount;
    EditText edtAccount;
    View lineAccount;
    View lineCenter1;
    ImageView ivPassword;
    EditText edtPassword;
    View linePassword;
    View lineCenter2;
    ImageView ivVerification;
    EditText edtVerification;
    ConstraintLayout clInfo;
    Button btnSignin;

    private Bitmap imgVerification;
    private String strVerification;
    public boolean isNeedPassword = true;
    public boolean isNeedVerification = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewHDB(resetContentView());
        ivLogo = findViewById(R.id.iv_logo);
        tvAppname = findViewById(R.id.tv_appname);
        clLogo = findViewById(R.id.cl_logo);
        ivAccount = findViewById(R.id.iv_account);
        edtAccount = findViewById(R.id.edt_account);
        lineAccount = findViewById(R.id.line_account);
        lineCenter1 = findViewById(R.id.line_center1);
        ivPassword = findViewById(R.id.iv_password);
        edtPassword = findViewById(R.id.edt_password);
        linePassword = findViewById(R.id.line_password);
        lineCenter2 = findViewById(R.id.line_center2);
        ivVerification = findViewById(R.id.iv_verification);
        edtVerification = findViewById(R.id.edt_verification);
        clInfo = findViewById(R.id.cl_info);
        btnSignin = findViewById(R.id.btn_signin);

        if (!isNeedPassword) {
            lineCenter1.setVisibility(View.GONE);
            ivPassword.setVisibility(View.GONE);
            edtPassword.setVisibility(View.GONE);
            linePassword.setVisibility(View.GONE);
        }
        if (!isNeedVerification) {
            lineCenter2.setVisibility(View.GONE);
            ivVerification.setVisibility(View.GONE);
            edtVerification.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViewHDB(@Nullable Bundle savedInstanceState) {
        super.initViewHDB(savedInstanceState);
        HashMap<String, Object> verificationCode = VerificationCodeUtils.getInstance().getVerificationCode();
        strVerification = (String) verificationCode.get("code");
        imgVerification = (Bitmap) verificationCode.get("bitmap");
        ivVerification.setImageBitmap(imgVerification);

        ivVerification.setOnClickListener(v -> {
            HashMap<String, Object> verificationCode1 = VerificationCodeUtils.getInstance().getVerificationCode();
            strVerification = (String) verificationCode1.get("code");
            imgVerification = (Bitmap) verificationCode1.get("bitmap");
            ivVerification.setImageBitmap(imgVerification);
        });

        btnSignin.setOnClickListener(v -> {
            if (ObjectUtils.isEmpty(edtAccount.getText().toString())) {
                ToastUtils.showShort(edtAccount.getHint().toString());
                return;
            }
            if (isNeedPassword && ObjectUtils.isEmpty(edtPassword.getText().toString())) {
                ToastUtils.showShort(edtPassword.getHint().toString());
                return;
            }
            if (isNeedVerification) {
                String verification = edtVerification.getText().toString().trim();
                if (ObjectUtils.isEmpty(verification)) {
                    ToastUtils.showShort("请输入验证码");
                    return;
                }
                String s1 = verification.toUpperCase();
                String s2 = strVerification.toUpperCase();
                if (!s1.equals(s2)) {
                    ToastUtils.showShort("验证码输入有误");
                    return;
                }
            }
            onLoginListener(edtAccount.getText().toString().trim(), edtPassword.getText().toString().trim());
        });
    }

    //============================================================
    //  功能开放
    //============================================================

    @LayoutRes
    protected int resetContentView() {
        return R.layout.hf_activity_login;
    }

    public EditText getEdtAccount() {
        return edtAccount;
    }

    public EditText getEdtPassword() {
        return edtPassword;
    }

    public BaseLoginActivity setAppname(@NonNull String appName) {
        if (ObjectUtils.isNotEmpty(appName)) {
            tvAppname.setText(appName);
        }
        return this;
    }

    //============================================================
    //  子类需要实现的抽象方法
    //============================================================

    protected abstract void onLoginListener(String username, String password);
}
