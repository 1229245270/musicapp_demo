package com.example.musicapp.Model;

import java.util.Date;

public class Song {
    private int rowNum;
    private String fileName;
    private String songName;
    private String singer;
    private String songHeader;
    private long songSize;
    private String songLyrics;
    private boolean isSelect;
    private String songPath;
    private String songMv;
    private long createDate;
    private int commentNum;

    public Song() {
    }

    public Song(String songName, String singer, String songHeader,String songPath, String songLyrics, boolean isSelect) {
        this.songName = songName;
        this.singer = singer;
        this.songHeader = songHeader;
        this.songPath = songPath;
        this.songLyrics = songLyrics;
        this.isSelect = isSelect;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSongMv() {
        return songMv;
    }

    public void setSongMv(String songMv) {
        this.songMv = songMv;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSongHeader() {
        return songHeader;
    }

    public void setSongHeader(String songHeader) {
        this.songHeader = songHeader;
    }

    public long getSongSize() {
        return songSize;
    }

    public void setSongSize(long songSize) {
        this.songSize = songSize;
    }

    public String getSongLyrics() {
        return songLyrics;
    }

    public void setSongLyrics(String songLyrics) {
        this.songLyrics = songLyrics;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    @Override
    public String toString() {
        return "Song{" +
                "rowNum=" + rowNum +
                ", songName='" + songName + '\'' +
                ", singer='" + singer + '\'' +
                ", songHeader='" + songHeader + '\'' +
                ", songSize=" + songSize +
                ", songLyrics='" + songLyrics + '\'' +
                ", isSelect=" + isSelect +
                ", songPath='" + songPath + '\'' +
                ", songMv='" + songMv + '\'' +
                '}';
    }
}
