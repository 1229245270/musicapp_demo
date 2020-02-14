package com.example.musicapp.Fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.GeDan;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabListenGeDan extends MyFragment {
    private RecyclerView recyclerView;
    private List<GeDan> list = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_gedan,null);
        for(int i = 0;i < 3;i++){
            list.add(new GeDan("二次元",R.drawable.include_default,111 + i,
                    "游戏必备 刺激神经","根据你的收藏的《Kaskade、Project 46 - LastChance》推荐","烈火小子",225 + i));
        }
        recyclerView = view.findViewById(R.id.recycleView);
        BaseRecycleAdapter<GeDan> baseRecycleAdapter = new BaseRecycleAdapter<GeDan>(getContext(),list,R.layout.item_tablisten_gedan) {
            @Override
            public void convert(BaseRecycleViewHolder holder, GeDan item, int position) {
                holder.setImageResource(R.id.imageView,((GeDan) item).getImage());
                holder.setText(R.id.tag,((GeDan) item).getTag());
                holder.setText(R.id.listener,((GeDan) item).getListener() + "");
                holder.setText(R.id.songListName,((GeDan) item).getSongListName());
                holder.setText(R.id.songListIntro,((GeDan) item).getSongListIntro());
                holder.setText(R.id.getLike,((GeDan) item).getAuthor());
                holder.setText(R.id.comment,((GeDan) item).getComment() + "");
            }
        };
        recyclerView.setLayoutManager(new AutoLayoutManage(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(baseRecycleAdapter);
        return view;
    }

    @Override
    public void loadData() {

    }
}
