package com.example.musicapp.Fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.ShiPin;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabListenShiPin extends MyFragment {
    private RecyclerView recyclerView;
    private List<ShiPin> list = new ArrayList<>();
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_shipin,null);
        for(int i = 0;i < 5;i++){
            list.add(new ShiPin("二次元",R.drawable.include_default,111,
                    11,"Visions(音频版)",R.drawable.include_default,"Acreix",1406,335));
        }
        recyclerView = view.findViewById(R.id.recycleView);
        BaseRecycleAdapter<ShiPin> baseRecycleAdapter = new BaseRecycleAdapter<ShiPin>(getContext(),list,R.layout.item_tablisten_shipin) {
            @Override
            public void convert(BaseRecycleViewHolder holder, ShiPin item, int position) {
                holder.setText(R.id.tag,((ShiPin) item).getTag());
                holder.setImageResource(R.id.shipin,((ShiPin) item).getMovie());
                holder.setText(R.id.watcher,((ShiPin) item).getListener() + "");
                holder.setText(R.id.time,((ShiPin) item).getTime()+ "");
                holder.setImageResource(R.id.authorHeader,((ShiPin) item).getAuthorHeader());
                holder.setText(R.id.author,((ShiPin) item).getAuthor());
                holder.setText(R.id.getLike,((ShiPin) item).getGetLike() + "");
                holder.setText(R.id.comment,((ShiPin) item).getComment() + "");
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
