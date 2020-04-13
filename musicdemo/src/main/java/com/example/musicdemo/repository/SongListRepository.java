package com.example.musicdemo.repository;

import com.example.musicdemo.entity.Song;
import com.example.musicdemo.entity.SongList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongListRepository extends JpaRepository<SongList,Integer> {
    SongList findByListNameAndUserName(String listName, String userName);
    Integer deleteByUserNameAndListName(String userName,String listName);
    List<SongList> findByUserName(String userName);
    List<SongList> findAllByOrderByCreateDateDesc(Pageable pageable);
}
