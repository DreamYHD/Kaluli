package com.example.administrator.kalulli.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.data.FoodJson;
import com.example.administrator.kalulli.ui.adapter.CameraResultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private List<FoodJson> list = new ArrayList<>();
    private String description = "";

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        Log.i(TAG, "logicActivity: " + bundle.getString("json"));
        try {
            JSONObject object = new JSONObject(bundle.getString("json"));
            Log.i(TAG, "logicActivity: " + object.toString());
            JSONArray jsonArray = object.getJSONArray("result");
            Log.i(TAG, "logicActivity: "+jsonArray.length());
            JSONObject object1 = (JSONObject) jsonArray.get(0);
            String object2 =  object1.getString("baike_info");
            String []strings = object2.split("\"");
            Log.i(TAG, "logicActivity: "+strings.length);
            if (strings.length>5){
                description = strings[12];
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
    }
}
