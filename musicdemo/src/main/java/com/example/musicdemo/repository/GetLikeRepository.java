package com.example.musicdemo.repository;

import com.example.musicdemo.entity.GetLike;
import com.example.musicdemo.entity.SongIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GetLikeRepository extends JpaRepository<GetLike,Integer> {
    GetLike findByUserNameAndGetLikeUserNameAndFileNameAndCommentTextAndToUserId(String userName,String getLikeUserName,String fileName,String commentText,int toUserName);
    void deleteByUserNameAndGetLikeUserNameAndFileNameAndCommentTextAndToUserId(String userName,String getLikeUserName,String fileName,String commentText,int toUserName);
}
