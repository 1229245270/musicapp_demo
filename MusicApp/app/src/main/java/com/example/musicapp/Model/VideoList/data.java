package com.example.musicapp.Model.VideoList;

import java.util.List;

public class data {
    private int id;
    private String tag;
    private String videoPath;
    private String songName;
    private String singerHeader;
    private String singer;
    private int getLikeNum;
    private int commentNum;
    private long createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerHeader() {
        return singerHeader;
    }

    public void setSingerHeader(String singerHeader) {
        this.singerHeader = singerHeader;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getGetLikeNum() {
        return getLikeNum;
    }

    public void setGetLikeNum(int getLikeNum) {
        this.getLikeNum = getLikeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
