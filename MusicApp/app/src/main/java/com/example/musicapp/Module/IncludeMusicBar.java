package com.example.musicapp.Module;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicapp.R;

public class IncludeMusicBar {
    private String TAG = "IncludeMusicBar";
    private static SeekBar include_seekBar;
    private static ImageView include_ivMusicHeader;
    private static TextView include_tvMusicName,include_tvMusicAuthor,include_tvEnd;
    private static ImageButton include_ibShow;
    private ImageButton include_ibNext,include_ibMenu;

    public IncludeMusicBar(final Context context, View view){

        include_ivMusicHeader = view.findViewById(R.id.include_ivMusicHeader);
        include_seekBar = view.findViewById(R.id.include_seekBar);
        include_tvMusicName = view.findViewById(R.id.include_tvMusicName);
        include_tvMusicAuthor = view.findViewById(R.id.include_tvMusicAuthor);
        include_ibShow = view.findViewById(R.id.include_ibShow);
        include_ibNext = view.findViewById(R.id.include_ibNext);
        include_ibMenu = view.findViewById(R.id.include_ibMenu);
        include_tvEnd = view.findViewById(R.id.include_tvEnd);

        include_ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialog dialog = new BottomDialog((Activity) context);
                dialog.show();
            }
        });
    }
}
