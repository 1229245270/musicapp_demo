package com.example.musicdemo.exception;

import com.example.musicdemo.enums.ResultEnum;

public class SongException extends RuntimeException {
    private Integer code;

    public SongException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
