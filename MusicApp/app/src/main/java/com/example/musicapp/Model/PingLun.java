package com.example.musicapp.Model;

public class PingLun {
    private String content;
    private String songName;
    private int commentatorHeader;
    private int songHeader;
    private String commentator;
    private int getLike;
    private int comment;

    public PingLun(String content, String songName, int commentatorHeader, int songHeader, String commentator, int getLike, int comment) {
        this.content = content;
        this.songName = songName;
        this.commentatorHeader = commentatorHeader;
        this.songHeader = songHeader;
        this.commentator = commentator;
        this.getLike = getLike;
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getCommentatorHeader() {
        return commentatorHeader;
    }

    public void setCommentatorHeader(int commentatorHeader) {
        this.commentatorHeader = commentatorHeader;
    }

    public int getSongHeader() {
        return songHeader;
    }

    public void setSongHeader(int songHeader) {
        this.songHeader = songHeader;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public int getGetLike() {
        return getLike;
    }

    public void setGetLike(int getLike) {
        this.getLike = getLike;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
