package com.example.musicapp.Service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class MyServiceConn implements ServiceConnection {
    private String TAG = "MyServiceConn";
    public static MusicService.MusicInterface musicInterface;
    private String filePath;
    public MyServiceConn(String filePath){
        this.filePath = filePath;
    }
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        musicInterface = (MusicService.MusicInterface) iBinder;//中间人
        Log.v(TAG,"获取到musicInterface对象，立即播放");
        play();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public void play(){
        Log.v(TAG,"调用MusicService里的方法play");
        if(filePath != null){
            musicInterface.play(filePath);
        }
    }
}
