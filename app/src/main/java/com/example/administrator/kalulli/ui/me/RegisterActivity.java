package com.example.administrator.kalulli.ui.me;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.utils.TableUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.back_register_image)
    ImageView backRegisterImage;
    @BindView(R.id.username_register)
    EditText usernameRegister;
    @BindView(R.id.hint1)
    TextView hint1;
    @BindView(R.id.password_register)
    EditText passwordRegister;
    @BindView(R.id.hint2)
    TextView hint2;
    @BindView(R.id.height_register)
    EditText heightRegister;
    @BindView(R.id.hint3)
    TextView hint3;
    @BindView(R.id.weight_register)
    EditText weightRegister;
    @BindView(R.id.hint4)
    TextView hint4;
    @BindView(R.id.sex_register)
    EditText sexRegister;
    @BindView(R.id.hint5)
    TextView hint5;
    @BindView(R.id.major_register)
    EditText majorRegister;
    @BindView(R.id.hint7)
    TextView hint7;
    @BindView(R.id.age_register)
    EditText ageRegister;
    @BindView(R.id.hint8)
    TextView hint8;
    @BindView(R.id.love_register)
    EditText loveRegister;
    @BindView(R.id.hint9)
    TextView hint9;
    @BindView(R.id.stick_register)
    EditText stickRegister;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.telephone_register)
    EditText telephoneRegister;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.back_register_image)
    public void onBackRegisterImageClicked() {
        mActivity.finish();
    }

    @OnClick(R.id.register_btn)
    public void onRegisterBtnClicked() {
        String userName = usernameRegister.getText().toString().trim();
        String passWord = passwordRegister.getText().toString().trim();
        String major = majorRegister.getText().toString().trim();
        String telephone = telephoneRegister.getText().toString();
        String sex = sexRegister.getText().toString();
        String height = heightRegister.getText().toString();
        String weight = weightRegister.getText().toString();
        String love = loveRegister.getText().toString();
        String stick = stickRegister.getText().toString();
        String age = ageRegister.getText().toString();

        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setMobilePhoneNumber(telephone);
        user.setPassword(passWord);
        user.setUsername(userName);
        user.put(TableUtil.USER_SEX,sex);
        user.put(TableUtil.USER_HEIGHT,height);
        user.put(TableUtil.USER_WEIGHT,weight);
        user.put(TableUtil.USER_AGE,age);
        user.put(TableUtil.USER_MAJOR,major);
        user.put(TableUtil.USER_LOVE,love);
        user.put(TableUtil.USER_STICK,stick);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
               if (e == null){
                   toast("注册成功",1);
                   mActivity.finish();
               }else {
                   toast("注册失败"+e.getMessage(),1);
               }
            }
        });

    }
}
