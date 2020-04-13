package com.example.musicapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.musicapp.DateBase.UsersTable;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Module.NumberImageView;
import com.example.musicapp.Service.MusicService;
import com.example.musicapp.Service.MyServiceConn;
import com.example.musicapp.Utils.SongListUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.example.musicapp.Service.MusicService.isPlayComplete;
import static com.example.musicapp.Service.MusicService.mediaPlayer;
import static com.example.musicapp.Service.MyServiceConn.musicInterface;

public class MusicActivity extends AppCompatActivity {

    private static ImageView musicAty_ivBack,musicAty_ivDownLoad,musicAty_ivLove,musicAty_ivMore,musicAty_ivPalyShow,musicAty_ivPlayFacility,musicAty_ivPlayMenu,
                musicAty_ivPlayNext,musicAty_ivPlayOrder,musicAty_ivPlayUp,musicAty_ivReVideo,musicAty_ivRinging,musicAty_ivShare;
    private NumberImageView musicAty_ivComment;
    private static TextView musicAty_tvAttention,musicAty_tvBackground,musicAty_tvCurrentTime,musicAty_tvPlaySpeed,musicAty_tvSinger,musicAty_tvSongName,
                musicAty_tvSoundEffect,musicAty_tvTone,musicAty_tvTotalTime;
    private static SeekBar musicAty_seekBar;
    private Switch musicAty_switchVideo;
    public static Boolean isRunMusicActivity = false;
    private static MusicActivity instance;
    public static boolean isUserPressThumbMusic = false;
    private SharedPreferences preferences;
    public static MusicActivity getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        musicAty_ivPalyShow = findViewById(R.id.musicAty_ivPlayShow);
        musicAty_seekBar = findViewById(R.id.musicAty_seekBar);
        musicAty_tvCurrentTime = findViewById(R.id.musicAty_tvCurrentTime);
        musicAty_tvSinger = findViewById(R.id.musicAty_tvSinger);
        musicAty_tvSongName = findViewById(R.id.musicAty_tvSongName);
        musicAty_tvTotalTime = findViewById(R.id.musicAty_tvTotalTime);
        musicAty_ivPlayUp = findViewById(R.id.musicAty_ivPlayUp);
        musicAty_ivPlayNext = findViewById(R.id.musicAty_ivPlayNext);
        musicAty_ivComment = findViewById(R.id.musicAty_ivComment);

        //ro  List<LrcRow> lrcRows = new LrcDataBuilder().Build(file);
        //mLrcView.setTextSizeAutomaticMode(true);//是否自动适配文字大小

        //init the lrcView

        preferences = getSharedPreferences("mSetting",MODE_PRIVATE);
        String songName = preferences.getString("songName","songName");
        String singer = preferences.getString("singer","singer");
        String fileName = preferences.getString("fileName","fileName");
        String lrc = preferences.getString("lyrics","");
        int current = preferences.getInt("current",0);
        int duration = preferences.getInt("duration",0);
        int commentNum = preferences.getInt("commentNum",0);
        musicAty_tvSongName.setText(songName);
        musicAty_tvSinger.setText(singer);
        musicAty_seekBar.setMax(duration);
        musicAty_seekBar.setProgress(current);
        musicAty_tvCurrentTime.setText(toTime(current));
        musicAty_tvTotalTime.setText(toTime(duration));
        musicAty_ivComment.setNum(commentNum);
        musicAty_ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicActivity.this,CommentActivity.class);
                intent.putExtra("fileName",fileName);
                startActivity(intent);
            }
        });

        if(isPlayComplete){
            musicAty_ivPalyShow.setImageResource(R.drawable.music_play);
        }else{
            if(musicInterface.isPlaying()){
                musicAty_ivPalyShow.setImageResource(R.drawable.music_pause);
            }else{
                musicAty_ivPalyShow.setImageResource(R.drawable.music_play);
            }
        }
        musicAty_ivPlayUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicInterface != null) {
                    musicInterface.upSong();
                }else{
                    SongListUtil.upSong(MusicActivity.this);
                }
            }
        });
        musicAty_ivPlayNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicInterface != null) {
                    musicInterface.nextSong();
                }else{
                    SongListUtil.nextSong(MusicActivity.this);
                }
            }
        });
        musicAty_ivPalyShow.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent(MusicActivity.this, MusicService.class);
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
        musicAty_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){

                }
                if(i == seekBar.getMax()){
                    musicAty_ivPalyShow.setImageResource(R.drawable.music_play);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isUserPressThumbMusic = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(!isPlayComplete){
                    musicInterface.seekTo(seekBar.getProgress());
                }
                isUserPressThumbMusic = false;
            }
        });
        isRunMusicActivity = true;
        instance = this;
    }

    @Override
    protected void onDestroy() {
        isRunMusicActivity = false;
        super.onDestroy();
    }

    private String toTime(int time){
        time = time/1000;
        String m;
        String s;
        int intS = time % 60;
        int intM = time / 60;
        if(intM >= 10 && intM < 60)
            m = String.valueOf(intM);
        else m = "0" + intM;
        if(intS <10) s = "0" + intS;
        else s = String.valueOf(intS);
        return m + ":" + s;
    }
}
