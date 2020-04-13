package com.example.musicapp.Model.RegisterUser;

public class RegisterUser {
    private int code = 200;
    private String msg;
    private data data;

    public RegisterUser() {
    }

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

    public com.example.musicapp.Model.RegisterUser.data getData() {
        return data;
    }

    public void setData(com.example.musicapp.Model.RegisterUser.data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
