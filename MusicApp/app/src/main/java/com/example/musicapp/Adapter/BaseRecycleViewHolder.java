package com.example.musicapp.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecycleViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context context;
    public BaseRecycleViewHolder(Context context,@NonNull View itemView) {
        super(itemView);
        this.context = context;
        views = new SparseArray<>();
    }
    public static BaseRecycleViewHolder getRecycleViewHolder(Context context,View itemView){
        return new BaseRecycleViewHolder(context,itemView);
    }
    public SparseArray<View> getViews(){
        return views;
    }
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }
    public BaseRecycleViewHolder setText(int viewId,String text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }
    public BaseRecycleViewHolder setImageResource(int viewId,int drawableId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(drawableId);
        return this;
    }
}
