package com.example.musicapp.Model.SongList;

import java.util.List;

public class SongList {
    private int code;
    private String msg;
    private List<data> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<com.example.musicapp.Model.SongList.data> getData() {
        return data;
    }

    public void setData(List<com.example.musicapp.Model.SongList.data> data) {
        this.data = data;
    }

}
