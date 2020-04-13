package com.example.musicapp.DateBase;

/**
 * Created by PC on 2019/6/7.
 */

public class Data {
    public static final String DB_NAME = "database";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PLAYLIST = "tbPlayList";
    public static final String COL_ROWNUM = "colRowNum";
    public static final String COL_SONGNAME = "colSongName";
    public static final String COL_FILENAME = "colFileName";
    public static final String COL_SINGER = "colSinger";
    public static final String COL_SONGPATH = "colSongPath";
    public static final String COL_SONGHEADER = "colSongHeader";
    public static final String COL_SONGLYRICS = "colSongLyrics";
    public static final String COL_SONGMV = "colMv";
    public static final String COL_CREATETIME = "colCreateTime";
    public static final String COL_COMMENTNUM = "colCommentNum";
    public static final String SQL_TB_PALYLIST = "create table " + TABLE_PLAYLIST + "(" +
            //COL_ROWNUM + " integer primary key autoincrement," +
            COL_ROWNUM + " integer primary key," +
            COL_FILENAME + " text," +
            COL_SONGNAME + " text," +
            COL_SINGER + " text," +
            COL_SONGPATH + " text," +
            COL_SONGHEADER + " text," +
            COL_SONGLYRICS + " text," +
            COL_SONGMV + " text," +
            COL_COMMENTNUM + " integer," +
            COL_CREATETIME + " integer" +
            ")";

    public static final String TABLE_SEARCHSESSION = "tbSearchSession";
    public static final String COL_SEARCHTIME = "colSearchTime";
    public static final String COL_SEARCHSONG = "colSearchSong";
    public static final String SQL_TB_SEARCHSESSION = "create table " + TABLE_SEARCHSESSION + "(" +
            COL_SEARCHTIME + " datetime default(datetime('now','localtime'))," +
            COL_SEARCHSONG + " text" +
            ")";
    public static final String SQL_SELECT_ALL_PLAYLIST = "select * from " + TABLE_PLAYLIST;
    public static final String SQL_SELECT_PLAYLIST_BY_SONGPATH = "select * from " + TABLE_PLAYLIST + " where " + COL_SONGPATH + "= ?";
    public static final String SQL_SELECT_PLAYLIST_BY_ROWNUM = "select * from " + TABLE_PLAYLIST + " where " + COL_ROWNUM + "= ?";
    public static final String SQL_DELETE_ALL_PLAYLIST = "delete from " + TABLE_PLAYLIST;
    //public static final String SQL_UPDATE_PLAYLIST_BY_FILENAME = "update " + TABLE_PLAYLIST + " set " + COL_SONGSELECT + "= ? where " + COL_SINGER + "= ? and " + COL_SONGNAME + "= ?";

    public static final String SQL_SELECT_ALL_SEARCHSESSION = "select * from " + TABLE_SEARCHSESSION;
    public static final String SQL_SELECT_SEARCHSESSION_BY_SONG = "select * from " + TABLE_SEARCHSESSION + " where " + COL_SEARCHSONG + "= ?";
    public static final String SQL_DELETE_ALL_SEARCHSESSION = "delete from " + TABLE_SEARCHSESSION;
}
