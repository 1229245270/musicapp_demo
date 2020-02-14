package com.example.musicapp.Model;

public class Song {
    private int songId;
    private String songName;
    private String singer;
    private String songPath;
    private int songHeader;
    private int songDuration;
    private int songSize;
    private String songLyrics;

    public Song() {
    }

    public Song(int songId, String songName, String singer, String songPath, int songHeader, int songDuration, int songSize, String songLyrics) {
        this.songId = songId;
        this.songName = songName;
        this.singer = singer;
        this.songPath = songPath;
        this.songHeader = songHeader;
        this.songDuration = songDuration;
        this.songSize = songSize;
        this.songLyrics = songLyrics;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
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

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public int getSongHeader() {
        return songHeader;
    }

    public void setSongHeader(int songHeader) {
        this.songHeader = songHeader;
    }

    public int getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    public int getSongSize() {
        return songSize;
    }

    public void setSongSize(int songSize) {
        this.songSize = songSize;
    }

    public String getSongLyrics() {
        return songLyrics;
    }

    public void setSongLyrics(String songLyrics) {
        this.songLyrics = songLyrics;
    }
}
