package com.example.administrator.kalulli.ui.camera;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.data.FoodJson;
import com.example.administrator.kalulli.ui.adapter.CameraResultAdapter;
import com.example.administrator.kalulli.utils.ComputerTypeUtil;
import com.example.administrator.kalulli.utils.TableUtil;
import com.example.administrator.kalulli.utils.TimeUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraResultActivity extends BaseActivity {

    private static final String TAG = "CameraResultActivity";
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.result_recyclerView)
    RecyclerView resultRecyclerView;
    @BindView(R.id.loading_put)
    AVLoadingIndicatorView loadingPut;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private List<FoodJson> list = new ArrayList<>();
    private String description = "";

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        getData();

    }

    private void getData() {
        loadingPut.hide();
        Bundle bundle = getIntent().getExtras();
        Log.i(TAG, "logicActivity: " + bundle.getString("json"));
        try {
            JSONObject object = new JSONObject(bundle.getString("json"));
            Log.i(TAG, "logicActivity: " + object.toString());
            JSONArray jsonArray = object.getJSONArray("result");
            Log.i(TAG, "logicActivity: " + jsonArray.length());
            JSONObject object1 = (JSONObject) jsonArray.get(0);
            String object2 = object1.getString("baike_info");
            Log.i(TAG, "getData: "+object2);
            String[] strings = object2.split("\"");
            Log.i(TAG, "logicActivity: " + strings.length);
            if (strings.length > 5) {
                description = strings[11];
            }
            FoodJson foodJson = new FoodJson(object1.getString("name"),
                    object1.getString("calorie"),
                    bundle.getString("str"),
                    description);
            list.add(foodJson);
            //Log.i(TAG, "logicActivity: "+ strings[7]);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "logicActivity: " + list.size());
        initRecyclerView();
    }

    public void initRecyclerView() {
        resultRecyclerView.setLayoutManager(linearLayoutManager);
        CameraResultAdapter cameraResultAdapter = new CameraResultAdapter(this, list);
        resultRecyclerView.setAdapter(cameraResultAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera_result;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        loadingPut.show();
        if (mAVUserFinal != null) {
            Log.i(TAG, "onViewClicked: click");
            //AVObject avObject = new AVObject(TableUtil.DAILY_FOOD_TABLE_NAME);
            Log.i(TAG, "onViewClicked: " + list.get(0).getPicture_url());
            try {
                final AVFile file = AVFile.withAbsoluteLocalPath(list.get(0).getFoodname(), list.get(0).getPicture_url());
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            final AVObject avObject = new AVObject(TableUtil.DAILY_FOOD_TABLE_NAME);
                            FoodJson foodJson = list.get(0);
                            String foodName = foodJson.getFoodname();
                            String imgUrl = file.getUrl();
                            String type = ComputerTypeUtil.getType();
                            final String calorie = foodJson.getNumber();
                            String info = foodJson.getDescription();
                            avObject.put(TableUtil.DAILY_FOOD_USER, mAVUserFinal);
                            avObject.put(TableUtil.DAILY_FOOD_URL, imgUrl);
                            if (type.equals("早餐")){
                                avObject.put(TableUtil.DAILY_FOOD_CALORIE, Integer.parseInt(calorie) * 2+"");
                            }else if (type.equals("午餐")){
                                avObject.put(TableUtil.DAILY_FOOD_CALORIE, Integer.parseInt(calorie) * 3.5+"");
                            }else {
                                avObject.put(TableUtil.DAILY_FOOD_CALORIE, Integer.parseInt(calorie) * 3+"");
                            }
                            avObject.put(TableUtil.DAILY_FOOD_DESCRIPTION, info);
                            avObject.put(TableUtil.DAILY_FOOD_TIME, TimeUtil.getRealDate());
                            avObject.put(TableUtil.DAILY_FOOD_NAME, foodName);
                            avObject.put(TableUtil.DAILY_FOOD_TYPE, type);
                            avObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {

                                        AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_TABLE_NAME);
                                        query.whereEqualTo(TableUtil.DAILY_DATE, TimeUtil.getDate());
                                        query.whereEqualTo(TableUtil.DAILY_USER, mAVUserFinal);
                                        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
                                        query.findInBackground(new FindCallback<AVObject>() {
                                            @Override
                                            public void done(List<AVObject> list, AVException e) {
                                                if (e == null) {
                                                    if (list == null || list.size() == 0) {
                                                        AVObject avObject1 = new AVObject(TableUtil.DAILY_TABLE_NAME);
                                                        if (ComputerTypeUtil.getType().equals("早餐")) {
                                                            avObject1.put(TableUtil.DAILY_MORNING, Integer.parseInt(calorie)*2+"");

                                                        } else if (ComputerTypeUtil.getType().equals("午餐")) {
                                                            avObject1.put(TableUtil.DAILY_AFTERNOON, Integer.parseInt(calorie)*3.5+"");

                                                        } else {
                                                            avObject1.put(TableUtil.DAILY_EVENING, Integer.parseInt(calorie)*3+"");
                                                        }
                                                        avObject1.put(TableUtil.DAILY_USER, mAVUserFinal);
                                                        avObject1.put(TableUtil.DAILY_DATE, TimeUtil.getDate());
                                                        avObject1.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(AVException e) {
                                                             if (e == null){
                                                                 Log.i(TAG, "done: daily 提交成功");
                                                             }else{
                                                                 Log.e(TAG, "done: "+e.getMessage() );
                                                             }
                                                            }
                                                        });
                                                    } else {
                                                        AVObject avObject2 = list.get(0);
                                                        if (ComputerTypeUtil.getType().equals("早餐")) {
                                                            if (avObject2.get(TableUtil.DAILY_MORNING) == null
                                                                    || avObject2.get(TableUtil.DAILY_MORNING).equals("")) {
                                                                avObject2.put(TableUtil.DAILY_MORNING, Integer.parseInt(calorie)*2+"");
                                                            } else {
                                                                double calorie2 = Double.parseDouble(avObject2.getString(TableUtil.DAILY_MORNING));
                                                                avObject2.put(TableUtil.DAILY_MORNING, calorie2 + Integer.parseInt( calorie)*2  + "");
                                                            }

                                                        } else if (ComputerTypeUtil.getType().equals("午餐")) {
                                                            if (avObject2.get(TableUtil.DAILY_AFTERNOON) == null
                                                                    || avObject2.get(TableUtil.DAILY_AFTERNOON).equals("")) {
                                                                avObject2.put(TableUtil.DAILY_AFTERNOON, Integer.parseInt( calorie)*3.5  + "");
                                                            } else {
                                                                double calorie2 = Double.parseDouble(avObject2.getString(TableUtil.DAILY_AFTERNOON));
                                                                avObject2.put(TableUtil.DAILY_AFTERNOON, calorie2 + Integer.parseInt( calorie)*3.5  + "");
                                                            }

                                                        } else {
                                                            if (avObject2.get(TableUtil.DAILY_EVENING) == null
                                                                    || avObject2.get(TableUtil.DAILY_EVENING).equals("")) {
                                                                avObject2.put(TableUtil.DAILY_EVENING, Integer.parseInt( calorie)*3  + "");
                                                            } else {
                                                                double calorie2 = Double.parseDouble(avObject2.getString(TableUtil.DAILY_EVENING));
                                                                avObject2.put(TableUtil.DAILY_EVENING, calorie2 + Integer.parseInt( calorie)*3  + "");
                                                            }
                                                        }
                                                        avObject2.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(AVException e) {
                                                                if (e == null){
                                                                    Log.i(TAG, "done: daily 修改成功");
                                                                }else {
                                                                    Log.e(TAG, "done: "+e.getMessage() );
                                                                }
                                                            }
                                                        });

                                                    }
                                                    loadingPut.hide();
                                                    toast("提交成功", 0);
                                                    mActivity.finish();
                                                }else {
                                                    Log.e(TAG, "done: "+e.getMessage() );
                                                }
                                            }

                                        });
                                    }else {
                                        Log.e(TAG, "done: "+e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            toast("请先登录", 0);
        }


    }

}
