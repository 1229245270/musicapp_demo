package com.example.musicdemo.service;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.Song;
import com.example.musicdemo.entity.SongIn;
import com.example.musicdemo.entity.SongList;
import com.example.musicdemo.enums.ResultEnum;
import com.example.musicdemo.exception.SongException;
import com.example.musicdemo.repository.SongInRepository;
import com.example.musicdemo.repository.SongListRepository;
import com.example.musicdemo.repository.SongRepository;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongListService {

    @Autowired
    private SongListRepository songListRepository;

    @Autowired
    private SongInRepository songInRepository;

    @Autowired SongRepository songRepository;

    /**
     *  查找用户歌单列表
     *  */
    public List<SongList> findSongListByUserName(String userName) throws SongException{
        return songListRepository.findByUserName(userName);
    }
    /**
     *  查找
     *  */
    public List<SongList> findSongList(int page,int size) throws SongException{
        PageRequest pr = PageRequest.of(page,size);
        return songListRepository.findAllByOrderByCreateDateDesc(pr);
    }

    /**
     *  查找用户歌单歌曲
     *  */
    public List<Song> findSongBySongList(SongList songList) throws SongException{
        List<SongIn> songIns = songInRepository.findByListNameAndUserName(songList.getListName(),songList.getUserName());
        List<Song> songs = new ArrayList<>();
        for(SongIn songIn : songIns){
            songs.add(songRepository.findByFileName(songIn.getFileName()));
        }
        return songs;
    }

    /**（修改）
     *  添加修改歌单
     *  */
    public SongList insertListSong(SongList songList, MultipartFile file) throws SongException{
        SongList baseSongList = songListRepository.findByListNameAndUserName(songList.getListName(),songList.getUserName());
        if(baseSongList != null){
            songList.setId(baseSongList.getId());
            songList.setCreateDate(baseSongList.getCreateDate());
        }
        songList.setUserName(songList.getUserName());
        songList.setTag(songList.getTag());
        songList.setListName(songList.getListName());
        songList.setTotal(songList.getTotal());
        songList.setTag(songList.getTag());
        songList.setIntro(songList.getIntro());
        songList.setListenNum(songList.getListenNum());
        try {
            int code = -1;
            if(file != null && file.getOriginalFilename() != null){
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                if(".jpg".equals(suffix) || ".png".equals(suffix) || ".bmp".equals(suffix) || ".webp".equals(suffix) || ".tif".equals(suffix) || ".gif".equals(suffix) ){
                    code = 200;
                }
                if(code == 200) {
                    int id = songList.getId();
                    File clientFile = new File("C:/musicDemo/songList/" + id);
                    file.transferTo(clientFile);
                    songList.setImg("http://47.107.232.78:8080/songPath/songList/" + id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songListRepository.save(songList);
    }

    /**
     *  删除歌单，同时删除歌单歌曲连接
     *  */
    @Transactional
    public Result<String> deleteSongList(String userName, String listName) throws SongException{
        List<SongIn> songIns = songInRepository.findByListName(listName);
        int code = 1;
        for (SongIn songIn : songIns) {
            int k = songInRepository.deleteById((int) songIn.getId());
            if (k == 0) {
                code = -1;
            }
        }
        int code2 = -1;
        String[] suffixes = new String[]{".png",".jpg",".bmp",".webp",".tif",".gif"};
        File[] files = new File[suffixes.length];
        String imgName = listName + " - " + userName;
        for(int m = 0;m < files.length;m++){
            files[m] = new File("C:/musicDemo/songList/" + imgName + suffixes[m]);
        }
        for(File file : files){
            if(file.exists()){
                if(file.delete()){
                    code2 = 200;
                }
            }
        }
        int i = songListRepository.deleteByUserNameAndListName(userName,listName);
        if(code == 1){
            if(i == 1){
                return ResultUtil.success();
            }else{
                return ResultUtil.error(ResultEnum.SONG_LIST_DELETE_ERROR.getCode(),ResultEnum.SONG_LIST_DELETE_ERROR.getMsg());
            }
        }else{
            return ResultUtil.error(ResultEnum.SONG_LIST_DELETE_ERROR2.getCode(),ResultEnum.SONG_LIST_DELETE_ERROR2.getMsg());
        }

    }

}
