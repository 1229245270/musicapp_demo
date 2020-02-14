package com.example.musicapp.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;


import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.example.musicapp.Service.MusicBroadcast.MAINACTIVITY;

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private Timer timer;
    private String hash;
    Boolean isPlayComplete = false;//判断播放是否结束
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG,"启动音乐播放服务");
        mediaPlayer = new MediaPlayer();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);//设定CUP锁定
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//播放结束监听事件
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.v(TAG,"播放结束");
                isPlayComplete = true;
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyMusicBinder(intent);
    }

    @Override
    public void onDestroy() {//关闭时释放资源
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        if(timer != null){
            timer.cancel();
            timer = null;
        }
        audioManager.abandonAudioFocus(audioFocusChangeListener);//释放焦点
        Log.v(TAG,"onDestroy");
    }

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){//指示申请的AudioFocus是短暂性的，很快释放
                pause();
            }else if(i == AudioManager.AUDIOFOCUS_GAIN){//指示申请得到的AudioFocus不知道会持续多久，一般是长期占有
                continuePlay();
            }else if(i == AudioManager.AUDIOFOCUS_LOSS){//失去AudioFocus，直接释放资源最好
                audioManager.abandonAudioFocus(audioFocusChangeListener);
                stop();
            }
        }
    };

    /*MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if(!mediaPlayer.isLooping()){
                audioManager.abandonAudioFocus(audioFocusChangeListener);
            }
        }
    };*/

    class MyMusicBinder extends Binder implements MusicInterface{
        private Intent intent;
        public MyMusicBinder(Intent intent){
            this.intent = intent;
        }
        @Override
        public void play(String filePath) {
            MusicService.this.play(intent,filePath);
        }

        @Override
        public void pause() {
            MusicService.this.pause();
        }

        @Override
        public void continuePlay() {
            MusicService.this.continuePlay();
        }

        @Override
        public void seekTo(int progress) {
            MusicService.this.seekTo(progress);
        }

        @Override
        public boolean isPlaying() {
            return MusicService.this.isPlaying();
        }

        @Override
        public int getCurrentPosition() {
            return MusicService.this.getCurrentPosition();
        }

        @Override
        public int getDuration() {
            return MusicService.this.getDuration();
        }
    }

    private boolean requestFocus(){
        //获得焦点
        int result = audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    //播放音乐
    public void play(final Intent intent, String filePath){
        mediaPlayer.reset();//重置
        if(requestFocus()){
            try{
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepareAsync();//异步准备，开启子线程加载资源
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    //prepare()方法准备完毕后，此方法调用
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        SeekPlayMessage(intent);
                        mediaPlayer.start();
                        Log.v(TAG,"开始播放中");
                        isPlayComplete = false;
                        if(timer != null){
                            timer = null;
                        }
                        SeekPlayCurrent();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //继续播放
    public void continuePlay(){
        if(mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
            Log.v(TAG,"继续播放中");
            isPlayComplete = false;
            if(timer != null){
                timer = null;
            }
            SeekPlayCurrent();
        }
    }
    //暂停播放
    public void pause(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            Log.v(TAG,"暂停播放");
            mediaPlayer.pause();
        }
    }
    //放弃播放
    public void stop(){
        if(mediaPlayer != null){
            Log.v(TAG,"放弃播放");
            mediaPlayer.stop();
        }
    }
    //更新进度条
    public void seekTo(int progress){
        if(mediaPlayer != null){
            mediaPlayer.seekTo(progress);
        }
    }

    //发送进度信息
    public void SeekPlayCurrent(){
        if(timer == null){
            Log.v(TAG,"创建timer对象");
            timer = new Timer();
            //timer就是开启子线程执行任务，与纯粹的子线程不同的是可以控制子线程执行的时间
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //获得歌曲当前播放进度
                Intent intent = new Intent();
                intent.putExtra("type",MAINACTIVITY);
                intent.putExtra("current",getCurrentPosition());
                intent.putExtra("isPlayComplete",false);
                if(mediaPlayer.isPlaying()){
                    sendBroadcast(intent);
                }
                else{
                    if(isPlayComplete){
                        //播放结束
                        intent.putExtra("isPlayComplete",true);
                    }
                    else{
                        intent.putExtra("isPlayComplete",false);
                    }
                    sendBroadcast(intent);
                    Log.v(TAG,"结束TimeTask");
                    timer.cancel();
                }
            }//开始计时任务后的200毫秒后第一次执行run方法，以后每500毫秒执行一次
        },200,500);
    }

    //发送播放信息
    public void SeekPlayMessage(Intent intent){
        intent.putExtra("type",MAINACTIVITY);
        intent.putExtra("duration",getDuration());
        intent.putExtra("current",getCurrentPosition());
        intent.putExtra("isPlayComplete",false);
        sendBroadcast(intent);
    }

    //获取播放状态
    public boolean isPlaying(){
        Boolean isPlaying = false;
        try {
            isPlaying = mediaPlayer.isPlaying();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isPlaying;
    }
    //获取当前播放位置
    public int getCurrentPosition(){
        int position = 0;
        try {
            position = mediaPlayer.getCurrentPosition();
        }catch (Exception e){
            e.printStackTrace();
        }
        return position;
    }
    //获取播放对象总时长
    public int getDuration(){
        int duration = 0;
        try{
            duration = mediaPlayer.getDuration();
        }catch (Exception e){
            e.printStackTrace();
        }
        return duration;
    }
    public interface MusicInterface{
        void play(String filePath);
        void pause();
        void continuePlay();
        void seekTo(int progress);
        boolean isPlaying();
        int getCurrentPosition();
        int getDuration();
    }
}
