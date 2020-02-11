package com.example.musicapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.R;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private String[] strTitle;
    private Context context;
    private int add;
    private int flog;

    public MyPagerAdapter(FragmentManager fm, Context context,int add,int flog) {
        super(fm);
        if(context.getResources().getInteger(R.integer.tab_home) == flog){
            strTitle = context.getResources().getStringArray(R.array.tabHome);
        }else if(context.getResources().getInteger(R.integer.tab_listen) == flog){
            strTitle = context.getResources().getStringArray(R.array.tabListen);
        }
        this.flog = flog;
        this.context = context;
        this.add = add;
    }
    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position + add);
    }

    @Override
    public int getCount() {
        return strTitle.length;
    }

    public View getTabView(int position){
        View view = View.inflate(context,R.layout.item_toptitle,null);
        TextView fragHome_tvTitle = view.findViewById(R.id.fragHome_tvTitle);
        fragHome_tvTitle.setText(strTitle[position]);
        if(flog == context.getResources().getInteger(R.integer.tab_home)){
            fragHome_tvTitle.setTextColor(context.getResources().getColor(R.color.colorTabHomeUnSelect));
        }else if(flog == context.getResources().getInteger(R.integer.tab_listen)){
            fragHome_tvTitle.setTextColor(context.getResources().getColor(R.color.colorTabListenUnSelect));
        }
        return view;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {    //重点,恢复界面布局
        //super.restoreState(state, loader);
    }
}

