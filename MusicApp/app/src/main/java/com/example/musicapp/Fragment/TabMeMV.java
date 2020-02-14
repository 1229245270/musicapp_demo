package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.R;

public class TabMeMV extends MyFragment {
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me_mv,null);
        return view;
    }

    @Override
    public void loadData() {

    }

}
