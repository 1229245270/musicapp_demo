package com.example.musicdemo.repository;

import com.example.musicdemo.entity.Video;
import com.example.musicdemo.entity.Zhibo;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface ZhiboRepository extends JpaRepository<Zhibo,Integer> {
    List<Zhibo> findAllByOrderByCreateDate(Pageable pageable);
}
