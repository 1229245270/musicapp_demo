package com.example.musicapp.Adapter;

import android.content.Context;
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

    public MyPagerAdapter(FragmentManager fm, Context context,int add) {
        super(fm);
        strTitle = context.getResources().getStringArray(R.array.tabTitle);
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
        return view;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {    //重点,恢复界面布局
        //super.restoreState(state, loader);
    }
}

