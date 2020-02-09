package com.example.musicapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.R;

import java.util.ArrayList;
import java.util.List;


public class TabMe extends MyFragment {
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me,null);
        return view;
    }

    @Override
    public void loadData() {

    }

}
