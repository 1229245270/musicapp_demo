package com.example.musicapp.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicapp.Model.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicUtil {
    private static ContentResolver contentResolver;

    public static List<Song> getDanQuData(Context context){
        List<Song> danQuList = new ArrayList<Song>();
        contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if(cursor != null){
            while (cursor.moveToNext()){
                Song danQu = new Song();
                danQu.setSongName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                danQu.setSinger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                danQu.setSongPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                danQu.setSongDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                danQu.setSongId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                //int n = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                //int n = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.Albums));
                //song.setSongHeader(getAlbumArt(n));
                /*if(danQu.getSongSize() > 1000 * 800){
                    if(danQu.getSongName().contains("-")){
                        String[] strings = danQu.getSongName().split(" - ");//左右俩边有空格
                        danQu.setSinger(strings[0]);
                        danQu.setSongName(strings[1]);
                    }
                }
                if(!song.getSinger().equals("<unknown>")){
                    songList.add(song);
                    i++;
                }*/
            }
        }
        if(cursor != null){
            cursor.close();
        }

        return danQuList;
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
