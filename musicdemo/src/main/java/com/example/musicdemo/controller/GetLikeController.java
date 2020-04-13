package com.example.musicdemo.controller;

import com.example.musicdemo.entity.GetLike;
import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.SongIn;
import com.example.musicdemo.service.GetLikeService;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GetLikeController {

    @Autowired
    private GetLikeService getLikeService;
    /**
     * 点赞 - 取消点赞评论
     */
    @PostMapping(value = "/getLike")
    public Result<Object> songInAdd(@Valid GetLike getLike, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(-1,bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(getLikeService.insertGetLike(getLike));
    }
}
