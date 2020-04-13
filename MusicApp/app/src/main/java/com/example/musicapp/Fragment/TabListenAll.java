package com.example.musicapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.CommentList.CommentList;
import com.example.musicapp.Model.GeDan;
import com.example.musicapp.Model.GetListList.GetListList;
import com.example.musicapp.Model.PingLun;
import com.example.musicapp.Model.RvZhiBo;
import com.example.musicapp.Model.ShiPin;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongList.SongList;
import com.example.musicapp.Model.VideoList.VideoList;
import com.example.musicapp.Model.XinGe;
import com.example.musicapp.Model.ZhiBo;
import com.example.musicapp.Model.ZhiboList.ZhiboList;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.example.musicapp.Utils.RetrofitUtil;
import com.example.musicapp.Utils.SongListUtil;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.Adapter.BaseRecycleAdapter.FLOG_TABLISTENALL;
import static com.example.musicapp.Utils.RetrofitUtil.GetCommentListForGetLike;
import static com.example.musicapp.Utils.RetrofitUtil.GetListListForTime;
import static com.example.musicapp.Utils.RetrofitUtil.GetSongListForTime;
import static com.example.musicapp.Utils.RetrofitUtil.GetVideoForTime;
import static com.example.musicapp.Utils.RetrofitUtil.GetZhiboForTime;

public class TabListenAll extends MyFragment {
    private RecyclerView tabListenAll_rvGeDan;
    private List<Object> objectList;
    private int fragmentId = 0;
    private BaseRecycleAdapter<Object> adapter;
    private int total = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Fragment Home_Fragment,TabSongList;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string){
        if(string.equals("TabListenAll")){
            RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(),GetSongListForTime,null,new int[]{0,5});
            retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
                @Override
                public void setObject(Object object) {
                    SongList songList = (SongList) object;
                    if(songList != null){
                        List<com.example.musicapp.Model.SongList.data> dataList = songList.getData();
                        int i = 0;
                        for(com.example.musicapp.Model.SongList.data data: dataList){
                            String songPath = Uri.encode(data.getPlayUrl(), "-![.:/,%?&=]");
                            String img = Uri.encode(data.getImg(), "-![.:/,%?&=]");
                            String mv = Uri.encode(data.getMv(), "-![.:/,%?&=]");
                            XinGe xinGe = new XinGe(data.getFileName(),data.getSongName(),data.getSinger(),img,data.getLyrics(),false,songPath,mv,data.getCommentNum());
                            objectList.set(total + i,xinGe);
                            i++;
                        }
                        adapter.notifyDataSetChanged();
                        EventBus.getDefault().post("");
                    }
                }
            });
            RetrofitUtil retrofitUtil2 = new RetrofitUtil(getContext(),GetListListForTime,null,new int[]{0,3});
            retrofitUtil2.setAnInterface(new RetrofitUtil.AnInterface() {
                @Override
                public void setObject(Object object) {
                    GetListList getListList = (GetListList) object;
                    if(getListList != null){
                        List<com.example.musicapp.Model.GetListList.data> dataList = getListList.getData();
                        int i = 5;
                        for(com.example.musicapp.Model.GetListList.data data: dataList){
                            GeDan geDan = new GeDan(data.getTag(),data.getImg(),data.getTotal(), data.getListName(),
                                    data.getIntro(),data.getUserName(),data.getTotal());
                            objectList.set(total + i,geDan);
                            i++;
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            RetrofitUtil retrofitUtil3 = new RetrofitUtil(getContext(),GetZhiboForTime,null,new int[]{0,10});
            retrofitUtil3.setAnInterface(new RetrofitUtil.AnInterface() {
                @Override
                public void setObject(Object object) {
                    ZhiboList zhiboList = (ZhiboList) object;
                    if(zhiboList != null){
                        List<com.example.musicapp.Model.ZhiboList.data> dataList = zhiboList.getData();
                        List<ZhiBo> zhiBoList = new ArrayList<>();
                        for(com.example.musicapp.Model.ZhiboList.data data : dataList){
                            zhiBoList.add(new ZhiBo(Uri.encode(data.getImage(), "-![.:/,%?&=]"),data.getUserName(),data.getIntro()));
                        }
                        RvZhiBo rvZhiBo = new RvZhiBo(zhiBoList);
                        objectList.set(total + 8,rvZhiBo);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

            RetrofitUtil retrofitUtil4 = new RetrofitUtil(getContext(),GetVideoForTime,null,new int[]{0,1});
            retrofitUtil4.setAnInterface(new RetrofitUtil.AnInterface() {
                @Override
                public void setObject(Object object) {
                    VideoList videoList = (VideoList) object;
                    if(videoList != null){
                        List<com.example.musicapp.Model.VideoList.data> dataList = videoList.getData();
                        com.example.musicapp.Model.VideoList.data data = dataList.get(0);
                        ShiPin shiPin =new ShiPin(data.getTag(),data.getVideoPath(),0,0,data.getSongName(),data.getSingerHeader(),data.getSinger(),data.getGetLikeNum(),data.getCommentNum());
                        objectList.set(total + 9,shiPin);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            RetrofitUtil retrofitUtil5 = new RetrofitUtil(getContext(),GetCommentListForGetLike,null,new int[]{0,1});
            retrofitUtil5.setAnInterface(new RetrofitUtil.AnInterface() {
                @Override
                public void setObject(Object object) {
                    CommentList commentList = (CommentList) object;
                    if(commentList != null){
                        List<com.example.musicapp.Model.CommentList.data> dataList = commentList.getData();
                        for(com.example.musicapp.Model.CommentList.data data: dataList){
                            PingLun pingLun = new PingLun(data.getCommentText(),data.getFileName(),data.getUserHeader(),
                                    data.getSongImage(),data.getUserName(),data.getGetLike(),data.getGetComment());
                            objectList.set(total + 10,pingLun);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }else{
            String songPath = preferences.getString("songPath","");
            if(!songPath.equals("")){
                for(Object object : objectList){
                    if(object instanceof XinGe){
                        if(((XinGe) object).getSongPath().equals(songPath)){
                            ((XinGe) object).setSelect(true);
                        }else{
                            ((XinGe) object).setSelect(false);
                        }
                    }

                }
            }
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public View initView() {
        EventBus.getDefault().register(this);
        objectList = new ArrayList<>();
        View view = View.inflate(getContext(), R.layout.fragment_listen_all,null);
        tabListenAll_rvGeDan = view.findViewById(R.id.tabListenAll_rvGeDan);
        preferences = getActivity().getSharedPreferences("mSetting", Context.MODE_PRIVATE);
        editor = preferences.edit();
        TabSongList = FragmentFactory.createFragment(116);
        Home_Fragment = FragmentFactory.createFragment(10);
        return view;
    }

    @Override
    public void loadData() {
        for(int i = 0;i < 11;i++){
            objectList.add(new Object());
        }
        adapter = new BaseRecycleAdapter<Object>(getContext(),objectList,0,FLOG_TABLISTENALL) {
            @Override
            public void convert(BaseRecycleViewHolder holder, Object item, int position) {
                if(item instanceof XinGe){
                    holder.setImageResource(R.id.header,((XinGe) item).getSongHeader());
                    holder.setText(R.id.songName,((XinGe) item).getSongName());
                    holder.setText(R.id.singer,((XinGe) item).getSinger());
                    holder.setImageNumber(R.id.comment,((XinGe) item).getCommentNum());
                    holder.setSelectColor(R.id.songName,((XinGe) item).isSelect(),"#117cc7","#000000");
                    holder.setSelectColor(R.id.singer,((XinGe) item).isSelect(),"#117cc7","#666666");
                    holder.setSelect(0,R.id.love,((XinGe) item).isSelect());
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
                    //holder.setText(R.id.tag,((ShiPin) item).getTag());
                    holder.setVideoURL(getContext(),R.id.shipin,((ShiPin) item).getMovie(),((ShiPin) item).getTitle());
                    //holder.setText(R.id.watcher,((ShiPin) item).getListener() + "");
                    //holder.setText(R.id.time,((ShiPin) item).getTime()+ "");
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
        tabListenAll_rvGeDan.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        tabListenAll_rvGeDan.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if(object instanceof XinGe){
                    Song song = new Song();
                    song.setSongHeader(((XinGe) object).getSongHeader());
                    song.setFileName(((XinGe) object).getFileName());
                    song.setSinger(((XinGe) object).getSinger());
                    song.setSongMv(((XinGe) object).getSongMv());
                    song.setSongLyrics(((XinGe) object).getSongLyrics());
                    song.setCommentNum(((XinGe) object).getCommentNum());
                    song.setCreateDate(((XinGe) object).getCreateDate());
                    song.setSongName(((XinGe) object).getSongName());
                    song.setSongPath(((XinGe) object).getSongPath());
                    SongListUtil.onClickPlay(getContext(),song,null);
                }else if(object instanceof GeDan){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.hide(Home_Fragment);
                    Bundle bundle = new Bundle();
                    bundle.putString("listName",((GeDan) object).getSongListName());
                    bundle.putString("userName",((GeDan) object).getAuthor());
                    bundle.putString("listImage",((GeDan) object).getImage());
                    TabSongList.setArguments(bundle);
                    if (!TabSongList.isAdded())
                        transaction.add(R.id.frameLayout_main, TabSongList).show(TabSongList);
                    else transaction.show(TabSongList);
                    transaction
                            .addToBackStack(null)
                            .commit();
                }else if(object instanceof ShiPin){

                }else if(object instanceof RvZhiBo){

                }else if(object instanceof PingLun){

                }
            }
            @Override
            public void onItemLongClick(Object object, int position) {

            }
        });
        tabListenAll_rvGeDan.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.v("1111","newState" + newState +"  ");
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.v("1111","dx:" + dx +"  dy:" + dy);
            }
        });
        EventBus.getDefault().post("TabListenAll");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
