package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;

import com.example.musicapp.Adapter.DrawableAdapter;
import com.example.musicapp.Factory.FragmentFactory;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.DrawableBottom;
import com.example.musicapp.Model.DrawableTop;
import com.example.musicapp.Module.IncludeMusicBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private GridView drawable_GvTop;
    private ListView drawable_LvBottom;
    private ArrayList<Object> drawableTops = new ArrayList<>();
    private ArrayList<Object> drawableBottoms = new ArrayList<>();
    private View include_music_bar;
    private MyFragment homeFragment;
    private FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        drawable_GvTop = findViewById(R.id.drawable_GvTop);
        drawable_LvBottom = findViewById(R.id.drawable_LvBottom);

        drawableTops.add(new DrawableTop(R.drawable.include_default,"扫一扫"));
        drawableTops.add(new DrawableTop(R.drawable.include_default,"我的二维码"));
        drawableTops.add(new DrawableTop(R.drawable.include_default,"我的好友"));
        drawableTops.add(new DrawableTop(R.drawable.include_default,"驾驶模式"));
        DrawableAdapter drawableAdapter = new DrawableAdapter(this,drawableTops);
        drawable_GvTop.setAdapter(drawableAdapter);

        drawableBottoms.add(new DrawableBottom(R.drawable.include_default,"消息中心",true,"",0));
        drawableBottoms.add(new DrawableBottom(R.drawable.include_default,"皮肤中心",false,"",0));
        drawableBottoms.add(new DrawableBottom(R.drawable.include_default,"皮肤中心",false,"点击添加好友",R.drawable.include_default));
        drawableBottoms.add(new DrawableBottom(R.drawable.include_default,"皮肤中心",true,"哈哈哈",0));
        drawableAdapter = new DrawableAdapter(this,drawableBottoms);
        drawable_LvBottom.setAdapter(drawableAdapter);

        include_music_bar = findViewById(R.id.include_music_bar);
        new IncludeMusicBar(this,include_music_bar);

        homeFragment = FragmentFactory.createFragment(10);
        if(!homeFragment.isAdded()){
            transaction.add(R.id.frameLayout_main,homeFragment,"homeFragment");
        }
        transaction.show(homeFragment).commit();


    }
}
