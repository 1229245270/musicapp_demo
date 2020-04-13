package com.example.musicapp.Model;

import android.view.View;

public class TabMeMenuDown {
    private Object icon;
    private String Name;
    private int SongNum;
    private int SongDownNum;
    private String Hint;

    public TabMeMenuDown(Object icon, String name, int songNum, int songDownNum, String hint) {
        this.icon = icon;
        Name = name;
        SongNum = songNum;
        SongDownNum = songDownNum;
        Hint = hint;
    }

    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
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

    public String getHint() {
        return Hint;
    }

    public void setHint(String hint) {
        Hint = hint;
    }

    @Override
    public String toString() {
        return "TabMeMenuDown{" +
                "icon=" + icon +
                ", Name='" + Name + '\'' +
                ", SongNum=" + SongNum +
                ", SongDownNum=" + SongDownNum +
                ", Hint='" + Hint + '\'' +
                '}';
    }
}
