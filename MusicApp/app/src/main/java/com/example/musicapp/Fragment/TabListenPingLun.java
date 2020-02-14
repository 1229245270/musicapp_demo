package com.example.musicapp.Fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.PingLun;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabListenPingLun extends MyFragment {
    private RecyclerView recyclerView;
    private List<PingLun> list = new ArrayList<>();
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_pinglun,null);
        for(int i = 0;i < 5;i++){
            list.add(new PingLun("巴拉巴拉巴拉二次元巴拉巴拉巴拉二次元巴拉巴拉巴拉二次元","《负重一万斤长大》-太一",R.drawable.include_default,
                    R.drawable.include_default,"小说家",3661,138));
        }
        recyclerView = view.findViewById(R.id.recycleView);
        BaseRecycleAdapter<PingLun> baseRecycleAdapter = new BaseRecycleAdapter<PingLun>(getContext(),list,R.layout.item_tablisten_pinlun) {
            @Override
            public void convert(BaseRecycleViewHolder holder, PingLun item, int position) {
                holder.setText(R.id.content,((PingLun) item).getContent());
                holder.setText(R.id.songName,((PingLun) item).getSongName());
                holder.setImageResource(R.id.songHeader,((PingLun) item).getSongHeader());
                holder.setText(R.id.author,((PingLun) item).getCommentator());
                holder.setImageResource(R.id.authorHeader,((PingLun) item).getCommentatorHeader());
                holder.setText(R.id.getLike,((PingLun) item).getGetLike() + "");
                holder.setText(R.id.comment,((PingLun) item).getComment() + "");
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
