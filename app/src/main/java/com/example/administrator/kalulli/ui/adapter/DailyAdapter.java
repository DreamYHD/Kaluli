package com.example.administrator.kalulli.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.bumptech.glide.Glide;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.OnClickListener;
import com.example.administrator.kalulli.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/3/31.
 */

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private List<AVObject> list = new ArrayList<>();
    private Context context;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public DailyAdapter(List<AVObject> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.timeitem_view, parent, false);
        return new ViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (onClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.click(position,v);
                }
            });
        }
        AVObject avObject = list.get(position);
        String foodName = avObject.get(TableUtil.DAILY_FOOD_NAME).toString();
        String imgUrl = avObject.get(TableUtil.DAILY_FOOD_URL).toString();
        String calories = avObject.get(TableUtil.DAILY_FOOD_CALORIE).toString();
        String time = avObject.get(TableUtil.DAILY_FOOD_TIME).toString();
        holder.foodCalorie.setText("卡路里含量: "+calories+"千卡");
        holder.foodName.setText("食物名称: "+foodName);
        holder.showTime.setText(time);
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.showImage);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.show_time)
        TextView showTime;
        @BindView(R.id.show_image)
        ImageView showImage;
        @BindView(R.id.food_name)
        TextView foodName;
        @BindView(R.id.food_calorie)
        TextView foodCalorie;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
