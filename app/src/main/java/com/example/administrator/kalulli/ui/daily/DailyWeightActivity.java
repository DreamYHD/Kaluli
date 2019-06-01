package com.example.administrator.kalulli.ui.daily;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.utils.TableUtil;
import com.example.administrator.kalulli.utils.TimeUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyWeightActivity extends BaseActivity {

    private static final String TAG = "DailyWeightActivity";
    @BindView(R.id.weight_et)
    EditText weightEt;
    @BindView(R.id.send_btn)
    Button dataSet;
    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.back_daily_img)
    ImageView backDailyImg;
    private List<Entry> entries = new ArrayList<Entry>();
    private List<String> meList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initChart();
        }
    };

    private void initChart() {
        //1
        for (String s : meList) {
            Log.i(TAG, "initChart: " + s);
            String s1 = s.split("\\|")[0];
            float x;
            if (s1.startsWith("0")) {
                x = Integer.parseInt(new String(String.valueOf(s1.toCharArray()[1])));
                Log.i(TAG, "initChart: " + x);
            } else {
                x = Float.parseFloat(s.split("\\|")[0]);
                Log.i(TAG, "initChart: " + x);


            }
            float y = Float.parseFloat(s.split("\\|")[1]);

            entries.add(new Entry(x, y));
        }
        Description description = new Description();
        description.setText("时间");
        description.setTextSize(15);
        chart.setNoDataText("当前还没有数据");
        chart.setDescription(description);
        chart.setBorderColor(Color.CYAN);
        chart.setDrawGridBackground(false);


        //2
        LineDataSet dataSet = new LineDataSet(entries, "体重曲线图");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(false);

        dataSet.setLineWidth(1.8f);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        dataSet.setColor(Color.CYAN);
        dataSet.setFillColor(Color.CYAN);
        dataSet.setFillAlpha(100);
        dataSet.setDrawHorizontalHighlightIndicator(false);

        //3
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daily_weight;
    }

    @OnClick(R.id.send_btn)
    public void onViewClicked() {
        final String weight = weightEt.getText().toString().trim();
        final String date = TimeUtil.getDate().split("-")[2];
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_WEIGHT_TABLE_NAME);
        query.whereEqualTo(TableUtil.DAILY_WEIGHT_USER, mAVUserFinal);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException avException) {
                if (avException == null) {
                    if (avObjects == null || avObjects.size() == 0) {
                        AVObject avObject = new AVObject(TableUtil.DAILY_WEIGHT_TABLE_NAME);
                        List<String> list = new ArrayList<>();
                        list.add("00|"+mAVUserFinal.get(TableUtil.USER_WEIGHT));
                        list.add(date + "|" + weight);
                        avObject.put(TableUtil.DAILY_WEIGHT_ARRAY, list);
                        avObject.put(TableUtil.DAILY_WEIGHT_USER, mAVUserFinal);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    toast("上传成功", 0);
                                    getData();
                                } else {
                                    Log.e(TAG, "done: " + e.getMessage());
                                }
                            }
                        });
                    } else {
                        AVObject avObject = avObjects.get(0);
                        Log.i(TAG, "done: " + avObject.getObjectId());
                        List<String> list = avObject.getList(TableUtil.DAILY_WEIGHT_ARRAY);
                        list.add(date + "|" + weight);
                        avObject.put(TableUtil.DAILY_WEIGHT_ARRAY, list);
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    toast("上传成功", 0);
                                    getData();
                                } else {
                                    Log.e(TAG, "done: " + e.getMessage());
                                }
                            }
                        });

                    }
                } else {
                    Log.e(TAG, "done: " + avException.getMessage());
                }
            }
        });
    }

    public void getData() {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_WEIGHT_TABLE_NAME);
        query.whereEqualTo(TableUtil.DAILY_WEIGHT_USER, mAVUserFinal);
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list != null && list.size() != 0) {
                        meList = list.get(0).getList(TableUtil.DAILY_WEIGHT_ARRAY);
                    }
                    handler.sendEmptyMessage(0);
                    Log.i(TAG, "done: " + list.size());
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.back_daily_img)
    public void onViewClicked2() {
        mActivity.finish();
    }
}
