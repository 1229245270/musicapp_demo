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
    private static TextView tabMe_tvLocalityNum,tabMe_tvLoveNum,tabMe_tvDownLoadNum,tabMe_tvNewSongNum;
    private ImageView tabMe_ivMyHeader,tabMe_ivVIP,tabMe_ivLeave,tabMe_ivIdentity,tabMe_ivInformation;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Fragment TabMeHome;
    private TabMeMenu tabMe_menu;
    @SuppressLint("HandlerLeak")
    static Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    tabMe_tvLocalityNum.setText(String.valueOf(msg.arg1));
                    break;
                case 1:
                    tabMe_tvLoveNum.setText(String.valueOf(msg.arg1));
                    break;
                case 2:
                    tabMe_tvDownLoadNum.setText(String.valueOf(msg.arg1));
                    break;
                case 3:
                    tabMe_tvNewSongNum.setText(String.valueOf(msg.arg1));
                    break;
            }
        }
    };
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me,null);
        TabMeHome = FragmentFactory.createFragment(110);
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
        tabMe_tvLoveNum = view.findViewById(R.id.tabMe_tvLoveNum);
        tabMe_tvDownLoadNum = view.findViewById(R.id.tabMe_tvDownLoadNum);
        tabMe_tvNewSongNum= view.findViewById(R.id.tabMe_tvNewSongNum);

        tabMe_menu = view.findViewById(R.id.tabMe_menu);
        tabMe_menu.getItemTabMe_ivIncrease().setImageResource(R.drawable.tabme_increase);
        tabMe_menu.getItemTabMe_ivMore().setImageResource(R.drawable.tabme_more);
        tabMe_menu.getItemTabMe_ivShare().setImageResource(R.drawable.tabme_daoru);

        preferences = getContext().getSharedPreferences("mSetting",Context.MODE_PRIVATE);
        editor = preferences.edit();

        tabMe_tvLocalityNum.setText(String.valueOf(preferences.getInt("localitySongNumber",0)));
        tabMe_tvLoveNum.setText(String.valueOf(preferences.getInt("loveNumber",0)));
        tabMe_tvDownLoadNum.setText(String.valueOf(preferences.getInt("downLoadNumber",0)));
        tabMe_tvNewSongNum.setText(String.valueOf(preferences.getInt("newSongNumber",0)));
        tabMe_tvLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("fragmentId",0);
                editor.commit();
                replaceFragment();
            }
        });
        return view;
    }

    @Override
    public void loadData() {
        List<Object> objects = new ArrayList<>();
        TabMeMenuDown model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",2,2,"猜你喜欢");
        objects.add(model);
        model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",12,0,"");
        objects.add(model);
        model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",3,2,"");
        objects.add(model);
        model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",22,12,"");
        objects.add(model);
        model = new TabMeMenuDown(R.drawable.include_default,"我的歌单",2,0,"");
        objects.add(model);
        tabMe_menu.setAdapter(objects);

    }

    public void replaceFragment(){
        if (getActivity().getSupportFragmentManager() != null) {
            Fragment Home_Fragment = FragmentFactory.createFragment(10);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.hide(Home_Fragment);
            if(!TabMeHome.isAdded()) transaction.add(R.id.frameLayout_main,TabMeHome,"tagMusicList").show(TabMeHome);
            else transaction.show(TabMeHome);
            transaction
                    .addToBackStack(null)
                    .commit();
        }
    }

}
