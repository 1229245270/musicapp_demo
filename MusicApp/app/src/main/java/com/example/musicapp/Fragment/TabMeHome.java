package com.example.musicapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp.Adapter.MyPagerAdapter;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.R;
import com.google.android.material.tabs.TabLayout;


public class TabMeHome extends MyFragment {
    private TabLayout fragMusic_tabLayout;
    private ViewPager fragMusic_viewPager;
    private Toolbar fragMusic_toolbar;
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me_home,null);
        fragMusic_tabLayout = view.findViewById(R.id.fragMusic_tabLayout);
        fragMusic_viewPager = view.findViewById(R.id.fragMusic_viewPager);
        fragMusic_toolbar = view.findViewById(R.id.fragMusic_toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(fragMusic_toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences preferences = getContext().getSharedPreferences("mSetting", Context.MODE_PRIVATE);
        int i = preferences.getInt("fragmentId",0);
        switch (i){
            case 0:
                fragMusic_toolbar.setTitle("本地音乐");
                break;
            case 1:
                fragMusic_toolbar.setTitle("我的收藏");
                break;
            case 2:
                fragMusic_toolbar.setTitle("下载管理");
                break;
            case 3:
                fragMusic_toolbar.setTitle("最近播放");
                break;
        }
        fragMusic_toolbar.setTitleTextColor(Color.WHITE);
        fragMusic_toolbar.setNavigationIcon(R.drawable.back);
        fragMusic_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        fragMusic_tabLayout.setupWithViewPager(fragMusic_viewPager);
        int flog = getContext().getResources().getInteger(R.integer.tab_me);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),getContext(),111,flog);  //这里使用getChildFragmentManager
        fragMusic_viewPager.setOffscreenPageLimit(4);//缓存4个页面
        fragMusic_viewPager.setAdapter(myPagerAdapter);
        for(int n = 0;n < fragMusic_tabLayout.getTabCount();n++){
            TabLayout.Tab tab = fragMusic_tabLayout.getTabAt(n);
            if(tab != null){
                tab.setCustomView(myPagerAdapter.getTabView(n));
            }
        }

        return view;
    }

    @Override
    public void loadData() {
    }

}
