package com.example.musicapp.Fragment;

import android.net.Uri;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.ZhiBo;
import com.example.musicapp.Model.ZhiboList.ZhiboList;
import com.example.musicapp.Model.ZhiboList.data;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.example.musicapp.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.Utils.RetrofitUtil.GetZhiboForTime;

public class TabListenZhiBo extends MyFragment {
    private RecyclerView recyclerView;
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_zhibo,null);
        recyclerView = view.findViewById(R.id.recycleView);

        return view;
    }

    @Override
    public void loadData() {
        RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(),GetZhiboForTime,null,new int[]{0,10});
        retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
            @Override
            public void setObject(Object object) {
                ZhiboList zhiboList = (ZhiboList) object;
                if(zhiboList != null){
                    List<data> dataList = zhiboList.getData();
                    List<ZhiBo> zhiBoList = new ArrayList<>();
                    for(data data : dataList){
                        zhiBoList.add(new ZhiBo(Uri.encode(data.getImage(), "-![.:/,%?&=]"),data.getUserName(),data.getIntro()));
                    }
                    BaseRecycleAdapter<ZhiBo> baseRecycleAdapter = new BaseRecycleAdapter<ZhiBo>(getContext(),zhiBoList,R.layout.item_tablisten_zhibo) {
                        @Override
                        public void convert(BaseRecycleViewHolder holder, ZhiBo item, int position) {
                            holder.setImageResource(R.id.imageView,item.getImage());
                            holder.setText(R.id.title,item.getTitle());
                            holder.setText(R.id.intro,item.getIntro());
                            holder.setHeightAndWidth(getActivity(),R.id.imageView);
                            holder.setTextSize(R.id.title,18);
                            holder.setTextSize(R.id.intro,14);
                        }
                    };
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
                    recyclerView.setAdapter(baseRecycleAdapter);
                }
            }
        });
    }
}
