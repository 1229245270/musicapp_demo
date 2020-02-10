package com.example.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.TabListenTop;
import com.example.musicapp.R;

import java.util.List;

public class TabListenTopAdapter extends RecyclerView.Adapter<TabListenTopAdapter.MyViewHolder> {
    private Context context;
    private List<TabListenTop> listenTops;

    public TabListenTopAdapter(Context context, List<TabListenTop> listenTops){
        this.context = context;
        this.listenTops = listenTops;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tablisten_top,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(listenTops.get(position).getText());
        holder.imageView.setImageResource(listenTops.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return listenTops.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
