package com.example.musicapp.Model;

public class XinGe {
    private String fileName;
    private String songName;
    private String singer;
    private String songHeader;
    private String songLyrics;
    private boolean isSelect;
    private String songPath;
    private String songMv;
    private long createDate;
    private int commentNum;

    public XinGe(String fileName, String songName, String singer, String songHeader, String songLyrics, boolean isSelect, String songPath, String songMv, int commentNum) {
        this.fileName = fileName;
        this.songName = songName;
        this.singer = singer;
        this.songHeader = songHeader;
        this.songLyrics = songLyrics;
        this.isSelect = isSelect;
        this.songPath = songPath;
        this.songMv = songMv;
        this.commentNum = commentNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getSongMv() {
        return songMv;
    }

    public void setSongMv(String songMv) {
        this.songMv = songMv;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
}