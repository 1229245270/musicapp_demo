package com.example.musicdemo.service;

import com.example.musicdemo.entity.*;
import com.example.musicdemo.enums.ResultEnum;
import com.example.musicdemo.exception.SongException;
import com.example.musicdemo.repository.*;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GetLikeService {

    @Autowired
    private GetLikeRepository getLikeRepository;
    @Autowired
    private CommentRepository commentRepository;
    /**
     * 点赞 - 取消点赞评论
     */
    @Transactional
    public String insertGetLike(GetLike getLike) throws SongException{
        GetLike baseGetLike = getLikeRepository.findByUserNameAndGetLikeUserNameAndFileNameAndCommentTextAndToUserId(getLike.getUserName(),getLike.getGetLikeUserName(),getLike.getFileName(),getLike.getFileName(),getLike.getToUserId());
        if(baseGetLike == null){
            getLikeRepository.save(getLike);
            Comment comment = commentRepository.findByFileNameAndUserNameAndCommentTextAndToUserId(getLike.getGetLikeUserName(),getLike.getFileName(),getLike.getCommentText(),getLike.getToUserId());
            comment.setGetLike(comment.getGetLike() + 1);
            commentRepository.save(comment);
            return "点赞";
        }else{
            getLikeRepository.deleteByUserNameAndGetLikeUserNameAndFileNameAndCommentTextAndToUserId(getLike.getUserName(),getLike.getGetLikeUserName(),getLike.getFileName(),getLike.getCommentText(),getLike.getToUserId());
            Comment comment = commentRepository.findByFileNameAndUserNameAndCommentTextAndToUserId(getLike.getGetLikeUserName(),getLike.getFileName(),getLike.getCommentText(),getLike.getToUserId());
            comment.setGetLike(comment.getGetLike() - 1);
            commentRepository.save(comment);
            return "取消点赞";
        }
    }

}
