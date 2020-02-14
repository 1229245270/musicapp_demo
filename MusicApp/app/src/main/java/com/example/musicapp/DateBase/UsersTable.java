package com.example.musicapp.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.musicapp.Model.Song;
import static com.example.musicapp.DateBase.Data.COL_SINGER;
import static com.example.musicapp.DateBase.Data.COL_SONGDURATION;
import static com.example.musicapp.DateBase.Data.COL_SONGHEADER;
import static com.example.musicapp.DateBase.Data.COL_SONGID;
import static com.example.musicapp.DateBase.Data.COL_SONGLYRICS;
import static com.example.musicapp.DateBase.Data.COL_SONGNAME;
import static com.example.musicapp.DateBase.Data.COL_SONGPATH;
import static com.example.musicapp.DateBase.Data.SQL_DELETE_ALL_PLAYLIST;
import static com.example.musicapp.DateBase.Data.SQL_SELECT_ALL_PLAYLIST;
import static com.example.musicapp.DateBase.Data.SQL_SELECT_PLAYLIST_BY_SONGID;
import static com.example.musicapp.DateBase.Data.TABLE_PLAYLIST;

/**
 * Created by PC on 2019/6/7.
 */

public class UsersTable {
    DatabaseHelper helper;
    SQLiteDatabase db;
    String hash;
    String TAG = "UsersTable";
    public UsersTable(Context context){
        helper = DatabaseHelper.getInstance(context);
        db = helper.getReadableDatabase();
    }

    public String getDatabaseName(){
        return helper.getDatabaseName();
    }

    public Boolean insertPlayList(Song song){
        long flag = 0;
        ContentValues values = new ContentValues();
        values.put(COL_SONGID,song.getSongId());
        values.put(COL_SONGNAME,song.getSongName());
        values.put(COL_SINGER,song.getSinger());
        values.put(COL_SONGPATH,song.getSongPath());
        values.put(COL_SONGHEADER,song.getSongHeader());
        values.put(COL_SONGDURATION,song.getSongDuration());
        values.put(COL_SONGLYRICS,song.getSongLyrics());
        try {
            flag = db.insert(TABLE_PLAYLIST,null,values);
            db.close();
        }catch (Exception e){
            e.getMessage();
            Log.v("database","insertPlayList失败");
            return false;
        }
        if(flag > 0) return true;
        Log.v("database","insertPlayList成功");
        return false;
    }
    public Song getSongListBySongId(String songId, final Context context){
        Song song = new Song();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT_PLAYLIST_BY_SONGID,new String[]{songId});
            if(cursor != null && cursor.moveToFirst()){
                do{
                    song.setSongId(cursor.getInt(cursor.getColumnIndex(COL_SONGID)));
                    song.setSongName(cursor.getString(cursor.getColumnIndex(COL_SONGNAME)));
                    song.setSinger(cursor.getString(cursor.getColumnIndex(COL_SINGER)));
                    song.setSongPath(cursor.getString(cursor.getColumnIndex(COL_SONGPATH)));
                    song.setSongHeader(cursor.getInt(cursor.getColumnIndex(COL_SONGHEADER)));
                    song.setSongDuration(cursor.getInt(cursor.getColumnIndex(COL_SONGDURATION)));
                    song.setSongLyrics(cursor.getString(cursor.getColumnIndex(COL_SONGLYRICS)));
                }while (cursor.moveToNext());
                /*if(song.getSongDuration() == 0 || song.getSongPath().equals("")){
                    hash = song.getHash();
                    Callable<String> callable = new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            return new HttpUtils("getHash",context).getJson("http://www.kugou.com/yy/index.php?r=play/getdata&hash=" + hash);
                        }
                    };
                    Future<String> future;
                    ExecutorService exeService = Executors.newFixedThreadPool(10);
                    future = exeService.submit(callable);
                    String jsonHash = null;
                    try {
                        jsonHash = future.get();
                        Gson gson = new Gson();
                        SongDetail songDetail = gson.fromJson(jsonHash,SongDetail.class);
                        if(songDetail != null){
                            if(songDetail.getStatus() == 1){
                                data data = songDetail.getData();
                                if(data != null){
                                    song.setSongPath(data.getPlay_url());
                                    song.setSongHeader(data.getImg());
                                    song.setSongDuration((int)data.getTimelength());
                                }
                            } else{
                                Log.v(TAG,"getHash接口挂掉了,getStatus == 0");
                                Toast.makeText(context,"接口挂掉了,暂无数据",Toast.LENGTH_SHORT).show();
                            }
                        } else Log.v(TAG,"getHash接口挂掉了");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v(TAG,"try错误");
                    }
                }*/
            }else{
                song = null;
                Log.v("database","getSongListBySongId对象为空");
            }
        }catch (Exception e){
            e.getMessage();
            Log.v("database","getSongListBySongId失败");
            song = null;
        }
        Log.v("database","getSongListBySongId成功");
        return song;
    }

    public Boolean deletePlayList(){
        try {
            db.execSQL(SQL_DELETE_ALL_PLAYLIST);
            db.close();
        }catch (Exception e){
            e.getMessage();
            Log.v("database","insertPlayList失败");
            return false;
        }
        Log.v("database","insertPlayList成功");
        return true;
    }

}
