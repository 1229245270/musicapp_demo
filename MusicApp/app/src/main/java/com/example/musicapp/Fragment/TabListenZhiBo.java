package com.example.musicapp.Fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.ZhiBo;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabListenZhiBo extends MyFragment {
    private RecyclerView recyclerView;
    private List<ZhiBo> list = new ArrayList<>();
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_zhibo,null);
        for(int i = 0;i < 5;i++){
            list.add(new ZhiBo(R.drawable.include_default,"你的小可爱上线啦","JZ点飘飘呀"));
        }
        recyclerView = view.findViewById(R.id.recycleView);
        BaseRecycleAdapter<ZhiBo> baseRecycleAdapter = new BaseRecycleAdapter<ZhiBo>(getContext(),list,R.layout.item_tablisten_zhibo) {
            @Override
            public void convert(BaseRecycleViewHolder holder, ZhiBo item, int position) {
                holder.setImageResource(R.id.imageView,item.getImage());
                holder.setText(R.id.title,item.getTitle());
                holder.setText(R.id.intro,item.getIntro());
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(baseRecycleAdapter);
        return view;
    }

    @Override
    public void loadData() {

    }
}
