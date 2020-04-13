package com.example.musicapp.Model;

public class SearchSession {
    private String searchTime;
    private String searchSong;

    public SearchSession() {
    }

    public SearchSession(String searchSong) {
        this.searchSong = searchSong;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public String getSearchSong() {
        return searchSong;
    }

    public void setSearchSong(String searchSong) {
        this.searchSong = searchSong;
    }
}
