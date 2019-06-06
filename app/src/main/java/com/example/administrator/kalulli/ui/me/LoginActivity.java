package com.example.administrator.kalulli.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forget_login_text)
    TextView forgetLoginText;
    @BindView(R.id.register_btn_text)
    TextView registerBtnText;
    @BindView(R.id.back_login_image)
    ImageView backLoginImage;
    @BindView(R.id.edit_username_login)
    EditText editUsernameLogin;
    @BindView(R.id.edit_password_login)
    EditText editPasswordLogin;


    @OnClick(R.id.login)
    public void onLoginClicked() {
        String phoneNumber = editUsernameLogin.getText().toString().trim();
        String passWord = editPasswordLogin.getText().toString().trim();
        if (passWord == null ||passWord.equals("")) {
            Toast.makeText(mActivity, "请输入密码", Toast.LENGTH_LONG).show();
        }else {
            AVUser.loginByMobilePhoneNumberInBackground(phoneNumber, passWord, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        Log.i(TAG, "done: 登陆成功" + AVUser.getCurrentUser().getUsername());
                        toast("登陆成功", 1);
                        setResult(1000);
                        mActivity.finish();

                    } else {
                        toast("登录失败" + e.getMessage(), 1);
                    }
                }
            });
        }
    }
    @OnClick(R.id.forget_login_text)
    public void onForgetLoginTextClicked() {
        Intent intent = new Intent(mActivity,ForgetActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.register_btn_text)
    public void onRegisterBtnTextClicked() {
        Intent intent = new Intent(mActivity, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick(R.id.back_login_image)
    public void onViewClicked() {
        finish();
    }

}
