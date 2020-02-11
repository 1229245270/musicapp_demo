package com.example.musicapp.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.TabListenTop;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {
    private Context context;
    private List<T> mData;
    private LayoutInflater inflater;
    private int itemLayoutId;
    private RecyclerView recyclerView;

    public BaseRecycleAdapter(Context context, List<T> mData,int itemLayoutId){
        this.context = context;
        this.mData = mData;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(itemLayoutId,parent,false);
        return BaseRecycleViewHolder.getRecycleViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecycleViewHolder holder, int position) {
        convert(holder,mData.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract void convert(BaseRecycleViewHolder holder,T item,int position);
}
