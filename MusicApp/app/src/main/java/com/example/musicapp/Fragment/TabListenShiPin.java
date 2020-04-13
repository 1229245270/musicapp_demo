package com.example.musicapp.Fragment;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.ShiPin;
import com.example.musicapp.Model.VideoList.VideoList;
import com.example.musicapp.Model.VideoList.data;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.example.musicapp.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.Utils.RetrofitUtil.GetVideoForTime;

public class TabListenShiPin extends MyFragment {
    private RecyclerView recyclerView;
    private List<ShiPin> list = new ArrayList<>();


    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_shipin,null);
        recyclerView = view.findViewById(R.id.recycleView);

        return view;
    }

    @Override
    public void loadData() {
        RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(),GetVideoForTime,null,new int[]{0,5});
        retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
            @Override
            public void setObject(Object object) {
                VideoList zhiboList = (VideoList) object;
                if(zhiboList != null){
                    List<data> dataList = zhiboList.getData();
                    List<ShiPin> shiPinList = new ArrayList<>();
                    for(data data : dataList){
                        shiPinList.add(new ShiPin(data.getTag(),data.getVideoPath(),0,0,data.getSongName(),data.getSingerHeader(),data.getSinger(),data.getGetLikeNum(),data.getCommentNum()));
                    }
                    BaseRecycleAdapter<ShiPin> baseRecycleAdapter = new BaseRecycleAdapter<ShiPin>(getContext(),shiPinList,R.layout.item_tablisten_shipin) {
                        @Override
                        public void convert(BaseRecycleViewHolder holder, ShiPin item, int position) {
                            //holder.setText(R.id.tag,((ShiPin) item).getTag());
                            holder.setVideoURL(getContext(),R.id.shipin,item.getMovie(),item.getTitle());
                            //holder.setText(R.id.watcher,((ShiPin) item).getListener() + "");
                            //holder.setText(R.id.time,((ShiPin) item).getTime()+ "");
                            holder.setImageResource(R.id.authorHeader,((ShiPin) item).getAuthorHeader());
                            holder.setText(R.id.author,((ShiPin) item).getAuthor());
                            holder.setText(R.id.getLike,((ShiPin) item).getGetLike() + "");
                            holder.setText(R.id.comment,((ShiPin) item).getComment() + "");
                        }
                    };
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                    recyclerView.setAdapter(baseRecycleAdapter);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
