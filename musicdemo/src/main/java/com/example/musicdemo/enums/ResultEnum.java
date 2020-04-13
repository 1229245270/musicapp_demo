package com.example.musicdemo.enums;

import com.example.musicdemo.entity.Result;

/**
 * @author zhiqiang.huang
 */

public enum ResultEnum {
    //歌曲路径错误
    SONG_INSERT_ERROR(404,"歌曲路径错误"),
    SONG_INSERT_SUFFIX_ERROR(402,"文件格式不对"),
    SONG_LIST_DELETE_ERROR(404,"歌单不存在"),
    SONG_LIST_DELETE_ERROR2(-1,"歌单删除失败"),
    SONG_IN_DELETE_ERROR(-1,"歌曲删除失败"),
    LOGIN_ERROR(400,"账号或密码错误"),
    LOGIN_ERROR_NULL(404,"账号不存在"),
    LOGIN_ERROR_ALL(-1,"请先完成登录"),
    LOGIN_ERROR_HAVE(201,"用户已存在"),
    LOGIN_ERROR_IMAGE(-1,"图片格式不正确或图片不存在"),
    COMMENT_ERROR(201,"您已经有相同的评论");
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
