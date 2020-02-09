package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.example.musicapp.Adapter.DrawableAdapter;
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
    private View include;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        include = findViewById(R.id.include);
        new IncludeMusicBar(this,include);
    }
}
