package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

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
    public static boolean isPerMissRead = true;
    private int REQUEST_CODE = 1201;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                isPerMissRead = true;
            }else{
                isPerMissRead = false;
                Toast.makeText(this,"读写权限授权失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int i = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
            if(i != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
                },REQUEST_CODE);
            }
        }
    }
}
