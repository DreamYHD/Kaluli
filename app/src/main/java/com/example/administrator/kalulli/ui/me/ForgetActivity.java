package com.example.administrator.kalulli.ui.me;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetActivity extends BaseActivity {

    private static final String TAG = "ForgetActivity";
    @BindView(R.id.back_register_image)
    ImageView backRegisterImage;
    @BindView(R.id.username_register)
    TextView usernameRegister;
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.sms_code)
    EditText smsCode;
    @BindView(R.id.newPassEdit)
    EditText newPassEdit;
    @BindView(R.id.sendBtn)
    Button sendBtn;
    @BindView(R.id.getSms_btn)
    Button getSmsBtn;
    private Boolean isSms = false;
    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }



    @OnClick(R.id.back_register_image)
    public void onBackRegisterImageClicked() {
        mActivity.finish();
    }

    @OnClick(R.id.sendBtn)
    public void onSendBtnClicked() {
        if (isSms){
            AVUser.resetPasswordBySmsCodeInBackground(smsCode.getText().toString().trim(), newPassEdit.getText().toString(), new UpdatePasswordCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        mActivity.finish();
                        AVUser.logOut();
                        toast("新密码设置成功",1);
                    } else {
                        toast("验证码错误",0);
                    }
                }
            });
        }
    }
    @OnClick(R.id.getSms_btn)
    public void onGetSmsBtnClicked() {
        AVUser.requestMobilePhoneVerifyInBackground(phoneNumber.getText().toString(), new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    isSms = true;
                    toast("验证码发送成功",1);
                    Log.i(TAG, "done: mobile get sms ");
                } else {
                    toast("验证码发送失败,请检查手机号",1);
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }
}
