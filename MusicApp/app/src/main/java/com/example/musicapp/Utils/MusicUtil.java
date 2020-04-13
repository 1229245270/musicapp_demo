package com.example.musicapp.Utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.musicapp.Model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MusicUtil {
    public static List<Song> getDanQuData(Context context){
        //MediaScannerConnection.scanFile(context, new String[] {new File("/storage/emulated/0/").getAbsolutePath()},null, null);
        /*Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(new File("/storage/emulated/0/")));
        context.sendBroadcast(intent);*/
        List<Song> songList = new ArrayList<>();
        if(SongListUtil.lacksPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)){
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if(cursor != null){
                int i = 0;
                while (cursor.moveToNext()){
                    Song song = new Song();
                    song.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                    song.setSongName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                    song.setSinger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                    String songPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    song.setSongPath(songPath);
                    song.setSongSize(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                    song.setRowNum(i);
                    song.setSelect(false);
                    //int n = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                    //int n = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.Albums));
                    //song.setSongHeader(getAlbumArt(n));
                    if(song.getSongSize() > 1000 * 800){
                        if(song.getFileName().contains("-")){
                            String[] strings = song.getSongName().split(" - ");//左右俩边有空格
                            song.setSinger(strings[0]);
                            song.setSongName(strings[1]);
                        }
                    }
                    if(!song.getSinger().equals("<unknown>") && SongListUtil.fileIsExists(songPath)){
                        songList.add(song);
                        i++;
                    }
                }
            }
            if(cursor != null){
                cursor.close();
            }
        }else{
            songList = null;
        }
        //没有数据是返回 songList = [] 与 null 不相等
        return songList;
    }


    /*private static String getAlbumArt(int album_id){
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = contentResolver.query(  Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),  projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToFirst();
            album_art = cur.getString(0);
        }
        cur.close();
        return album_art;
    }*/
}
