package com.example.musicapp.Fragment;

import android.view.View;

import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.R;

public class TabLook extends MyFragment {

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_look,null);
        return view;
    }

    @Override
    public void loadData() {

    }
}
