package com.example.musicapp.Fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.XinGe;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabListenXinGe extends MyFragment {
    private RecyclerView recyclerView;
    private List<XinGe> list = new ArrayList<>();
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_xinge,null);
        for(int i = 0;i < 5;i++){
            list.add(new XinGe(R.drawable.include_default,"songName" + i,"singer" + i,11 + i));
        }
        recyclerView = view.findViewById(R.id.recycleView);
        BaseRecycleAdapter<XinGe> baseRecycleAdapter = new BaseRecycleAdapter<XinGe>(getContext(),list,R.layout.item_tablisten_xinge) {
            @Override
            public void convert(BaseRecycleViewHolder holder, XinGe item, int position) {
                holder.setImageResource(R.id.header,((XinGe) item).getImage());
                holder.setText(R.id.songName,((XinGe) item).getSongName());
                holder.setText(R.id.singer,((XinGe) item).getSinger());
                holder.setImageNumber(R.id.comment,((XinGe) item).getComment());
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
