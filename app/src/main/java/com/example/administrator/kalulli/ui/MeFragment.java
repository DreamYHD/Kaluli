package com.example.administrator.kalulli.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.ui.adapter.DailyAdapter;
import com.example.administrator.kalulli.ui.regist.LoginActivity;

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
    private List<String>list = new ArrayList<>();

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment getInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        DailyAdapter dailyAdapter = new DailyAdapter(list, getContext());
        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dailyRecyclerView.setAdapter(dailyAdapter);
        dailyAdapter.notifyDataSetChanged();
        return view;
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
        startActivity(intent);
    }

    @OnClick(R.id.imageButton2)
    public void onImageButton2Clicked() {
    }
}
