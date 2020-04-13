package com.example.musicdemo.controller;

import com.example.musicdemo.entity.Comment;
import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.SongIn;
import com.example.musicdemo.service.CommentService;
import com.example.musicdemo.service.SongInService;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 查歌曲评论列表
     */
    @GetMapping(value = "/comment/{fileName}")
    public Result<Object> getCommentList(@PathVariable("fileName") String fileName,@RequestParam("page") int page,@RequestParam("size") int size){
        return ResultUtil.success(commentService.findByFileName(fileName,page,size));
    }
    /**
     *
     */
    @GetMapping(value = "/comment/all")
    public Result<List<Comment>> getCommentAll(@RequestParam("page") int page,@RequestParam("size") int size){
        return ResultUtil.success(commentService.findByAll(page,size));
    }

    /**
     * 查评论
     */
    @GetMapping(value = "/comment")
    public Result<Object> getComment(@Valid Comment comment){
        return ResultUtil.success(commentService.findByComment(comment));
    }
    /**
     * (先去查评论)
     * 写评论
     */
    @PostMapping(value = "/comment")
    public Result<Comment> songInAdd(@Valid Comment comment, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(-1,bindingResult.getFieldError().getDefaultMessage());
        }
        return commentService.insertComment(comment);
    }

}
