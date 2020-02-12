package com.example.musicapp.Model;

public class ShiPin {
    private String tag;
    private int movie;
    private int time;
    private int listener;
    private String title;
    private int authorHeader;
    private String author;
    private int getLike;
    private int comment;

    public ShiPin(String tag, int movie, int time, int listener, String title, int authorHeader, String author, int getLike, int comment) {
        this.tag = tag;
        this.movie = movie;
        this.time = time;
        this.listener = listener;
        this.title = title;
        this.authorHeader = authorHeader;
        this.author = author;
        this.getLike = getLike;
        this.comment = comment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getMovie() {
        return movie;
    }

    public void setMovie(int movie) {
        this.movie = movie;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getListener() {
        return listener;
    }

    public void setListener(int listener) {
        this.listener = listener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorHeader() {
        return authorHeader;
    }

    public void setAuthorHeader(int authorHeader) {
        this.authorHeader = authorHeader;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
