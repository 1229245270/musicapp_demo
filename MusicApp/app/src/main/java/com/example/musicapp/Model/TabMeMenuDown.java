package com.example.musicapp.Model;

import android.view.View;

public class TabMeMenuDown {
    private int icon;
    private String Name;
    private int SongNum;
    private int SongDownNum;
    private int HintImg;

    public TabMeMenuDown(int icon, String name, int songNum, int songDownNum, int hintImg) {
        this.icon = icon;
        Name = name;
        SongNum = songNum;
        SongDownNum = songDownNum;
        HintImg = hintImg;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSongNum() {
        return SongNum;
    }

    public void setSongNum(int songNum) {
        SongNum = songNum;
    }

    public int getSongDownNum() {
        return SongDownNum;
    }

    public void setSongDownNum(int songDownNum) {
        SongDownNum = songDownNum;
    }

    public int getHintImg() {
        return HintImg;
    }

    public void setHintImg(int hintImg) {
        HintImg = hintImg;
    }
}
