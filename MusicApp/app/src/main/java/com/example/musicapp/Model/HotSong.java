package com.example.musicapp.Model;

public class HotSong {
    private int ranking;
    private String songName;
    private String Intro;

    public HotSong(int ranking, String songName, String intro) {
        this.ranking = ranking;
        this.songName = songName;
        Intro = intro;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }
}
