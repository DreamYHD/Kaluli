package com.example.administrator.kalulli.ui.suggest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.utils.HealthUtil;
import com.example.administrator.kalulli.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuggestDailyActivity extends BaseActivity {

    private static final String TAG = "SuggestDailyActivity";
    @BindView(R.id.back_daily_img)
    ImageView backDailyImg;
    @BindView(R.id.morning_image)
    ImageView morningImage;
    @BindView(R.id.morning_name)
    TextView morningName;
    @BindView(R.id.morning_number)
    TextView morningNumber;
    @BindView(R.id.afternoon1_image)
    ImageView afternoon1Image;
    @BindView(R.id.afternoon1_name)
    TextView afternoon1Name;
    @BindView(R.id.afternoon1_number)
    TextView afternoon1Number;
    @BindView(R.id.afternoon2_image)
    ImageView afternoon2Image;
    @BindView(R.id.afternoon2_name)
    TextView afternoon2Name;
    @BindView(R.id.afternoon2_number)
    TextView afternoon2Number;
    @BindView(R.id.evening_image)
    ImageView eveningImage;
    @BindView(R.id.evening_name)
    TextView eveningName;
    @BindView(R.id.evening_number)
    TextView eveningNumber;
    @BindView(R.id.sum_tv)
    TextView sumTv;
    private List<AVObject> alllist = new ArrayList<>();
    private boolean morningIs = false;
    private boolean afternoon1Is = false;
    private boolean afternoon2Is = false;
    private boolean eveningIs = false;
    private double sum;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AVUser avUser = AVUser.getCurrentUser();
            String healthType = HealthUtil.getHealth(avUser.get(TableUtil.USER_WEIGHT).toString(), avUser.get(TableUtil.USER_HEIGHT).toString());

            for (int i = 0; i < alllist.size(); i++) {
                AVObject avObject = alllist.get(i);
                if (healthType.equals("胖")) {
                    String s = avObject.get(TableUtil.FOOD_NAME).toString();
                    if (s.contains("肉")) {
                        continue;
                    }
                }
                if (avObject.get(TableUtil.DAILY_FOOD_TYPE).equals("早餐") && !morningIs) {
                    morningIs = true;
                    morningName.setText(avObject.get(TableUtil.FOOD_NAME).toString());
                    morningNumber.setText(avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString());
                    sum += Double.parseDouble(avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString());
                    Glide.with(mActivity)
                            .load(avObject.get(TableUtil.DAILY_FOOD_URL))
                            .placeholder(R.mipmap.ic_launcher)
                            .into(morningImage);
                } else if (avObject.get(TableUtil.DAILY_FOOD_TYPE).equals("通用") && !afternoon1Is
                        && (avObject.get(TableUtil.FOOD_NAME).toString().equals("米饭") ||
                        avObject.get(TableUtil.FOOD_NAME).toString().equals("馒头"))) {
                    String s = avObject.get(TableUtil.FOOD_NAME).toString();
                    afternoon1Is = true;
                    afternoon1Name.setText(avObject.get(TableUtil.FOOD_NAME).toString());
                    afternoon1Number.setText(avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString());
                    sum += Double.parseDouble(avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString());

                    Glide.with(mActivity)
                            .load(avObject.get(TableUtil.DAILY_FOOD_URL))
                            .placeholder(R.mipmap.ic_launcher)
                            .into(afternoon1Image);
                } else if (avObject.get(TableUtil.DAILY_FOOD_TYPE).equals("通用") && !eveningIs) {
                    eveningIs = true;
                    eveningName.setText(avObject.get(TableUtil.FOOD_NAME).toString());
                    eveningNumber.setText(avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString());
                    sum += Double.parseDouble(avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString());

                    Glide.with(mActivity)
                            .load(avObject.get(TableUtil.DAILY_FOOD_URL))
                            .placeholder(R.mipmap.ic_launcher)
                            .into(eveningImage);
                } else if (avObject.get(TableUtil.DAILY_FOOD_TYPE).equals("通用") && !afternoon2Is) {
                    String s = avObject.get(TableUtil.FOOD_NAME).toString();
                    if (s.contains("饺子")) {
                        continue;
                    }
                    afternoon2Is = true;
                    afternoon2Name.setText(avObject.get(TableUtil.FOOD_NAME).toString());
                    afternoon2Number.setText(avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString());
                    sum += Double.parseDouble(avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString());
                    Glide.with(mActivity)
                            .load(avObject.get(TableUtil.DAILY_FOOD_URL))
                            .placeholder(R.mipmap.ic_launcher)
                            .into(afternoon2Image);
                }
            }
            sumTv.setText("预计 "+sum+" 千卡，祝您饮食愉快");
        }
    };

    protected void logicActivity(Bundle mSavedInstanceState) {
        getData();
    }

    private void getData() {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.FOOD_TABLE_NAME);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException avException) {
                if (avException == null) {
                    if (avObjects != null && avObjects.size() != 0) {
                        alllist.addAll(avObjects);
                        Log.i(TAG, "done: " + alllist.size());
                        handler.sendEmptyMessage(0);
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_suggest_daily;
    }

    @OnClick(R.id.back_daily_img)
    public void onViewClicked() {
        mActivity.finish();
    }

}
