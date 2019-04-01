package com.example.administrator.kalulli.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.ui.adapter.SuggestAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestFragment extends Fragment {


    @BindView(R.id.suggest_recyclerView)
    RecyclerView suggestRecyclerView;
    Unbinder unbinder;
    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
    private List<String>list = new ArrayList<>();
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
        SuggestAdapter suggestAdapter = new SuggestAdapter(list,getContext());
        suggestRecyclerView.setAdapter(suggestAdapter);
        suggestRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
