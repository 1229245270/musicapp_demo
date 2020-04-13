package com.example.musicdemo.controller;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.Song;
import com.example.musicdemo.entity.SongList;
import com.example.musicdemo.service.SongListService;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
public class SongListController {

    @Autowired
    private SongListService songListService;

    @GetMapping(value = "/songList/all")
    public Result<SongList> selectListAll(@RequestParam("page") int page,@RequestParam("size") int size){
        return ResultUtil.success(songListService.findSongList(page,size));
    }

    @GetMapping(value = "/songList/{userName}")
    public Result<SongList> selectList(@PathVariable("userName") String userName){
        return ResultUtil.success(songListService.findSongListByUserName(userName));
    }

    @GetMapping(value = "/songList")
    public Result<Song> getSongBySongList(@Valid SongList songList){
        return ResultUtil.success(songListService.findSongBySongList(songList));
    }
    /**
     * 谁添加,修改歌单
     */
    @PostMapping(value = "/songList")
    public Result<SongList> add(@Valid SongList songList, @RequestParam(value = "file",required = false) MultipartFile file, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtil.success(songListService.insertListSong(songList,file));
    }

    /**
     * 删除歌单
     */
    @DeleteMapping(value = "/songList")
    public Result<String> delete(@RequestParam("userName") String userName,@RequestParam("listName") String listName){
        return songListService.deleteSongList(userName,listName);
    }
}
