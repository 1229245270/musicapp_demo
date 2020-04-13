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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SongInService {

    @Autowired
    private SongInRepository songInRepository;

    @Autowired
    private SongListRepository songListRepository;

    @Autowired
    private SongRepository songRepository;
    /**
     * （修改total）
     * 需要用户
     * 插入歌曲到歌单
     */
    public Object insertSongToList(SongIn songIn) throws SongException {
        SongList songList = songListRepository.findByListNameAndUserName(songIn.getListName(),songIn.getUserName());
        Song song = songRepository.findByFileName(songIn.getFileName());
        SongIn baseSongIn = songInRepository.findByFileNameAndListNameAndUserName(songIn.getFileName(),songIn.getListName(),songIn.getUserName());
        if(songList != null && song != null){
            if(baseSongIn == null){
                songIn.setUserName(songIn.getUserName());
                songIn.setFileName(songIn.getFileName());
                songIn.setListName(songIn.getListName());
                songList.setTotal(songList.getTotal() + 1);
                songListRepository.save(songList);
                return songInRepository.save(songIn);
            }
            return "歌单中已存在该歌曲";
        }
        return "歌单或歌曲不存在";
    }
    /**
     * （修改total）
     * 需要哪个用户
     * 移动歌曲到歌单
     */
    public Object updateSongToList(SongIn songIn,String toListName) throws SongException{
        SongIn baseSongIn = songInRepository.findByFileNameAndListNameAndUserName(songIn.getFileName(),songIn.getListName(),songIn.getUserName());
        SongList songList = songListRepository.findByListNameAndUserName(songIn.getListName(),songIn.getUserName());
        Song song = songRepository.findByFileName(songIn.getFileName());
        if(songList != null && song != null){
            SongList toSongList = songListRepository.findByListNameAndUserName(toListName,songIn.getUserName());
            if(toSongList != null){
                toSongList.setTotal(toSongList.getTotal() + 1);
                songList.setTotal(songList.getTotal() - 1);
                songListRepository.save(toSongList);
                songListRepository.save(songList);
                baseSongIn.setListName(toListName);
                return songInRepository.save(baseSongIn);
            }
            return "移动的歌单不存在";
        }
        return "歌单或歌曲不存在";
    }

    /**
     *
     * （修改total）
     * 需要用户
     * 删除歌单歌曲
     */
    @Transactional
    public Result<String> deleteSongIn(SongIn songIn) throws SongException{
        SongList songList = songListRepository.findByListNameAndUserName(songIn.getListName(),songIn.getUserName());
        int i = songInRepository.deleteByFileNameAndListNameAndUserName(songIn.getFileName(),songIn.getListName(),songIn.getUserName());
        if(i == 1){
            songList.setTotal(songList.getTotal() -1);
            songListRepository.save(songList);
            return ResultUtil.success();
        }else{
            return ResultUtil.error(ResultEnum.SONG_IN_DELETE_ERROR.getCode(),ResultEnum.SONG_IN_DELETE_ERROR.getMsg());
        }
    }
}
