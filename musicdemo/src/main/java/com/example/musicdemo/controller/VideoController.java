package com.example.musicdemo.controller;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.User;
import com.example.musicdemo.enums.ResultEnum;
import com.example.musicdemo.interceptor.Constant;
import com.example.musicdemo.service.VideoService;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping(value = "/video/all")
    public Result<Object> getVideoAll(@RequestParam("page") int page,@RequestParam("size") int size){
        return ResultUtil.success(videoService.getAllVideo(page, size));
    }

    @GetMapping(value = "/zhibo/all")
    public Result<Object> getZhiboAll(@RequestParam("page") int page,@RequestParam("size") int size){
        return ResultUtil.success(videoService.getAllZhibo(page, size));
    }
}
