package com.example.musicdemo.repository;

import com.example.musicdemo.entity.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoRepository extends JpaRepository<Video,Integer> {
    List<Video> findAllByOrderByCreateDate(Pageable pageable);
}
