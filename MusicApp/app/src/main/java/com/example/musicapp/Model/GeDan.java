package com.example.musicapp.Model;

public class GeDan {
    private String tag;
    private int image;
    private int listener;
    private String songListName;
    private String songListIntro;
    private String author;
    private int comment;

    public GeDan(String tag, int image, int listener, String songListName, String songListIntro, String author, int comment) {
        this.tag = tag;
        this.image = image;
        this.listener = listener;
        this.songListName = songListName;
        this.songListIntro = songListIntro;
        this.author = author;
        this.comment = comment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getListener() {
        return listener;
    }

    public void setListener(int listener) {
        this.listener = listener;
    }

    public String getSongListName() {
        return songListName;
    }

    public void setSongListName(String songListName) {
        this.songListName = songListName;
    }

    public String getSongListIntro() {
        return songListIntro;
    }

    public void setSongListIntro(String songListIntro) {
        this.songListIntro = songListIntro;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
