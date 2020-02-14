package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.GeDan;
import com.example.musicapp.Model.PingLun;
import com.example.musicapp.Model.RvZhiBo;
import com.example.musicapp.Model.ShiPin;
import com.example.musicapp.Model.XinGe;
import com.example.musicapp.Model.ZhiBo;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TabListenAll extends MyFragment {
    private RecyclerView tabListenAll_rvGeDan;
    private List<Object> objectList = new ArrayList<>();
    private int fragmentId = 0;
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_all,null);
        tabListenAll_rvGeDan = view.findViewById(R.id.tabListenAll_rvGeDan);

        for(int i = 0;i < 5;i++){
            objectList.add(new XinGe(R.drawable.include_default,"songName" + i,"singer" + i,11 + i));
        }
        for(int i = 0;i < 3;i++){
            objectList.add(new GeDan("二次元",R.drawable.include_default,111 + i,
                    "游戏必备 刺激神经","根据你的收藏的《Kaskade、Project 46 - LastChance》推荐","烈火小子",225 + i));
        }
        List<ZhiBo> zhiBoList= new ArrayList<>();
        for(int i = 0;i < 10;i++){
            zhiBoList.add(new ZhiBo(R.drawable.include_default,"你的小可爱上线啦","JZ点飘飘呀"));
        }
        RvZhiBo rvZhiBo = new RvZhiBo(zhiBoList);
        objectList.add(rvZhiBo);
        objectList.add(new ShiPin("二次元",R.drawable.include_default,111,
                11,"Visions(音频版)",R.drawable.include_default,"Acreix",1406,335));
        objectList.add(new PingLun("巴拉巴拉巴拉二次元巴拉巴拉巴拉二次元巴拉巴拉巴拉二次元","《负重一万斤长大》-太一",R.drawable.include_default,
                R.drawable.include_default,"小说家",3661,138));

        BaseRecycleAdapter<Object> baseRecycleAdapter = new BaseRecycleAdapter<Object>(getContext(),objectList) {
            @Override
            public void convert(BaseRecycleViewHolder holder, Object item, int position) {
                if(item instanceof XinGe){
                    holder.setImageResource(R.id.header,((XinGe) item).getImage());
                    holder.setText(R.id.songName,((XinGe) item).getSongName());
                    holder.setText(R.id.singer,((XinGe) item).getSinger());
                    holder.setImageNumber(R.id.comment,((XinGe) item).getComment());
                }else if(item instanceof GeDan){
                    holder.setImageResource(R.id.imageView,((GeDan) item).getImage());
                    holder.setText(R.id.tag,((GeDan) item).getTag());
                    holder.setText(R.id.listener,((GeDan) item).getListener() + "");
                    holder.setText(R.id.songListName,((GeDan) item).getSongListName());
                    holder.setText(R.id.songListIntro,((GeDan) item).getSongListIntro());
                    holder.setText(R.id.getLike,((GeDan) item).getAuthor());
                    holder.setText(R.id.comment,((GeDan) item).getComment() + "");
                }else if(item instanceof RvZhiBo){
                    BaseRecycleAdapter<ZhiBo> zhiBoAdapter = new BaseRecycleAdapter<ZhiBo>(getContext(),((RvZhiBo) item).getZhiBo(),R.layout.item_tablisten_zhibo) {
                        @Override
                        public void convert(BaseRecycleViewHolder holder, ZhiBo item, int position) {
                            holder.setImageResource(R.id.imageView,item.getImage());
                            holder.setText(R.id.title,item.getTitle());
                            holder.setText(R.id.intro,item.getIntro());
                        }
                    };
                    holder.getRecycleView(R.id.recycleView).setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    holder.getRecycleView(R.id.recycleView).setAdapter(zhiBoAdapter);
                }else if(item instanceof ShiPin){
                    holder.setText(R.id.tag,((ShiPin) item).getTag());
                    holder.setImageResource(R.id.shipin,((ShiPin) item).getMovie());
                    holder.setText(R.id.watcher,((ShiPin) item).getListener() + "");
                    holder.setText(R.id.time,((ShiPin) item).getTime()+ "");
                    holder.setImageResource(R.id.authorHeader,((ShiPin) item).getAuthorHeader());
                    holder.setText(R.id.author,((ShiPin) item).getAuthor());
                    holder.setText(R.id.getLike,((ShiPin) item).getGetLike() + "");
                    holder.setText(R.id.comment,((ShiPin) item).getComment() + "");
                }else if(item instanceof PingLun){
                    holder.setText(R.id.content,((PingLun) item).getContent());
                    holder.setText(R.id.songName,((PingLun) item).getSongName());
                    holder.setImageResource(R.id.songHeader,((PingLun) item).getSongHeader());
                    holder.setText(R.id.author,((PingLun) item).getCommentator());
                    holder.setImageResource(R.id.authorHeader,((PingLun) item).getCommentatorHeader());
                    holder.setText(R.id.getLike,((PingLun) item).getGetLike() + "");
                    holder.setText(R.id.comment,((PingLun) item).getComment() + "");
                }
            }
        };
        tabListenAll_rvGeDan.setLayoutManager(new AutoLayoutManage(getContext(),LinearLayoutManager.VERTICAL,false));
        tabListenAll_rvGeDan.setAdapter(baseRecycleAdapter);

        return view;
    }

    @Override
    public void loadData() {

    }
}
