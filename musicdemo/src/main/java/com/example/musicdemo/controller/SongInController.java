package com.example.musicdemo.controller;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.SongIn;
import com.example.musicdemo.service.SongInService;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class SongInController {

    @Autowired
    private SongInService songInService;
    /**
     * 添加歌曲到歌单
     */
    @PostMapping(value = "/songIn")
    public Result<Object> songInAdd(@Valid SongIn songIn, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(-1,bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(songInService.insertSongToList(songIn));
    }
    /**
     * 移动歌曲到歌单
     */
    @PutMapping(value = "/songIn")
    public Result<Object> songInUpdate(@Valid SongIn songIn, @RequestParam String toListName, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(-1,bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(songInService.updateSongToList(songIn,toListName));
    }
    /**
     * 删除歌单中的歌曲
     */
    @DeleteMapping(value = "/songIn")
    public Result<String> songInDelete(@Valid SongIn songIn){
        return songInService.deleteSongIn(songIn);
    }
}
