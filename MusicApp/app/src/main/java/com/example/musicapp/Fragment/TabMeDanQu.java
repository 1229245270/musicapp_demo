package com.example.musicapp.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.view.View;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.DateBase.UsersTable;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.Foot;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service.MusicService;
import com.example.musicapp.Service.MyServiceConn;
import com.example.musicapp.Utils.MusicUtil;
import com.example.musicapp.Utils.SongListUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


import static com.example.musicapp.Adapter.BaseRecycleAdapter.FLOG_TABMEDANQU;

public class TabMeDanQu extends MyFragment {
    //关于数据赋值一定要写在下面，不然重新加载页面会返回之前数据对象
    private String TAG = "Tab_Me_danQu";
    private List<Song> songList;
    private MyServiceConn conn;
    private String filePath;
    private RecyclerView fragDanQu_rvDanQu;
    private Spinner fragDanQu_spPlayOrder;
    private int fragmentId;
    private static List<Object> objectList;
    private Context context;
    private static SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static BaseRecycleAdapter<Object> adapter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string){
        String songPath = preferences.getString("songPath","");
        if(!songPath.equals("")){
            for(Object object : objectList){
                if(object instanceof Song){
                    if(((Song) object).getSongPath().equals(songPath)){
                        ((Song) object).setSelect(true);
                    }else{
                        ((Song) object).setSelect(false);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me_danqu, null);
        context = getContext();
        EventBus.getDefault().register(this);
        fragDanQu_rvDanQu = view.findViewById(R.id.fragDanQu_rvDanQu);
        fragDanQu_spPlayOrder = view.findViewById(R.id.fragDanQu_spPlayOrder);
        return view;
    }
    @Override
    public void loadData () {
        preferences = getContext().getSharedPreferences("mSetting", Context.MODE_PRIVATE);
        editor = preferences.edit();
        objectList = new ArrayList<>();
        songList = new ArrayList<>();
        if (SongListUtil.lacksPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            switch (fragmentId) {
                case 0:
                    songList = MusicUtil.getDanQuData(getContext());
                    break;
                case 1:
                    songList = MusicUtil.getDanQuData(getContext());
                    break;
                case 2:
                    songList = MusicUtil.getDanQuData(getContext());
                    break;
                case 3:
                    songList = MusicUtil.getDanQuData(getContext());
                    break;
            }
        }
        //songList = MusicUtil.getDanQuData(context);
        setAdapterAndListener();
        int localitySongNumber = songList.size();
        editor.putInt("localitySongNumber", localitySongNumber);
        editor.commit();
        EventBus.getDefault().post("localitySongNumber");
        EventBus.getDefault().post("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //设置adapter
    private void setAdapterAndListener(){
        objectList = new ArrayList<Object>();
        if(songList.size() > 0){
            objectList.addAll(songList);
        }
        objectList.add(new Foot());
        adapter = new BaseRecycleAdapter<Object>(getContext(), objectList, R.layout.item_tabme_danqu, FLOG_TABMEDANQU) {
            @Override
            public void convert(BaseRecycleViewHolder holder, Object item, int position) {
                if (item instanceof Foot) {
                    holder.setText(R.id.textView, "共" + (getItemCount() - 1) + "首歌曲");
                } else if (item instanceof Song) {
                    holder.setText(R.id.songName, ((Song) item).getSongName());
                    holder.setText(R.id.singer, ((Song) item).getSinger());
                    holder.setSelect(R.id.show, R.id.hide, ((Song) item).isSelect());
                    holder.setText(R.id.fileName, ((Song) item).getSinger() + " - " + ((Song) item).getSongName());
                    holder.setImageResource(R.id.header, ((Song) item).getSongHeader());
                }
            }
        };
        fragDanQu_rvDanQu.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        fragDanQu_rvDanQu.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                onClick(object,position);
            }

            @Override
            public void onItemLongClick(Object object, int position) {
            }
        });
    }

    private void onClick(Object object,int position){
        //如果与数据不符，修改
        List<Song> songListDataBase = new UsersTable(context).getAllSongList();
        //songList为空 !false 执行
        //songListDataBase为空 !false 执行
        //俩个list不相等时 ((有权限当没数据)listEquals = []) 执行
        if (!SongListUtil.listEquals(songList, songListDataBase)) {
            new UsersTable(context).deletePlayList();//必须用显示UserTable
            for (Song song : songList) {
                new UsersTable(context).insertPlayList(song);//必须用显示UserTable
            }
        }
        //这里有空页面，不用object
        Song song = songList.get(position);
        if (song != null) {
            String songName = song.getSongName();
            String singer = song.getSinger();
            String fileName = song.getFileName();
            String songPath = song.getSongPath();
            String header = song.getSongHeader();
            editor.putString("songPath",songPath);
            editor.commit();
            MyServiceConn conn = new MyServiceConn(songPath);
            Intent intent = new Intent(context, MusicService.class);
            intent.putExtra("songName", songName);
            intent.putExtra("singer", singer);
            intent.putExtra("fileName", fileName);
            intent.putExtra("header", header);
            //设置Action的目的是为了让onBind()调用多次
            intent.setAction(songPath);
            context.startService(intent);
            context.bindService(intent, conn, Context.BIND_AUTO_CREATE);//绑定服务
        }
    }


}