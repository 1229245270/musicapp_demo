package com.example.musicapp.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicapp.MainActivity;
import com.example.musicapp.MusicActivity;
import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static com.example.musicapp.MainActivity.isUserPressThumb;
import static com.example.musicapp.MusicActivity.isUserPressThumbMusic;
import static com.example.musicapp.Service.MusicService.isPlayComplete;
import static com.example.musicapp.Service.MyServiceConn.musicInterface;

public class MusicBroadcast extends BroadcastReceiver {
    public static final int MAINACTIVITY_MSG = 0;
    public static final int MAINACTIVITY_SEEKBAR = 1;
    public static final int MUSICACTIVITY_MSG = 2;
    public static final int MUSICACTIVITY_SEEKBAR = 3;
    private SeekBar include_seekBar,musicAty_seekBar;
    private ImageView include_ivMusicHeader,include_ibShow,musicAty_ivPlayShow;
    private TextView include_tvMusicName,include_tvMusicAuthor,musicAty_tvSongName,musicAty_tvSinger,musicAty_tvCurrentTime,musicAty_tvTotalTime;
    @Override
    public void onReceive(Context context, final Intent intent) {
        switch (intent.getIntExtra("type",0)){
            case MAINACTIVITY_MSG:
                include_seekBar = MainActivity.getInstance().findViewById(R.id.include_seekBar);
                include_ivMusicHeader = MainActivity.getInstance().findViewById(R.id.include_ivMusicHeader);
                include_tvMusicName = MainActivity.getInstance().findViewById(R.id.include_tvMusicName);
                include_tvMusicAuthor = MainActivity.getInstance().findViewById(R.id.include_tvMusicAuthor);
                include_ibShow = MainActivity.getInstance().findViewById(R.id.include_ibShow);
                MainActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int duration = intent.getIntExtra("duration",0);
                        int current = intent.getIntExtra("current",0);
                        String songName = intent.getStringExtra("songName");
                        String singer = intent.getStringExtra("singer");
                        String header = intent.getStringExtra("header");
                        if(header != null && !header.equals("")){
                            Picasso.get().load(header).error(R.drawable.include_default).into(include_ivMusicHeader);
                        }else{
                            Picasso.get().load(R.drawable.include_default).into(include_ivMusicHeader);
                        }
                        include_seekBar.setMax(duration);
                        include_seekBar.setProgress(current);
                        include_tvMusicName.setText(songName);
                        include_tvMusicAuthor.setText(singer);
                        if(isPlayComplete){
                            include_ibShow.setImageResource(R.drawable.include_play);
                        }else{
                            include_ibShow.setImageResource(R.drawable.include_pause);
                        }
                    }
                });
                break;
            case MAINACTIVITY_SEEKBAR:
                include_seekBar = MainActivity.getInstance().findViewById(R.id.include_seekBar);
                include_ibShow = MainActivity.getInstance().findViewById(R.id.include_ibShow);
                MainActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int current = intent.getIntExtra("current",0);
                        if(!isUserPressThumb) include_seekBar.setProgress(current); //没有拖动音乐条，设置播放时长
                        if(isPlayComplete){
                            include_ibShow.setImageResource(R.drawable.include_play);
                        }else{
                            if(intent.getBooleanExtra("isPlay",false)){
                                include_ibShow.setImageResource(R.drawable.include_pause);
                            }else{
                                include_ibShow.setImageResource(R.drawable.include_play);
                            }
                        }
                    }
                });
                break;
            case MUSICACTIVITY_MSG:
                musicAty_tvSongName = MusicActivity.getInstance().findViewById(R.id.musicAty_tvSongName);
                musicAty_tvSinger = MusicActivity.getInstance().findViewById(R.id.musicAty_tvSinger);
                musicAty_seekBar = MusicActivity.getInstance().findViewById(R.id.musicAty_seekBar);
                musicAty_tvCurrentTime = MusicActivity.getInstance().findViewById(R.id.musicAty_tvCurrentTime);
                musicAty_tvTotalTime = MusicActivity.getInstance().findViewById(R.id.musicAty_tvTotalTime);
                musicAty_ivPlayShow = MusicActivity.getInstance().findViewById(R.id.musicAty_ivPlayShow);
                MusicActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int duration = intent.getIntExtra("duration",0);
                        int current = intent.getIntExtra("current",0);
                        String songName = intent.getStringExtra("songName");
                        String singer = intent.getStringExtra("singer");
                        String lrc = intent.getStringExtra("lyrics");
                        musicAty_tvSongName.setText(songName);
                        musicAty_tvSinger.setText(singer);
                        musicAty_seekBar.setMax(duration);
                        musicAty_seekBar.setProgress(current);
                        musicAty_tvCurrentTime.setText(toTime(current));
                        musicAty_tvTotalTime.setText(toTime(duration));
                        if(isPlayComplete){
                            musicAty_ivPlayShow.setImageResource(R.drawable.music_play);
                        }else{
                            musicAty_ivPlayShow.setImageResource(R.drawable.music_pause);
                        }
/*
                        MusicService musicService = new MusicService();
                        lrcView.setLrc(lrc).setPlayer(musicService.getMediaPlayer()).draw();*/
                    }
                });
                break;
            case MUSICACTIVITY_SEEKBAR:
                musicAty_seekBar = MusicActivity.getInstance().findViewById(R.id.musicAty_seekBar);
                musicAty_tvCurrentTime = MusicActivity.getInstance().findViewById(R.id.musicAty_tvCurrentTime);
                musicAty_ivPlayShow = MusicActivity.getInstance().findViewById(R.id.musicAty_ivPlayShow);
                MainActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int current = intent.getIntExtra("current",0);
                        if(!isUserPressThumbMusic) musicAty_seekBar.setProgress(current);
                        musicAty_tvCurrentTime.setText(toTime(current));
                        if(isPlayComplete){
                            musicAty_ivPlayShow.setImageResource(R.drawable.music_play);
                        }else{
                            if(intent.getBooleanExtra("isPlay",false)){
                                musicAty_ivPlayShow.setImageResource(R.drawable.music_pause);
                            }else{
                                musicAty_ivPlayShow.setImageResource(R.drawable.music_play);
                            }
                        }
                    }
                });
                break;
        }

    }
    private Bitmap getBitmapFromByte(byte[] temp){
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
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
