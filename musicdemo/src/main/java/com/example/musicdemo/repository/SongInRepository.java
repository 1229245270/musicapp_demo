package com.example.musicdemo.repository;

import com.example.musicdemo.entity.SongIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongInRepository extends JpaRepository<SongIn,Integer> {
    Integer deleteByFileNameAndListNameAndUserName(String fileName,String listName,String userName);
    SongIn findByFileNameAndListNameAndUserName(String fileName,String listName,String userName);
    List<SongIn> findByListNameAndUserName(String listName,String userName);
    List<SongIn> findByListName(String listName);
    Integer deleteById(int id);
}
