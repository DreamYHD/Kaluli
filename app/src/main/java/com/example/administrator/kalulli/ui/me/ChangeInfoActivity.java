package com.example.administrator.kalulli.ui.me;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.utils.TableUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeInfoActivity extends BaseActivity {


    private static final String TAG = "ChangeInfoActivity";
    @BindView(R.id.back_register_image)
    ImageView backRegisterImage;
    @BindView(R.id.height_register)
    EditText heightRegister;
    @BindView(R.id.weight_register)
    EditText weightRegister;
    @BindView(R.id.major_register)
    EditText majorRegister;
    @BindView(R.id.age_register)
    EditText ageRegister;
    @BindView(R.id.love_register)
    EditText loveRegister;
    @BindView(R.id.stick_register)
    EditText stickRegister;
    @BindView(R.id.change_btn)
    Button changeBtn;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_info;
    }
    @OnClick(R.id.back_register_image)
    public void onBackRegisterImageClicked() {
        mActivity.finish();
    }

    @OnClick(R.id.change_btn)
    public void onChangeBtnClicked() {
        AVUser avUser = AVUser.getCurrentUser();
        String major = majorRegister.getText().toString().trim();
        String height = heightRegister.getText().toString();
        String weight = weightRegister.getText().toString();
        String love = loveRegister.getText().toString();
        String stick = stickRegister.getText().toString();
        String age = ageRegister.getText().toString();
        if (major !=null && !major.equals("")){
            avUser.put(TableUtil.USER_MAJOR,major);
        }
        if (height !=null && !height.equals("")){
            avUser.put(TableUtil.USER_HEIGHT,height);
        }
        if (weight !=null && !weight.equals("")){
            avUser.put(TableUtil.USER_WEIGHT,weight);
        }
        if (love !=null && !love.equals("")){
            avUser.put(TableUtil.USER_LOVE,love);
        }
        if (stick !=null && !stick.equals("")){
            avUser.put(TableUtil.USER_STICK,stick);
        }
        if (age !=null && !age.equals("")){
            avUser.put(TableUtil.USER_AGE,age);
        }
        avUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if ( e == null){
                    toast("修改成功",0);
                    mActivity.finish();
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }
}
