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
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    public static int number = 0;
    public static Map<String,Integer>map = new HashMap();


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
    private int baseSize = 0;

    int selectPosition = 4;
    private RapidFloatingActionHelper rfabHelper;
    private List<AVObject> alllist = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loading.hide();
            initRecyclerView();
        }
    };

    private void initRecyclerView() {
        SuggestAdapter suggestAdapter = new SuggestAdapter(alllist, getContext());
        Log.i(TAG, "initRecyclerView: "+alllist.size());
        suggestAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(int position, View view) {
                Intent intent = new Intent(getActivity(), ShowFoodActivity.class);
                intent.putExtra("objectId", alllist.get(position).getObjectId());
                if (position + 1 <= baseSize){
                    intent.putExtra("table", TableUtil.FOOD_TABLE_NAME);
                }else {
                    intent.putExtra("table", TableUtil.DAILY_FOOD_TABLE_NAME);

                }
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
        if(AVUser.getCurrentUser() == null){
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        }else {
            getData();
        }
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
        items.add(new RFACLabelItem<Integer>()
                .setLabel("每日推荐")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(4)
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
        alllist.clear();
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.FOOD_TABLE_NAME);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException avException) {
                if (avException == null){
                    if (avObjects!= null&& avObjects.size()!=0){
                        baseSize = avObjects.size();
                        alllist.addAll(avObjects);
                        getUserList(selectPosition);
                    }
                }
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
        alllist.clear();
        selectPosition = position;
        getDataByPosition(position);
        doFbtnLayout.collapseContent();

    }

    private void getDataByPosition(int position) {
        if (position == 3){
            Intent intent = new Intent(getContext(),SuggestDailyActivity.class);
            startActivity(intent);
        }else {
            loading.show();
            getUserList(position);
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
                    if (avException == null){
                        if (alllist == null){
                            alllist = new ArrayList<>();
                        }else {

                            alllist.addAll(avObjects);
                        }
                        handler.sendEmptyMessage(0);
                    }
                }
            });
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        alllist.clear();
        selectPosition = position;
        getDataByPosition(position);
        doFbtnLayout.collapseContent();
    }

    public void getUserList(final int position2) {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.USER_TABLE_NAME);
        //1 获取用户列表
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> list, AVException e) {
                if (e == null){
                    //2 基于用户特征计算相似度
                    if (list != null && list.size() != 0) {

                        Log.i(TAG, "done: user size = "+list.size());
                        for (int i = 0; i < list.size(); i++) {
                            AVObject avObject = list.get(i);
                            AVUser avUser = AVUser.getCurrentUser();
                            if (avObject.getObjectId().equals(avUser.getObjectId())){
                                continue;
                            }
                            double finalHeight = Double.parseDouble(avUser.get(TableUtil.USER_HEIGHT).toString());
                            double finalWeight = Double.parseDouble(avUser.get(TableUtil.USER_WEIGHT).toString());
                            double iHeight = Double.parseDouble(avObject.get(TableUtil.USER_HEIGHT).toString());
                            double iWeight = Double.parseDouble(avObject.get(TableUtil.USER_WEIGHT).toString());
                            double finalIco = finalWeight / (finalHeight * finalHeight);
                            double iIco = iWeight / (iHeight * iHeight);
                            //1
                            if ((finalIco - iIco) <= 3 && (iIco - finalIco) <= 3) {
                                number += 4;
                            }
                            //2
                            if (avObject.get(TableUtil.USER_SEX).equals(avUser.get(TableUtil.USER_SEX))) {
                                number += 2;
                            } else {
                                number -= 1;
                            }
                            //3
                            Log.i(TAG, "done: "+avUser.get(TableUtil.USER_AGE).toString());
                            int finalAge = Integer.parseInt(avUser.get(TableUtil.USER_AGE).toString());
                            int iAge = Integer.parseInt(avObject.get(TableUtil.USER_AGE).toString());
                            if ((finalAge - iAge) <= 5 && (iAge - finalAge) <= 5) {
                                number += 1;
                            } else if ((finalAge - iAge) >= 15 || (iAge - finalAge) >= 15) {
                                number -= 1;
                            }
                            //4
                            String finalMajor = avUser.get(TableUtil.USER_MAJOR).toString();
                            String iMajor = avObject.get(TableUtil.USER_MAJOR).toString();
                            if (levenshtein(iMajor,finalMajor)) {
                                number += 1;
                            }
                            //5
                            String finalLove = avUser.get(TableUtil.USER_LOVE).toString();
                            String iLove = avObject.get(TableUtil.USER_LOVE).toString();
                            if (levenshtein(finalLove,iLove)){
                                number += 2;
                            }
                            String type = "";
                            if (number >= 5){
                                AVQuery<AVObject> query = new AVQuery<>(TableUtil.DAILY_FOOD_TABLE_NAME);
                                query.whereEqualTo(TableUtil.DAILY_FOOD_USER, avObject);
                                if (position2 == 0) {
                                    type = "早餐";
                                    query.whereEqualTo(TableUtil.DAILY_FOOD_TYPE,type);
                                } else if (position2 == 1 ){
                                    type = "午餐";
                                    query.whereEqualTo(TableUtil.DAILY_FOOD_TYPE,type);
                                }else if (position2 == 2){
                                    type = "晚餐";
                                    query.whereEqualTo(TableUtil.DAILY_FOOD_TYPE,type);
                                }
                                // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
                                query.findInBackground(new FindCallback<AVObject>() {
                                    @Override
                                    public void done(List<AVObject> listL, AVException e) {
                                        if (e == null){
                                            alllist.addAll(listL);
                                            handler.sendEmptyMessage(0);
                                            Log.i(TAG, "done:listL "+ listL.size());
                                        }else {
                                            Log.e(TAG, "done: "+e.getMessage() );
                                        }
                                    }
                                });
                            }
                            number = 0;
                        }
                    } else {
                        Log.i(TAG, "done: list == null");
                    }
                }else {
                    Log.e(TAG, "done: "+e.getMessage());
                }

            }
        });

    }
    //2 基于用户特征计算相似度
    /**
     *
     * @param str1
     * @param str2
     */
    public static boolean levenshtein(String str1,String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        System.out.println("字符串\""+str1+"\"与\""+str2+"\"的比较");
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        System.out.println("差异步骤："+dif[len1][len2]);
        //计算相似度
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        Log.i(TAG, "levenshtein: "+similarity);
        if (similarity >= 0.5){
            return true;
        }else {
            return false;
        }
    }
    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }
}
