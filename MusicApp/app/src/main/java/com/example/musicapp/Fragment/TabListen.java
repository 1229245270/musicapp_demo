package com.example.musicapp.Fragment;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabListen extends MyFragment {
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen,null);

        return view;
    }

    @Override
    public void loadData() {

    }
}
