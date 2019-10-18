package com.testone.demo.activity.mqtt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.testone.demo.R;
import com.testone.demo.activity.BaseActivity;
import com.testone.demo.utils.ToastUtil;

public class LoginChatBoxActivity extends BaseActivity implements View.OnClickListener {

    private boolean inputShowLoginName;
    private boolean inputShowPassword;
    private EditText loginName;
    private EditText loginPwd;
    private ImageView loginNameEyesIV;
    private ImageView passwordEyesIV;
    private TextView loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_chatbox);

        loginName = findViewById(R.id.login_name_input);
        loginName.addTextChangedListener(new MXTextWatcher(loginName));
        loginNameEyesIV = findViewById(R.id.iv_plainttext_loginname);
        RelativeLayout loginNameEyes = findViewById(R.id.rl_loginname_plaintext);
        loginNameEyes.setOnClickListener(this);

        loginPwd = findViewById(R.id.login_pwd_input);
        loginPwd.addTextChangedListener(new MXTextWatcher(loginPwd));
        passwordEyesIV = findViewById(R.id.iv_plainttext_password);
        RelativeLayout loginPasswordEyes = findViewById(R.id.rl_password_plaintext);
        loginPasswordEyes.setOnClickListener(this);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_loginname_plaintext:
                // 输入新密码小眼睛
                inputShowLoginName = !inputShowLoginName;
                swichSeePswShow(loginName, loginNameEyesIV, inputShowLoginName);

                break;

            case R.id.rl_password_plaintext:
                // 确认密码小眼睛
                inputShowPassword = !inputShowPassword;
                swichSeePswShow(loginPwd, passwordEyesIV, inputShowPassword);
                break;

            case R.id.login_button:
                if (TextUtils.equals(loginName.getText().toString(), loginPwd.getText().toString())){
                    Intent intent = new Intent();
                    intent.setClass(this, ChatBoxesActivity.class);
                    intent.putExtra("loginname", loginName.getText().toString());
                    intent.putExtra("password", loginPwd.getText().toString());
                    startActivity(intent);
                } else {
                    ToastUtil.showToast(this, "帐号或密码错误");
                }
                break;
        }
    }

    /**
     * 展示小眼睛的UI
     *
     * @param pwdEt        EditText
     * @param seePwdImg    ImageView
     * @param showPassword Boolean
     */
    private void swichSeePswShow(EditText pwdEt, ImageView seePwdImg, boolean showPassword) {
        if (showPassword) {
            seePwdImg.setSelected(true);
            pwdEt.setInputType(InputType.TYPE_CLASS_TEXT);
            pwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            seePwdImg.setSelected(false);
            pwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            pwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        pwdEt.setSelection(pwdEt.getText().length());
    }


    private int editTextCount = 0;

    /**
     *
     */
    private class MXTextWatcher implements TextWatcher {
        private EditText editText = null;

        private MXTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (editText == loginName) {
                if (TextUtils.isEmpty(loginName.getText()))
                    loginButton.setEnabled(false);
                else {
                    editTextCount = editTextCount - 1;//1
                }
            }

            if (editText == loginPwd) {
                if (TextUtils.isEmpty(loginPwd.getText()))
                    loginButton.setEnabled(false);
                else {
                    editTextCount = editTextCount - 1;//1
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String inputNewPwdString = loginName.getText().toString();
            String inputNewPwdAgainString = loginPwd.getText().toString();

            if (editText == loginName) {
                if (TextUtils.isEmpty(inputNewPwdString))
                    loginButton.setEnabled(false);
                else {
                    editTextCount = editTextCount + 1;//1
                }
            }

            if (editText == loginPwd) {
                if (TextUtils.isEmpty(inputNewPwdAgainString))
                    loginButton.setEnabled(false);
                else {
                    editTextCount = editTextCount + 1;//1
                }
            }

            if (editTextCount == 2 && inputNewPwdString.length() > 5 && inputNewPwdAgainString.length() > 5) {
                loginButton.setEnabled(true);
            } else {
                loginButton.setEnabled(false);
            }
        }
    }
}
