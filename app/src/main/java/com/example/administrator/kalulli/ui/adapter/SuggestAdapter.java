package com.example.administrator.kalulli.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.kalulli.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/31.
 */

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder>{
    private List<String> list = new ArrayList<>();
    private Context context;

    public SuggestAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggestitem_view,parent,false);
        return new SuggestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
