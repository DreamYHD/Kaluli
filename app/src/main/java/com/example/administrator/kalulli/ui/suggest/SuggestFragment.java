package com.example.administrator.kalulli.ui.suggest;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.OnClickListener;
import com.example.administrator.kalulli.ui.adapter.SuggestAdapter;
import com.example.administrator.kalulli.utils.TableUtil;
import com.wang.avi.AVLoadingIndicatorView;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {


    private static final String TAG = "SuggestFragment";
    @BindView(R.id.suggest_recyclerView)
    RecyclerView suggestRecyclerView;
    Unbinder unbinder;
    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
    @BindView(R.id.do_fbtn)
    RapidFloatingActionButton doFbtn;
    @BindView(R.id.do_fbtn_layout)
    RapidFloatingActionLayout doFbtnLayout;
    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;

    private RapidFloatingActionHelper rfabHelper;
    private List<AVObject> list = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loading.hide();
            initRecyclerView();
        }
    };

    private void initRecyclerView() {
        SuggestAdapter suggestAdapter = new SuggestAdapter(list, getContext());
        suggestAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(int position, View view) {
                Intent intent = new Intent(getActivity(), ShowFoodActivity.class);
                intent.putExtra("objectId", list.get(position).getObjectId());
                intent.putExtra("table", TableUtil.FOOD_TABLE_NAME);
                startActivity(intent);
            }
        });
        suggestRecyclerView.setAdapter(suggestAdapter);
        suggestRecyclerView.setLayoutManager(layoutManager);
    }

    public SuggestFragment() {
        // Required empty public constructor
    }

    public static SuggestFragment getInstance() {
        return new SuggestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest, container, false);
        unbinder = ButterKnife.bind(this, view);
        initFloating();
        getData();
        return view;
    }

    private void initFloating() {
        loading.show();
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getContext());
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("早餐推荐")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("午餐推荐")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("晚餐推荐")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(3)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(5)
        ;
        rfabHelper = new RapidFloatingActionHelper(
                getContext(),
                doFbtnLayout,
                doFbtn,
                rfaContent
        ).build();
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
    }
    private void getData() {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.FOOD_TABLE_NAME);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException avException) {
                list = avObjects;
                handler.sendEmptyMessage(0);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {

        getDataByPosition(position);
        doFbtnLayout.collapseContent();

    }

    private void getDataByPosition(int position) {
        loading.show();
        Log.i(TAG, "getDataByPosition: " + position);
        String type = "";
        if (position == 0) {
            type = "早餐";
        } else {
            type = "通用";
        }
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.FOOD_TABLE_NAME);
        query.whereEqualTo(TableUtil.DAILY_FOOD_TYPE, type);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException avException) {
                list = avObjects;
                handler.sendEmptyMessage(0);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        getDataByPosition(position);
        doFbtnLayout.collapseContent();
    }
}
