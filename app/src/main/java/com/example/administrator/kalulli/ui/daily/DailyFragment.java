package com.example.administrator.kalulli.ui.daily;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.kalulli.MainActivity;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.utils.TableUtil;
import com.example.administrator.kalulli.utils.TimeUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.sql.Time;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {

    private static final String TAG = "DailyFragment";

    @BindView(R.id.daily_more_tv)
    TextView dailyMoreTv;
    @BindView(R.id.daily_date_tv)
    TextView dailyDateTv;
    @BindView(R.id.morning_tv)
    TextView morningTv;
    @BindView(R.id.afternoon_tv)
    TextView afternoonTv;
    @BindView(R.id.evening_tv)
    TextView eveningTv;
    @BindView(R.id.daily_btn)
    Button dailyBtn;
    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;
    Unbinder unbinder;

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment getInstance() {
        return new DailyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        unbinder = ButterKnife.bind(this, view);
        loading.hide();
        dailyDateTv.setText(TimeUtil.getDate());
        getData();
        return view;
    }

    private void getData() {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_TABLE_NAME);
        query.whereEqualTo(TableUtil.DAILY_USER, AVUser.getCurrentUser());
        query.whereEqualTo(TableUtil.DAILY_DATE, TimeUtil.getDate());
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                   AVObject avObject = list.get(0);
                   double sum = 0.0;
                   if (avObject.get(TableUtil.DAILY_MORNING) != null && !avObject.get(TableUtil.DAILY_MORNING).equals("")) {
                        morningTv.setText(avObject.get(TableUtil.DAILY_MORNING).toString());
                        sum +=  Double.parseDouble(avObject.get(TableUtil.DAILY_MORNING).toString());
                    }else{
                       sum += 0;
                    }
                    if (avObject.get(TableUtil.DAILY_AFTERNOON) != null && !avObject.get(TableUtil.DAILY_AFTERNOON).equals("")) {
                        morningTv.setText(avObject.get(TableUtil.DAILY_AFTERNOON).toString());
                        sum +=  Double.parseDouble(avObject.get(TableUtil.DAILY_AFTERNOON).toString());
                    }else{
                        sum += 0;
                    }
                    if (avObject.get(TableUtil.DAILY_EVENING) != null && !avObject.get(TableUtil.DAILY_EVENING).equals("")) {
                        morningTv.setText(avObject.get(TableUtil.DAILY_EVENING).toString());
                        sum +=  Double.parseDouble(avObject.get(TableUtil.DAILY_EVENING).toString());
                    }else{
                        sum += 0;
                    }
                    dailyMoreTv.setText(1000-sum+"");
                }else {
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.daily_btn)
    public void onViewClicked() {
        loading.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(getActivity(),DailyActivity.class);
                    startActivity(intent);
                    loading.hide();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
