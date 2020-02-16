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
        List<Song> songList = new ArrayList<Song>();
        contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if(cursor != null){
            while (cursor.moveToNext()){
                Song song = new Song();
                song.setSongName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                song.setSinger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                song.setSongPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                song.setSongDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                song.setSongId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                song.setSongSize(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                //int n = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                //int n = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.Albums));
                //song.setSongHeader(getAlbumArt(n));
                if(song.getSongSize() > 1000 * 800){
                    if(song.getSongName().contains("-")){
                        String[] strings = song.getSongName().split(" - ");//左右俩边有空格
                        song.setSinger(strings[0]);
                        song.setSongName(strings[1]);
                    }
                }
                if(!song.getSinger().equals("<unknown>")){
                    songList.add(song);
                }
            }
        }
        if(cursor != null){
            cursor.close();
        }

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
