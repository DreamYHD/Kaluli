package com.example.administrator.kalulli.ui.me;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.OnClickListener;
import com.example.administrator.kalulli.ui.adapter.DailyAdapter;
import com.example.administrator.kalulli.ui.suggest.ShowFoodActivity;
import com.example.administrator.kalulli.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {


    private static final String TAG = "MeFragment";
    @BindView(R.id.imageButton)
    ImageView imageButton;
    @BindView(R.id.imageButton2)
    ImageView imageButton2;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.daily_recyclerView)
    RecyclerView dailyRecyclerView;
    Unbinder unbinder;
    private List<AVObject>meList = new ArrayList<>();

    public MeFragment() {
        // Required empty public constructor
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initRecyclerView();
        }
    };
    public static MeFragment getInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ChangeInfoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getData() {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null){
            textView.setText(avUser.getUsername());
            AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_FOOD_TABLE_NAME);
            query.whereEqualTo(TableUtil.DAILY_FOOD_USER, avUser);
            // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null){
                        meList = list;
                        handler.sendEmptyMessage(0);
                        Log.i(TAG, "done: "+ list.size());
                    }else {
                        Log.e(TAG, "done: "+e.getMessage() );
                    }
                }
            });

        }else {
            textView.setText("请登录");
        }
    }


    public void initRecyclerView(){
        DailyAdapter dailyAdapter = new DailyAdapter(meList, getContext());
        dailyAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(int position, View view) {
                Intent intent = new Intent(getActivity(), ShowFoodActivity.class);
                intent.putExtra("objectId",meList.get(position).getObjectId());
                intent.putExtra("table",TableUtil.DAILY_FOOD_TABLE_NAME);
                startActivity(intent);
            }
        });
        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dailyRecyclerView.setAdapter(dailyAdapter);
        dailyAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imageButton)
    public void onImageButtonClicked() {
        Log.i(TAG, "onImageButtonClicked: "+"click login");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent,200);
    }

    @OnClick(R.id.imageButton2)
    public void onImageButton2Clicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示：");
        builder.setMessage("是否退出登录");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AVUser.logOut();
                getData();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }
}
