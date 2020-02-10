package com.example.musicapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.TabMeMenuDown;
import com.example.musicapp.Module.TabMeMenu;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;


public class TabMe extends MyFragment {
    private TextView tabMe_tvMyName,tabMe_tvTime,tabMe_tvLocality,tabMe_tvLove,tabMe_tvDownLoad,tabMe_tvNewSong;
    private static TextView tabMe_tvLocalityNum;
    private ImageView tabMe_ivMyHeader,tabMe_ivVIP,tabMe_ivLeave,tabMe_ivIdentity,tabMe_ivInformation;
    private TabMeMenu tabMe_menu;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Fragment musicList_fragment;
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me,null);
        musicList_fragment = FragmentFactory.createFragment(2000);
        tabMe_tvMyName = view.findViewById(R.id.tabMe_tvMyName);
        tabMe_tvTime = view.findViewById(R.id.tabMe_tvTime);
        tabMe_tvLocality = view.findViewById(R.id.tabMe_tvLocality);
        tabMe_tvLove = view.findViewById(R.id.tabMe_tvLove);
        tabMe_tvDownLoad = view.findViewById(R.id.tabMe_tvDownLoad);
        tabMe_tvNewSong = view.findViewById(R.id.tabMe_tvNewSong);
        tabMe_ivMyHeader = view.findViewById(R.id.tabMe_ivMyHeader);
        tabMe_ivVIP = view.findViewById(R.id.tabMe_ivVIP);
        tabMe_ivLeave = view.findViewById(R.id.tabMe_ivLeave);
        tabMe_ivIdentity = view.findViewById(R.id.tabMe_ivIdentity);
        tabMe_ivInformation = view.findViewById(R.id.tabMe_ivInformation);
        tabMe_tvLocalityNum = view.findViewById(R.id.tabMe_tvLocalityNum);

        tabMe_menu = view.findViewById(R.id.tabMe_menu);

        return view;
    }

    @Override
    public void loadData() {
        List<Object> objects = new ArrayList<>();
        TabMeMenuDown model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",2,2,R.mipmap.ic_launcher);
        objects.add(model);
        model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",12,0,0);
        objects.add(model);
        model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",3,2,0);
        objects.add(model);
        model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",22,12,R.mipmap.ic_launcher);
        objects.add(model);
        model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",2,0,R.drawable.include_default);
        objects.add(model);
        tabMe_menu.setAdapter(objects);

    }

}
