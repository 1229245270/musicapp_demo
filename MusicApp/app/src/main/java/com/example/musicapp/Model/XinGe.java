package com.example.musicapp.Model;

public class XinGe {
    private int image;
    private String songName;
    private String singer;
    private int comment;

    public XinGe(int image, String songName, String singer, int comment) {
        this.image = image;
        this.songName = songName;
        this.singer = singer;
        this.comment = comment;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
