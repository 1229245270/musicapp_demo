package com.example.musicapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongList.SongList;
import com.example.musicapp.Model.SongList.data;
import com.example.musicapp.Model.XinGe;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.example.musicapp.Utils.RetrofitUtil;
import com.example.musicapp.Utils.SongListUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.Adapter.BaseRecycleAdapter.FLOG_TABLISTENALL;
import static com.example.musicapp.Utils.RetrofitUtil.GetSongListForTime;

public class TabListenXinGe extends MyFragment {
    private RecyclerView recyclerView;
    private BaseRecycleAdapter<Song> adapter;
    private List<Song> baseSongList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string){
        String songPath = preferences.getString("songPath","");
        if(!songPath.equals("")){
            for(Song song : baseSongList){
                if(song.getSongPath().equals(songPath)){
                    song.setSelect(true);
                }else{
                    song.setSelect(false);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_xinge,null);
        EventBus.getDefault().register(this);
        recyclerView = view.findViewById(R.id.recycleView);
        preferences = getActivity().getSharedPreferences("mSetting", Context.MODE_PRIVATE);
        editor = preferences.edit();
        return view;
    }

    @Override
    public void loadData() {
        baseSongList = new ArrayList<>();
        RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(),GetSongListForTime,null,new int[]{0,10});
        retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
            @Override
            public void setObject(Object object) {
                SongList songList = (SongList) object;
                if(songList != null){
                    List<data> dataList = songList.getData();
                    int i = 0;
                    for(data data: dataList){
                        String songPath = Uri.encode(data.getPlayUrl(), "-![.:/,%?&=]");
                        String img = Uri.encode(data.getImg(), "-![.:/,%?&=]");
                        String mv = Uri.encode(data.getMv(), "-![.:/,%?&=]");
                        Song song = new Song();
                        song.setRowNum(i);
                        song.setSongPath(songPath);
                        song.setSongHeader(img);
                        song.setSongMv(mv);
                        song.setSongLyrics(data.getLyrics());
                        song.setFileName(data.getFileName());
                        song.setSongName(data.getSongName());
                        song.setSinger(data.getSinger());
                        song.setCommentNum(data.getCommentNum());
                        song.setCreateDate(data.getCreateDate());
                        song.setSelect(false);
                        baseSongList.add(song);
                        i++;
                    }
                    adapter = new BaseRecycleAdapter<Song>(getContext(),baseSongList,R.layout.item_tablisten_xinge,FLOG_TABLISTENALL) {
                        @Override
                        public void convert(BaseRecycleViewHolder holder, Song item, int position) {
                            holder.setImageResource(R.id.header,item.getSongHeader());
                            holder.setText(R.id.songName,item.getSongName());
                            holder.setText(R.id.singer,item.getSinger());
                            holder.setImageNumber(R.id.comment,item.getCommentNum());
                            holder.setSelectColor(R.id.songName,item.isSelect(),"#117cc7","#000000");
                            holder.setSelectColor(R.id.singer,item.isSelect(),"#117cc7","#666666");
                            holder.setSelect(0,R.id.love,item.isSelect());
                        }
                    };
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Object object, int position) {
                            SongListUtil.onClickPlay(getContext(),object,baseSongList);
                        }
                        @Override
                        public void onItemLongClick(Object object, int position) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
