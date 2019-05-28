package com.example.administrator.kalulli.ui.adapter;

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

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder> {
    private List<AVObject> list = new ArrayList<>();
    private Context context;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SuggestAdapter(List<AVObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggest_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        AVObject avObject = list.get(position);
        String foodName = avObject.get(TableUtil.FOOD_NAME).toString();
        String imgUrl = avObject.get(TableUtil.FOOD_URL).toString();
        String foodType = avObject.get(TableUtil.FOOD_TYPE).toString();
        String foodInfo = avObject.get(TableUtil.FOOD_DESCRIPTION).toString();
        holder.foodInfoItem.setText(foodInfo);
        holder.foodNameItem.setText(foodName);
        holder.foodTypeItem.setText("#"+foodType);
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.foodImgItem);
        if (onClickListener!= null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.click(holder.getLayoutPosition(),holder.itemView);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.food_name_item)
        TextView foodNameItem;
        @BindView(R.id.food_img_item)
        ImageView foodImgItem;
        @BindView(R.id.food_type_item)
        TextView foodTypeItem;
        @BindView(R.id.food_info_item)
        TextView foodInfoItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
