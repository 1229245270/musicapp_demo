package com.example.musicdemo.handle;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.exception.SongException;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        if(e instanceof SongException){
            SongException songException = (SongException) e;
            return ResultUtil.error(songException.getCode(),songException.getMessage());
        }else {
            return ResultUtil.error(-1, e.getMessage());
        }
    }
}
