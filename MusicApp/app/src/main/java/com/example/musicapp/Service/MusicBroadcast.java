package com.example.musicapp.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicBroadcast extends BroadcastReceiver {
    public static final int MAINACTIVITY = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        View view;
        switch (intent.getIntExtra("type",0)){
            case MAINACTIVITY:
                view = View.inflate(context,R.layout.activity_main,null);
                SeekBar include_seekBar = view.findViewById(R.id.include_seekBar);
                ImageView include_ivMusicHeader = view.findViewById(R.id.include_ivMusicHeader);
                TextView include_tvMusicName = view.findViewById(R.id.include_tvMusicName);
                TextView include_tvMusicAuthor = view.findViewById(R.id.include_tvMusicAuthor);
                ImageView include_ibShow = view.findViewById(R.id.include_ibShow);

                int duration = intent.getIntExtra("duration",0);
                int current = intent.getIntExtra("current",0);
                String songName = intent.getStringExtra("songName");
                String singer = intent.getStringExtra("singer");
                int header = intent.getIntExtra("header", R.drawable.include_default);
                include_seekBar.setMax(duration);
                include_seekBar.setProgress(current);
                include_ivMusicHeader.setImageResource(header);
                include_tvMusicName.setText(songName);
                include_tvMusicAuthor.setText(singer);
                if(intent.getBooleanExtra("isPlayComplete",false)){
                    include_ibShow.setImageResource(R.drawable.include_default);
                }else{
                    include_ibShow.setImageResource(R.drawable.include_default);
                }
                break;
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
