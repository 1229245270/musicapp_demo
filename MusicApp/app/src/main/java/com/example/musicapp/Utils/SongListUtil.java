package com.example.musicapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.musicapp.DateBase.UsersTable;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Service.MusicService;
import com.example.musicapp.Service.MyServiceConn;

import java.io.File;
import java.util.List;

public class SongListUtil {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void upSong(Context context){
        Context applicationContext = context.getApplicationContext();
        preferences = applicationContext.getSharedPreferences("mSetting",Context.MODE_PRIVATE);
        editor = preferences.edit();
        Song song = new UsersTable(applicationContext).getSongListBySongPath(preferences.getString("songPath",""));
        int rowNum = -1;
        if(song != null){
            rowNum= song.getRowNum();
        }
        switch (preferences.getInt("playOrder",0)){
            //顺序播放
            case 0:
                rowNum--;
                break;
            //随机播放
            case 1:
                int row = new UsersTable(applicationContext).getAllSongListNumber();
                rowNum = (int)(Math.random()*row);
                break;
            //单曲循环
            case 2:
                break;
        }
        Song upSong = new UsersTable(applicationContext).getSongListByRowNum(rowNum);
        if(upSong != null){
            autosStartService(upSong,applicationContext);
        }else{
            rowNum = new UsersTable(applicationContext).getAllSongListNumber() - 1;
            upSong = new UsersTable(applicationContext).getSongListByRowNum(rowNum);
            if(upSong != null){
                autosStartService(upSong,applicationContext);
            }else{
                Toast.makeText(applicationContext,"错误，歌曲不存在",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void nextSong(Context context){
        Context applicationContext = context.getApplicationContext();
        preferences = applicationContext.getSharedPreferences("mSetting",Context.MODE_PRIVATE);
        editor = preferences.edit();
        Song song = new UsersTable(applicationContext).getSongListBySongPath(preferences.getString("songPath",""));
        int rowNum = -1;
        if(song != null){
            rowNum= song.getRowNum();
        }
        switch (preferences.getInt("playOrder",0)){
            //顺序播放
            case 0:
                rowNum++;
                break;
            //随机播放
            case 1:
                int row = new UsersTable(applicationContext).getAllSongListNumber();
                rowNum = (int)(Math.random()*row);
                break;
            //单曲循环
            case 2:
                break;
        }
        Song nextSong = new UsersTable(applicationContext).getSongListByRowNum(rowNum);
        if(nextSong != null){
            autosStartService(nextSong,applicationContext);
        }else{
            rowNum = 0;
            nextSong = new UsersTable(applicationContext).getSongListByRowNum(rowNum);
            if(nextSong != null){
                autosStartService(nextSong,applicationContext);
            }else{
                Toast.makeText(applicationContext,"错误，歌曲不存在",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static void autosStartService(Song song,Context context){
        String fileName = song.getFileName();
        String songName = song.getSongName();
        String singer = song.getSinger();
        String songPath = song.getSongPath();
        String header = song.getSongHeader();
        String lyrics = song.getSongLyrics();
        String mv = song.getSongMv();
        int commentNum = song.getCommentNum();
        Long createDate = song.getCreateDate();
        //设置临时音乐数据
        editor.putString("songPath",songPath);
        editor.commit();

        MyServiceConn conn = new MyServiceConn(songPath);
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra("fileName", fileName);
        intent.putExtra("songName", songName);
        intent.putExtra("singer", singer);
        intent.putExtra("lyrics", lyrics);
        intent.putExtra("commentNum", commentNum);
        intent.putExtra("header", header);
        intent.putExtra("mv", mv);
        intent.putExtra("createDate", createDate);

        //设置Action的目的是为了让onBind()调用多次
        //当页面变化时，待修改
        intent.setAction(songPath);
        context.startService(intent);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);//绑定服务
    }

    //判断权限
    public static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean listEquals(List<Song> listEqual, List<Song> listDataBase) {
        //没有权限为null
        if(listEqual == null) {
            return true;
        }
        //第一次数据库没有数据,必定为null
        if(listDataBase == null) {
            return false;
        }
        if(listEqual.size() > 0 && listDataBase.size() > 0){
            if(listEqual.size() != listDataBase.size()){
                return false;
            }
            for (int i = 0; i < listEqual.size(); i++) {
                if (!(listEqual.get(i).getSongPath().equals(listDataBase.get(i).getSongPath()) &&
                        listEqual.get(i).getSongName().equals(listDataBase.get(i).getSongName()) &&
                        listEqual.get(i).getSinger().equals(listDataBase.get(i).getSinger()))) {
                    return false;
                }
            }
            //有权限没数据 listEqual = []
        }else if(listEqual.isEmpty() && listDataBase.isEmpty()){
            return true;
        }
        return true;
    }
    public static boolean fileIsExists(String strFile)
    {
        try {
            File f=new File(strFile);
            if(!f.exists()) {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    //点击事件
    public static void onClickPlay(Context context,Object object,List<Song> songList){
        //更新歌曲列表
        Song clickSong = ((Song)object);
        if(songList == null && clickSong != null) {
            String songPath = clickSong.getSongPath();
            String songHeader = Uri.encode(clickSong.getSongHeader(), "-![.:/,%?&=]");
            int rowNum = new UsersTable(context).getAllSongListNumber();
            Song song = new UsersTable(context).getSongListBySongPath(songPath);
            if(song == null){
                clickSong.setSongPath(songPath);
                clickSong.setSongHeader(songHeader);
                clickSong.setRowNum(rowNum);
                new UsersTable(context).insertPlayList(clickSong);
            }
        }else {
            List<Song> songListDataBase = new UsersTable(context).getAllSongList();
            if (!SongListUtil.listEquals(songList, songListDataBase)) {
                new UsersTable(context).deletePlayList();//必须用显示UserTable
                for (Song song : songList) {
                    song.setSongPath(song.getSongPath());
                    song.setSongHeader(Uri.encode(clickSong.getSongHeader(), "-![.:/,%?&=]"));
                    new UsersTable(context).insertPlayList(song);//必须用显示UserTable
                }
            }
        }
        if(clickSong != null){
            preferences = context.getSharedPreferences("mSetting",Context.MODE_PRIVATE);
            editor = preferences.edit();
            String songName = clickSong.getSongName();
            String singer = clickSong.getSinger();
            String songPath = Uri.encode(clickSong.getSongPath(), "-![.:/,%?&=]");
            String header = Uri.encode(clickSong.getSongHeader(), "-![.:/,%?&=]");
            String fileName = clickSong.getFileName();
            String lyrics = clickSong.getSongLyrics();
            int commentNum = clickSong.getCommentNum();
            String mv = clickSong.getSongMv();
            Long createDate = clickSong.getCreateDate();
            editor.putString("songPath",songPath);
            editor.commit();
            MyServiceConn conn = new MyServiceConn(songPath);
            Intent intent = new Intent(context, MusicService.class);
            intent.putExtra("songName", songName);
            intent.putExtra("singer", singer);
            intent.putExtra("fileName", fileName);
            intent.putExtra("header", header);
            intent.putExtra("lyrics", lyrics);
            intent.putExtra("commentNum", commentNum);
            intent.putExtra("mv", mv);
            intent.putExtra("createDate", createDate);

            //设置Action的目的是为了让onBind()调用多次
            intent.setAction(songPath);
            context.startService(intent);
            context.bindService(intent, conn, Context.BIND_AUTO_CREATE);//绑定服务
        }
    }
}
