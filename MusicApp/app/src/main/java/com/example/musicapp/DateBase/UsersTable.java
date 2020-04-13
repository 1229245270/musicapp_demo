package com.example.musicapp.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.musicapp.Model.SearchSession;
import com.example.musicapp.Model.Song;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.DateBase.Data.COL_COMMENTNUM;
import static com.example.musicapp.DateBase.Data.COL_CREATETIME;
import static com.example.musicapp.DateBase.Data.COL_FILENAME;
import static com.example.musicapp.DateBase.Data.COL_ROWNUM;
import static com.example.musicapp.DateBase.Data.COL_SEARCHSONG;
import static com.example.musicapp.DateBase.Data.COL_SEARCHTIME;
import static com.example.musicapp.DateBase.Data.COL_SINGER;
import static com.example.musicapp.DateBase.Data.COL_SONGHEADER;
import static com.example.musicapp.DateBase.Data.COL_SONGLYRICS;
import static com.example.musicapp.DateBase.Data.COL_SONGMV;
import static com.example.musicapp.DateBase.Data.COL_SONGNAME;
import static com.example.musicapp.DateBase.Data.COL_SONGPATH;
import static com.example.musicapp.DateBase.Data.SQL_DELETE_ALL_PLAYLIST;
import static com.example.musicapp.DateBase.Data.SQL_DELETE_ALL_SEARCHSESSION;
import static com.example.musicapp.DateBase.Data.SQL_SELECT_ALL_PLAYLIST;
import static com.example.musicapp.DateBase.Data.SQL_SELECT_ALL_SEARCHSESSION;
import static com.example.musicapp.DateBase.Data.SQL_SELECT_PLAYLIST_BY_ROWNUM;
import static com.example.musicapp.DateBase.Data.SQL_SELECT_PLAYLIST_BY_SONGPATH;
import static com.example.musicapp.DateBase.Data.SQL_SELECT_SEARCHSESSION_BY_SONG;
import static com.example.musicapp.DateBase.Data.TABLE_PLAYLIST;
import static com.example.musicapp.DateBase.Data.TABLE_SEARCHSESSION;

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
        values.put(COL_ROWNUM,song.getRowNum());
        values.put(COL_FILENAME,song.getFileName());
        values.put(COL_SONGNAME,song.getSongName());
        values.put(COL_COMMENTNUM,song.getCommentNum());
        values.put(COL_SINGER,song.getSinger());
        values.put(COL_SONGPATH,song.getSongPath());
        values.put(COL_SONGHEADER,song.getSongHeader());
        values.put(COL_SONGLYRICS,song.getSongLyrics());
        values.put(COL_SONGMV,song.getSongMv());
        values.put(COL_CREATETIME,song.getCreateDate());
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
    public Song getSongListBySongPath(String songPath){
        Song song = new Song();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT_PLAYLIST_BY_SONGPATH,new String[]{songPath});
            if(cursor != null && cursor.moveToFirst()){
                song.setRowNum(cursor.getInt(cursor.getColumnIndex(COL_ROWNUM)));
                song.setFileName(cursor.getString(cursor.getColumnIndex(COL_FILENAME)));
                song.setSongName(cursor.getString(cursor.getColumnIndex(COL_SONGNAME)));
                song.setCommentNum(cursor.getInt(cursor.getColumnIndex(COL_COMMENTNUM)));
                song.setSinger(cursor.getString(cursor.getColumnIndex(COL_SINGER)));
                song.setSongPath(cursor.getString(cursor.getColumnIndex(COL_SONGPATH)));
                song.setSongHeader(cursor.getString(cursor.getColumnIndex(COL_SONGHEADER)));
                song.setSongLyrics(cursor.getString(cursor.getColumnIndex(COL_SONGLYRICS)));
                song.setSongMv(cursor.getString(cursor.getColumnIndex(COL_SONGMV)));
                song.setCreateDate(cursor.getLong(cursor.getColumnIndex(COL_CREATETIME)));
            }else{
                song = null;
                Log.v("database","getSongListBySongPath对象为空");
            }
        }catch (Exception e){
            e.getMessage();
            Log.v("database","getSongListBySongPath失败");
            song = null;
        }
        Log.v("database","getSongListBySongPath成功");
        return song;
    }

    public Song getSongListByRowNum(int rowNum){
        Song song = new Song();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT_PLAYLIST_BY_ROWNUM,new String[]{String.valueOf(rowNum)});
            if(cursor != null && cursor.moveToFirst()){
                song.setRowNum(cursor.getInt(cursor.getColumnIndex(COL_ROWNUM)));
                song.setFileName(cursor.getString(cursor.getColumnIndex(COL_FILENAME)));
                song.setSongName(cursor.getString(cursor.getColumnIndex(COL_SONGNAME)));
                song.setCommentNum(cursor.getInt(cursor.getColumnIndex(COL_COMMENTNUM)));
                song.setSinger(cursor.getString(cursor.getColumnIndex(COL_SINGER)));
                song.setSongPath(cursor.getString(cursor.getColumnIndex(COL_SONGPATH)));
                song.setSongHeader(cursor.getString(cursor.getColumnIndex(COL_SONGHEADER)));
                song.setSongLyrics(cursor.getString(cursor.getColumnIndex(COL_SONGLYRICS)));
                song.setSongMv(cursor.getString(cursor.getColumnIndex(COL_SONGMV)));
                song.setCreateDate(cursor.getLong(cursor.getColumnIndex(COL_CREATETIME)));
            }else{
                song = null;
                Log.v("database","getSongListByRowNum对象为空");
            }
        }catch (Exception e){
            e.getMessage();
            Log.v("database","getSongListByRowNum失败");
            song = null;
        }
        Log.v("database","getSongListByRowNum成功");
        return song;
    }

    public List<Song> getAllSongList(){
        List<Song> songList = new ArrayList<Song>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT_ALL_PLAYLIST,null);
            if(cursor != null && cursor.moveToFirst()){
                do{
                    Song song = new Song();
                    song.setRowNum(cursor.getInt(cursor.getColumnIndex(COL_ROWNUM)));
                    song.setFileName(cursor.getString(cursor.getColumnIndex(COL_FILENAME)));
                    song.setSongName(cursor.getString(cursor.getColumnIndex(COL_SONGNAME)));
                    song.setSinger(cursor.getString(cursor.getColumnIndex(COL_SINGER)));
                    song.setCommentNum(cursor.getInt(cursor.getColumnIndex(COL_COMMENTNUM)));
                    song.setSongPath(cursor.getString(cursor.getColumnIndex(COL_SONGPATH)));
                    song.setSongHeader(cursor.getString(cursor.getColumnIndex(COL_SONGHEADER)));
                    song.setSongLyrics(cursor.getString(cursor.getColumnIndex(COL_SONGLYRICS)));
                    song.setSongMv(cursor.getString(cursor.getColumnIndex(COL_SONGMV)));
                    song.setCreateDate(cursor.getLong(cursor.getColumnIndex(COL_CREATETIME)));
                    songList.add(song);
                }while (cursor.moveToNext());
            }else{
                songList = null;
                Log.v("database","getAllSongList对象为null");
            }
        }catch (Exception e){
            e.getMessage();
            Log.v("database","getAllSongList失败");
            songList = null;
        }
        Log.v("database","getAllSongList成功");
        return songList;
    }
    public int getAllSongListNumber(){
        Cursor cursor = null;
        int number = 0;
        try {
            cursor = db.rawQuery(SQL_SELECT_ALL_PLAYLIST,null);
            if(cursor != null && cursor.moveToFirst()){
                do{
                    number++;
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.getMessage();
            Log.v("database","getAllSongListNumber失败");
        }
        Log.v("database","getAllSongListNumber成功");
        return number;
    }

    public Boolean deletePlayList(){
        try {
            db.execSQL(SQL_DELETE_ALL_PLAYLIST);
            db.close();
        }catch (Exception e){
            e.getMessage();
            Log.v("database","deletePlayList失败");
            return false;
        }
        Log.v("database","deletePlayList成功");
        return true;
    }

    public Boolean insertSearchSession(SearchSession searchSession){
        long flag = 0;
        ContentValues values = new ContentValues();
        values.put(COL_SEARCHSONG,searchSession.getSearchSong());
        try {
            flag = db.insert(TABLE_SEARCHSESSION,null,values);
            db.close();
        }catch (Exception e){
            e.getMessage();
            Log.v("database","insertSearchSession失败");
            return false;
        }
        if(flag > 0){
            Log.v("database","insertSearchSession成功");
            return true;
        }
        return false;
    }
    public SearchSession getSearchSessionBySearchSong(String song){
        SearchSession searchSession = new SearchSession();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT_SEARCHSESSION_BY_SONG,new String[]{song});
            if(cursor != null && cursor.moveToFirst()){
                do{
                    searchSession.setSearchSong(cursor.getString(cursor.getColumnIndex(COL_SEARCHSONG)));
                    searchSession.setSearchTime(cursor.getString(cursor.getColumnIndex(COL_SEARCHTIME)));
                }while (cursor.moveToNext());
            }else{
                searchSession = null;
                Log.v("database","getSearchSessionBySearchSong对象为空");
            }
        }catch (Exception e){
            e.getMessage();
            Log.v("database","getSearchSessionBySearchSong失败");
            searchSession = null;
        }
        Log.v("database","getSearchSessionBySearchSong成功");
        return searchSession;
    }

    public List<SearchSession> getAllSearchSession(){
        List<SearchSession> searchSessions = new ArrayList<SearchSession>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT_ALL_SEARCHSESSION,null);
            if(cursor != null && cursor.moveToFirst()){
                do{
                    SearchSession searchSession = new SearchSession();
                    searchSession.setSearchTime(cursor.getString(cursor.getColumnIndex(COL_SEARCHTIME)));
                    searchSession.setSearchSong(cursor.getString(cursor.getColumnIndex(COL_SEARCHSONG)));
                    searchSessions.add(searchSession);
                }while (cursor.moveToNext());
            }else{
                searchSessions = null;
                Log.v("database","getAllSearchSession对象为空");
            }
        }catch (Exception e){
            e.getMessage();
            Log.v("database","getAllSearchSession失败");
            searchSessions = null;
        }
        Log.v("database","getAllSearchSession成功");
        return searchSessions;
    }
    public Boolean deleteSearchSession(){
        try {
            db.execSQL(SQL_DELETE_ALL_SEARCHSESSION);
            db.close();
        }catch (Exception e){
            e.getMessage();
            Log.v("database","deleteSearchSession失败");
            return false;
        }
        Log.v("database","deleteSearchSession成功");
        return true;
    }
}
