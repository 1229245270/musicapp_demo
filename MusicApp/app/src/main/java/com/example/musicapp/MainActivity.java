package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Adapter.DrawableAdapter;
import com.example.musicapp.DateBase.UsersTable;
import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.DrawableBottom;
import com.example.musicapp.Model.DrawableTop;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Module.BottomDialog;
import com.example.musicapp.Service.MusicService;
import com.example.musicapp.Service.MyServiceConn;
import com.example.musicapp.Utils.SongListUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.example.musicapp.Service.MusicService.isPlayComplete;
import static com.example.musicapp.Service.MyServiceConn.musicInterface;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private GridView drawable_GvTop;
    private ListView drawable_LvBottom;
    private TextView include_tvMusicName,include_tvMusicAuthor;
    private ImageButton include_ibMenu,include_ibShow,include_ibNext;
    private ImageView include_ivMusicHeader;
    private SeekBar include_seekBar;
    private ArrayList<Object> drawableTops,drawableBottoms;
    private View include_music_bar;
    private MyFragment homeFragment;
    private FragmentTransaction transaction;
    private int REQUEST_CODE = 1201;
    private static MainActivity instance;
    public static MainActivity getInstance(){
        return instance;
    }
    public static boolean isUserPressThumb = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(!(permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this,"读写权限授权失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawableTops = new ArrayList<>();
        drawableBottoms = new ArrayList<>();
        transaction = getSupportFragmentManager().beginTransaction();
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        final SharedPreferences preferences = getSharedPreferences("mSetting",MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("current",0);
        editor.commit();
        drawable_GvTop = findViewById(R.id.drawable_GvTop);
        drawable_LvBottom = findViewById(R.id.drawable_LvBottom);

        drawableTops.add(new DrawableTop(R.drawable.drawlayout_saoyisao,"扫一扫"));
        drawableTops.add(new DrawableTop(R.drawable.drawlayout_erweima,"我的二维码"));
        drawableTops.add(new DrawableTop(R.drawable.drawlayout_haoyou,"我的好友"));
        drawableTops.add(new DrawableTop(R.drawable.drawlayout_jiashi,"驾驶模式"));
        DrawableAdapter drawableAdapter = new DrawableAdapter(this,drawableTops);
        drawable_GvTop.setAdapter(drawableAdapter);

        drawableBottoms.add(new DrawableBottom(R.drawable.drawlayout_dmail,"消息中心",true,"",0));
        drawableBottoms.add(new DrawableBottom(R.drawable.drawlayout_skin,"皮肤中心",false,"这里有好看的皮肤哦",0));
        drawableBottoms.add(new DrawableBottom(R.drawable.drawlayout_sketch,"会员中心",false,"有钱才能变强",R.drawable.drawlayout_huiyuan));
        drawableBottoms.add(new DrawableBottom(R.drawable.drawlayout_cspace,"私人云盘",false,"",0));
        drawableBottoms.add(new DrawableBottom(R.drawable.drawlayout_region,"智能定位",true,"",0));
        drawableBottoms.add(new DrawableBottom(R.drawable.drawlayout_yanchu_line,"铃声彩铃",false,"",0));
        drawableBottoms.add(new DrawableBottom(R.drawable.drawlayout_cart,"购物商城",false,"这里有好东西",0));
        drawableAdapter = new DrawableAdapter(this,drawableBottoms);
        drawable_LvBottom.setAdapter(drawableAdapter);

        include_music_bar = findViewById(R.id.include_music_bar);
        include_music_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MusicActivity.class);
                startActivity(intent);
            }
        });
        include_ibMenu = include_music_bar.findViewById(R.id.include_ibMenu);
        include_seekBar = include_music_bar.findViewById(R.id.include_seekBar);
        include_ibShow = include_music_bar.findViewById(R.id.include_ibShow);
        include_ibNext = include_music_bar.findViewById(R.id.include_ibNext);
        include_ivMusicHeader = include_music_bar.findViewById(R.id.include_ivMusicHeader);
        include_tvMusicName = include_music_bar.findViewById(R.id.include_tvMusicName);
        include_tvMusicAuthor = include_music_bar.findViewById(R.id.include_tvMusicAuthor);
        include_seekBar.setMax(preferences.getInt("duration",0));
        include_seekBar.setProgress(preferences.getInt("current",0));
        Picasso.get().load(preferences.getString("header",null)).error(R.drawable.include_default).into(include_ivMusicHeader);
        include_tvMusicName.setText(preferences.getString("songName","songName"));
        include_tvMusicAuthor.setText(preferences.getString("singer","singer"));
        include_ibShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPlayComplete){    //没有播放完
                    if(musicInterface.isPlaying()){
                        musicInterface.pause();
                    }else{
                        musicInterface.continuePlay();
                    }
                }else{  //播放上次关闭歌曲
                    String songPath = preferences.getString("songPath","");
                    MyServiceConn conn = new MyServiceConn(songPath);
                    Intent intent = new Intent(MainActivity.this, MusicService.class);
                    intent.putExtra("songName", preferences.getString("songName","songName"));
                    intent.putExtra("singer", preferences.getString("singer","singer"));
                    intent.putExtra("fileName",preferences.getString("fileName","fileName"));
                    intent.putExtra("header", preferences.getString("header",""));
                    intent.putExtra("lyrics",preferences.getString("lyrics",""));
                    intent.putExtra("commentNum",preferences.getInt("commentNum",0));
                    intent.putExtra("mv",preferences.getString("mv",""));
                    intent.putExtra("createDate",preferences.getLong("createDate",0));
                    //设置Action的目的是为了让onBind()调用多次
                    intent.setAction(songPath);
                    bindService(intent,conn, Context.BIND_AUTO_CREATE);
                }
            }
        });
        include_ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicInterface != null) {
                    musicInterface.nextSong();
                }else{
                    SongListUtil.nextSong(MainActivity.this);
                }
            }
        });
        include_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //进度条变化时
                //设置时间
                if(b){

                }
                if(i == seekBar.getMax()){
                    include_ibShow.setImageResource(R.drawable.include_play);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始拖动时的状态
                //Log.v(TAG,"onStartTrackingTouch");
                isUserPressThumb = true;
                seekBar.setThumbOffset(15);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //拖动停止时
                //Log.v(TAG,"onStopTrackingTouch");
                if(!isPlayComplete){
                    musicInterface.seekTo(seekBar.getProgress());
                }
                isUserPressThumb = false;
            }
        });
        include_ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialog dialog = new BottomDialog(MainActivity.this);
                dialog.show();
            }
        });

        homeFragment = FragmentFactory.createFragment(10);
        if(!homeFragment.isAdded()){
            transaction.add(R.id.frameLayout_main,homeFragment,"homeFragment").show(homeFragment);
        }else{
            transaction.show(homeFragment);
        }
        transaction.commit();
        instance = this;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int i = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
            if(i != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
                },REQUEST_CODE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
