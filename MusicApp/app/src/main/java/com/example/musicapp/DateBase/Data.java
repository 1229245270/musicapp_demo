package com.example.musicapp.DateBase;

/**
 * Created by PC on 2019/6/7.
 */

public class Data {
    public static final String DB_NAME = "database";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PLAYLIST = "tbPlayList";
    public static final String COL_SONGID = "colSongId";
    public static final String COL_SONGNAME = "colSongName";
    public static final String COL_SINGER = "colSinger";
    public static final String COL_SONGPATH = "colSongPath";
    public static final String COL_SONGDURATION = "colSongDuration";
    public static final String COL_SONGHEADER = "colSongHeader";
    public static final String COL_SONGLYRICS = "colSongLyrics";
    public static final String SQL_TB_PALYLIST = "create table " + TABLE_PLAYLIST + "(" +
            COL_SONGID + " integer," +
            COL_SONGNAME + " text," +
            COL_SINGER + " text," +
            COL_SONGPATH + " text," +
            COL_SONGDURATION + " integer," +
            COL_SONGHEADER + " text," +
            COL_SONGLYRICS + " text" +
            ")";

    public static final String SQL_SELECT_ALL_PLAYLIST = "select * from " + TABLE_PLAYLIST;
    public static final String SQL_SELECT_PLAYLIST_BY_SONGID = "select * from " + TABLE_PLAYLIST + " where " + COL_SONGID + "= ?";
    public static final String SQL_DELETE_ALL_PLAYLIST = "delete from " + TABLE_PLAYLIST;
}
