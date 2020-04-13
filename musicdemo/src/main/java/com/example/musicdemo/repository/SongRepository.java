package com.example.musicdemo.repository;

import com.example.musicdemo.entity.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Integer> {
    List<Song> findByFileNameLikeOrderByCreateDateDesc(Pageable pageable, String searchName);
    Song findByFileName(String fileName);
    Integer deleteByFileName(String fileName);
    List<Song> findAllByOrderByCreateDateDesc(Pageable pageable);
}
