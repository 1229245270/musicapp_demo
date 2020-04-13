package com.example.musicapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.LoginActivity;
import com.example.musicapp.Model.GetListList.GetListList;
import com.example.musicapp.Model.GetListList.data;
import com.example.musicapp.Model.TabMeMenuDown;
import com.example.musicapp.Module.TabMeMenu;
import com.example.musicapp.R;
import com.example.musicapp.Utils.RetrofitUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.Utils.RetrofitUtil.GetListListForUser;

public class TabMe extends MyFragment {
    private TextView tabMe_tvMyName,tabMe_tvTime,tabMe_tvLocality,tabMe_tvLove,tabMe_tvDownLoad,tabMe_tvNewSong;
    private static TextView tabMe_tvLocalityNum,tabMe_tvLoveNum,tabMe_tvDownLoadNum,tabMe_tvNewSongNum;
    private ImageView tabMe_ivMyHeader,tabMe_ivVIP,tabMe_ivLeave,tabMe_ivIdentity,tabMe_ivInformation;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Fragment TabMeHome,TabUserHeader,TabSongList,Home_Fragment;
    private TabMeMenu tabMe_menu;
    private String userName,img;
    private List<TabMeMenuDown> tabMeMenuDownList;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string){
        switch (string) {
            case "User":
                userName = preferences.getString("userName", "");
                img = preferences.getString("userHeader", "");
                if(!userName.equals("")){
                    tabMe_tvMyName.setText(userName);
                    tabMe_tvMyName.setOnClickListener(lookUser);
                    tabMe_ivMyHeader.setOnClickListener(lookUser);
                    if (!img.equals("")) {
                        Picasso.get().load(img).error(R.drawable.include_default).into(tabMe_ivMyHeader);
                    }
                    RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(),GetListListForUser,new String[]{userName},null);
                    retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
                        @Override
                        public void setObject(Object object) {
                            tabMeMenuDownList = new ArrayList<>();
                            GetListList getListList = (GetListList) object;
                            if(getListList != null){
                                List<data> dataList = getListList.getData();
                                for(data data : dataList){
                                    TabMeMenuDown tabMeMenuDown;
                                    if (data.getImg() != null && !data.getImg().equals("")) {
                                        tabMeMenuDown = new TabMeMenuDown(data.getImg(), data.getListName(), data.getTotal(), 0, "");
                                    } else {
                                        tabMeMenuDown = new TabMeMenuDown(R.drawable.include_default, data.getListName(), data.getTotal(), 0, "");
                                    }
                                    tabMeMenuDownList.add(tabMeMenuDown);
                                }
                                tabMe_menu.setAdapter(tabMeMenuDownList);
                                tabMe_menu.setOnClick(new TabMeMenu.OnItemClickListen() {
                                    @Override
                                    public void onItemClick(TabMeMenuDown tabMeMenuDown, int position) {
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                        transaction.hide(Home_Fragment);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("userName",preferences.getString("userName",""));
                                        bundle.putString("listName",tabMeMenuDown.getName());
                                        if(tabMeMenuDown.getIcon() instanceof String){
                                            bundle.putString("listImage",(String)tabMeMenuDown.getIcon());
                                        }
                                        TabSongList.setArguments(bundle);
                                        if (!TabSongList.isAdded())
                                            transaction.add(R.id.frameLayout_main, TabSongList).show(TabSongList);
                                        else transaction.show(TabSongList);
                                        transaction
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                });
                            }
                        }
                    });
                }else{
                    tabMe_ivMyHeader.setOnClickListener(loginUser);
                }
                break;
            case "localitySongNumber":
                tabMe_tvLocalityNum.setText(String.valueOf(preferences.getInt("localitySongNumber", 0)));
                break;
            case "loveNumber":
                tabMe_tvLoveNum.setText(String.valueOf(preferences.getInt("loveNumber", 0)));
                break;
            case "downLoadNumber":
                tabMe_tvDownLoadNum.setText(String.valueOf(preferences.getInt("downLoadNumber", 0)));
                break;
            case "newSongNumber":
                tabMe_tvNewSongNum.setText(String.valueOf(preferences.getInt("newSongNumber", 0)));
                break;
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me,null);
        TabMeHome = FragmentFactory.createFragment(110);
        TabUserHeader = FragmentFactory.createFragment(115);
        TabSongList = FragmentFactory.createFragment(116);
        Home_Fragment = FragmentFactory.createFragment(10);
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
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void loadData() {
        preferences = getContext().getSharedPreferences("mSetting",Context.MODE_PRIVATE);
        editor = preferences.edit();
        tabMe_tvLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("fragmentId",0);
                editor.commit();
                replaceFragment(1);
            }
        });
        EventBus.getDefault().post("User");
        EventBus.getDefault().post("localitySongNumber");
        EventBus.getDefault().post("loveNumber");
        EventBus.getDefault().post("downLoadNumber");
        EventBus.getDefault().post("newSongNumber");
    }

    private View.OnClickListener loginUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(userName.equals("")) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                //Fragment already added
            }
        }
    };

    private View.OnClickListener lookUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            replaceFragment(2);
        }
    };
    private View.OnClickListener updateUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            replaceFragment(2);
        }
    };
    private void replaceFragment(int i){
        if (getActivity().getSupportFragmentManager() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.hide(Home_Fragment);
            if(i == 1) {
                if (!TabMeHome.isAdded())
                    transaction.add(R.id.frameLayout_main, TabMeHome).show(TabMeHome);
                else transaction.show(TabMeHome);
                transaction
                        .addToBackStack(null)
                        .commit();
            }else if(i == 2){
                if (!TabUserHeader.isAdded())
                    transaction.add(R.id.frameLayout_main, TabUserHeader).show(TabUserHeader);
                else transaction.show(TabUserHeader);
                transaction
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
