package com.example.musicapp.Module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.DateBase.UsersTable;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service.MusicService;
import com.example.musicapp.Service.MyServiceConn;
import com.example.musicapp.Utils.SongListUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.example.musicapp.Adapter.BaseRecycleAdapter.FLOG_TABDIALOG;


public class BottomDialog extends Dialog {
    private Activity activity;
    private RecyclerView botDig_rv;
    private ImageView botDig_iv;
    private TextView botDig_tv;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private List<Song> songList;
    private BaseRecycleAdapter<Song> adapter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string){
        String songPath = preferences.getString("songPath","");
        if(!songPath.equals("")) {
            for (Song song : songList) {
                if (song.getSongPath().equals(songPath)) {
                    song.setSelect(true);
                } else {
                    song.setSelect(false);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public BottomDialog(Activity context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.activity = context;
    }

    @Override
    public void show() {
        super.show();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_dialog);
        setViewLocation();
        setCanceledOnTouchOutside(true);

        preferences = getContext().getSharedPreferences("mSetting",Context.MODE_PRIVATE);
        editor = preferences.edit();

        botDig_rv = findViewById(R.id.botDig_rv);
        botDig_iv = findViewById(R.id.botDig_iv);
        botDig_tv = findViewById(R.id.botDig_tv);
        Drawable drawable;
        switch (preferences.getInt("playOrder",0)){
            case 0:
                drawable = getContext().getResources().getDrawable(R.drawable.item_dialog_shunxu);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                botDig_tv.setText("顺序播放");
                botDig_tv.setCompoundDrawables(drawable, null, null, null);
                break;
            case 1:
                drawable = getContext().getResources().getDrawable(R.drawable.item_dialog_suiji);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                botDig_tv.setText("随机播放");
                botDig_tv.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                drawable = getContext().getResources().getDrawable(R.drawable.item_dialog_xunhuan);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                botDig_tv.setText("单曲循环");
                botDig_tv.setCompoundDrawables(drawable, null, null, null);
                break;
        }

        botDig_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable;
                switch (preferences.getInt("playOrder",0)){
                    case 0:
                        editor.putInt("playOrder",1);
                        drawable = getContext().getResources().getDrawable(R.drawable.item_dialog_suiji);
                        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        botDig_tv.setText("随机播放");
                        botDig_tv.setCompoundDrawables(drawable, null, null, null);
                        Toast.makeText(getContext(),"随机播放",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        editor.putInt("playOrder",2);
                        drawable = getContext().getResources().getDrawable(R.drawable.item_dialog_xunhuan);
                        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        botDig_tv.setText("单曲循环");
                        botDig_tv.setCompoundDrawables(drawable, null, null, null);
                        Toast.makeText(getContext(),"单曲循环",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        editor.putInt("playOrder",0);
                        drawable = getContext().getResources().getDrawable(R.drawable.item_dialog_shunxu);
                        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                        botDig_tv.setText("顺序播放");
                        botDig_tv.setCompoundDrawables(drawable, null, null, null);
                        Toast.makeText(getContext(),"顺序播放",Toast.LENGTH_SHORT).show();
                        break;
                }
                editor.commit();

            }
        });

        songList = new UsersTable(getContext()).getAllSongList();
        if(songList != null){
            adapter = new BaseRecycleAdapter<Song>(getContext(),songList,R.layout.item_dialog,FLOG_TABDIALOG) {
                @Override
                public void convert(BaseRecycleViewHolder holder, Song item, int position) {
                    position = position + 1;
                    String rankId = "01";
                    if(position < 10){
                        rankId = "0" + position;
                    }else{
                        rankId = String.valueOf(position);
                    }
                    holder.setSelect(R.id.rankId,R.id.header,item.isSelect());
                    holder.setSelectColor(R.id.songName,item.isSelect(),"#117cc7","#000000");
                    holder.setSelectColor(R.id.singer,item.isSelect(),"#117cc7","#666666");
                    holder.setText(R.id.rankId,rankId);
                    holder.setText(R.id.songName,item.getSongName());
                    holder.setText(R.id.singer,item.getSinger());
                    holder.setImageResource(R.id.header,item.getSongHeader());
                }
            };
            botDig_rv.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
            botDig_rv.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Object object, int position) {
                    SongListUtil.onClickPlay(getContext(),object,null);
                }

                @Override
                public void onItemLongClick(Object object, int position) {

                }
            });
        }
    }

    private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        onWindowAttributesChanged(lp);
    }
}
