package com.example.musicdemo.controller;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.Song;
import com.example.musicdemo.entity.SongIn;
import com.example.musicdemo.entity.SongList;
import com.example.musicdemo.service.*;
import com.example.musicdemo.utils.ResultUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    //C:/default要求singer - songName名字
    @PostMapping(value = "hello/default")
    public String hello(){
        File file = new File("C:/musicDemo/song");
        songService.insertDefault(file);
        return "hello default";
    }

    /**
     * 搜索歌曲信息
     */
    @GetMapping(value = "/song/all")
    public Result<List<Song>> songSelectAll(@RequestParam("page") int page,@RequestParam("size") int size){
        return ResultUtil.success(songService.findSongAll(page,size));
    }

    /**
     * 根据fileName搜索歌曲信息
     */
    @GetMapping(value = "/song/{fileName}")
    public Result<Song> songSelectByFileName(@PathVariable("fileName") String fileName){
        return ResultUtil.success(songService.findSongByFileName(fileName));
    }

    /**
     * 模糊搜索歌曲
     */
    @GetMapping(value = "/song/like/{searchName}")
    public Result<List<Song>> songSelectBySearchName(@PathVariable("searchName") String searchName,@RequestParam(value = "page") int page){
        return ResultUtil.success(songService.findSongBySearchName(searchName,page));
    }

    /**
     * 添加歌曲文件或修改
     */
    @PostMapping(value = "/song")
    public Result<String> songAdd(@RequestParam("fileName") MultipartFile file,@RequestParam(value = "songName",defaultValue = "",required = false) String songName,@RequestParam(value = "singer",defaultValue = "",required = false) String singer){
        if("".equals(songName) || "".equals(singer)){
            return songService.insertSong(file);
        }else{
            return songService.insertSong(file,songName,singer);
        }
    }

    /**
     * 删除歌曲
     */
    @DeleteMapping(value = "/song/{fileName}")
    public Result<String> songDelete(@PathVariable("fileName") String fileName){
        return songService.deleteFileName(fileName);
    }
}
