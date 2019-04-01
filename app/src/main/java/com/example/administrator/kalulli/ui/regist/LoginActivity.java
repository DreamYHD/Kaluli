package com.example.administrator.kalulli.ui.regist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forget_login_text)
    TextView forgetLoginText;
    @BindView(R.id.register_btn_text)
    TextView registerBtnText;
    @BindView(R.id.back_login_image)
    ImageView backLoginImage;


    @OnClick(R.id.login)
    public void onLoginClicked() {
    }

    @OnClick(R.id.forget_login_text)
    public void onForgetLoginTextClicked() {
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
