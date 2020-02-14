package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.R;

public class TabMeGeShou extends MyFragment {
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me_geshou,null);
        return view;
    }

    @Override
    public void loadData() {

    }

}
