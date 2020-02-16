package com.example.musicapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.MainActivity.isPerMissRead;

public class TabMeDanQu extends MyFragment {
    private String TAG = "Tab_Me_danQu";
    private static List<Song> songList = new ArrayList<>();
    private MyServiceConn conn;
    private String filePath;
    private static RecyclerView fragDanQu_rvDanQu;
    private Spinner fragDanQu_spPlayOrder;
    private static int hover;
    private int fragmentId;
    private static boolean isFirstSetSong = true;
    private List<Object> objectList = new ArrayList<>();
    private static Context context;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    @SuppressLint("HandlerLeak")
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //数据库操作，删除和插入
            if (isFirstSetSong) {
                new UsersTable(context).deletePlayList();//必须用显示UserTable
                for (Song song : songList) {
                    new UsersTable(context).insertPlayList(song);//必须用显示UserTable
                }
                isFirstSetSong = false;
            }
            /*for (int n = 0; n < fragDanQu_rvDanQu.getAdapter().getItemCount(); n++) {
                LinearLayout show = fragDanQu_rvDanQu.getChildAt(n).findViewById(R.id.show);
                ConstraintLayout hide = fragDanQu_rvDanQu.getChildAt(n).findViewById(R.id.hide);
                if (n == hover) {
                    show.setVisibility(View.GONE);
                    hide.setVisibility(View.VISIBLE);
                } else {
                    show.setVisibility(View.VISIBLE);
                    hide.setVisibility(View.GONE);
                }
            }*/
            Song song = songList.get(hover);
            if (song != null) {
                int songId = song.getSongId();
                String songName = song.getSongName();
                String singer = song.getSinger();
                String songPath = song.getSongPath();
                int header = song.getSongHeader();

                editor.putString("songPath",songPath);
                editor.commit();
                MyServiceConn conn = new MyServiceConn(songPath);
                Intent intent = new Intent(context, MusicService.class);
                intent.putExtra("songName", songName);
                intent.putExtra("singer", singer);
                intent.putExtra("header", header);
                intent.putExtra("songId", songId);
                //设置Action的目的是为了让onBind()调用多次
                intent.setAction(String.valueOf(songId));
                context.startService(intent);
                context.bindService(intent, conn, Context.BIND_AUTO_CREATE);//绑定服务
            }
        }
    };

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me_danqu, null);
        context = getContext();
        fragDanQu_rvDanQu = view.findViewById(R.id.fragDanQu_rvDanQu);
        fragDanQu_spPlayOrder = view.findViewById(R.id.fragDanQu_spPlayOrder);
        if (isPerMissRead) {
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
        if(songList.size() > 0){
            objectList.addAll(songList);
        }
        objectList.add(new Foot());
        //objectList.add(new Foot());
        BaseRecycleAdapter<Object> adapter = new BaseRecycleAdapter<Object>(getContext(), objectList, R.layout.item_tabme_danqu,true) {
            @Override
            public void convert(BaseRecycleViewHolder holder, Object item, int position) {
                if (item instanceof Foot) {
                    holder.setText(R.id.textView, "共" + (getItemCount()-1) + "首歌曲");
                } else if (item instanceof Song) {
                    holder.setText(R.id.songName, ((Song) item).getSongName());
                    holder.setText(R.id.singer, ((Song) item).getSinger());
                    holder.setImageResource(R.id.header, ((Song) item).getSongHeader());
                }
            }
        };
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "单击了", Toast.LENGTH_SHORT).show();
                hover = position;
                handler.sendMessage(new Message());
            }
            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(getContext(), "长长单击了", Toast.LENGTH_SHORT).show();
                hover = position;
                handler.sendMessage(new Message());
            }
        });

        fragDanQu_rvDanQu.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        fragDanQu_rvDanQu.setAdapter(adapter);

        preferences = getContext().getSharedPreferences("mSetting", Context.MODE_PRIVATE);
        editor = preferences.edit();
        int localitySongNumber = songList.size();
        editor.putInt("localitySongNumber", localitySongNumber);
        editor.commit();
        Message message = new Message();
        message.what = 0;
        message.arg1 = localitySongNumber;
        TabMe.handler.sendMessage(message); //发送返回TabMe页面，歌曲数量
        return view;
    }
    @Override
    public void loadData () {

    }
}