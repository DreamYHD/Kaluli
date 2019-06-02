package com.example.administrator.kalulli.ui.suggest;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.utils.TableUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowFoodActivity extends BaseActivity {

    private static final String TAG = "ShowFoodActivity";
    @BindView(R.id.food_name)
    TextView foodName;
    @BindView(R.id.food_img)
    ImageView foodImg;
    @BindView(R.id.food_type)
    TextView foodType;
    @BindView(R.id.food_affect)
    TextView foodAffect;
    @BindView(R.id.food_calorie)
    TextView foodCalorie;
    @BindView(R.id.food_info)
    TextView foodInfo;
    private String objectId = "";
    private String table = "";

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        objectId = getIntent().getStringExtra("objectId");
        table = getIntent().getStringExtra("table");
        final AVObject avObject = AVObject.createWithoutData(table, objectId);
        avObject.fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                Log.i(TAG, "logicActivity: " + avObject.getObjectId());
                foodName.setText(avObject.get(TableUtil.FOOD_NAME).toString());
                foodAffect.setText("功效:  "+avObject.get(TableUtil.FOOD_AFFECT).toString());
                foodCalorie.setText("卡路里含量:"+avObject.get(TableUtil.FOOD_CALORIE).toString()+"千卡");
                foodType.setText("#"+avObject.get(TableUtil.FOOD_TYPE).toString());
                foodInfo.setText(avObject.get(TableUtil.FOOD_DESCRIPTION).toString());
                Glide.with(mActivity)
                        .load(avObject.get(TableUtil.FOOD_URL))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(foodImg);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_food;
    }

}
