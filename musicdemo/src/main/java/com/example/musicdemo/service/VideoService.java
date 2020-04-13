package com.example.musicdemo.service;

import com.example.musicdemo.entity.Video;
import com.example.musicdemo.entity.Zhibo;
import com.example.musicdemo.repository.VideoRepository;
import com.example.musicdemo.repository.ZhiboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ZhiboRepository zhiboRepository;

    public List<Video> getAllVideo(int page,int size){
        PageRequest pr = PageRequest.of(page,size);
        return videoRepository.findAllByOrderByCreateDate(pr);
    }
    public List<Zhibo> getAllZhibo(int page, int size){
        PageRequest pr = PageRequest.of(page,size);
        return zhiboRepository.findAllByOrderByCreateDate(pr);
    }
}
