package com.example.musicdemo.service;

import com.example.musicdemo.entity.*;
import com.example.musicdemo.enums.ResultEnum;
import com.example.musicdemo.exception.SongException;
import com.example.musicdemo.repository.*;
import com.example.musicdemo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SongRepository songRepository;
    /**
     * 查找歌曲评论列表
     */
    public List<Comment> findByFileName(String fileName,int page,int size) throws SongException {
        PageRequest pr = PageRequest.of(page,size);
        return commentRepository.findByFileNameOrderByCreateDateDesc(fileName,pr);
    }
    /**
     * 查找歌曲评论列表
     */
    public List<Comment> findByAll(int page,int size) throws SongException {
        PageRequest pr = PageRequest.of(page,size);
        return commentRepository.findAllByOrderByGetLike(pr);
    }
    /**
     * 查找评论详情
     */
    public Comment findByComment(Comment comment) throws SongException {
        return commentRepository.findByFileNameAndUserNameAndCommentTextAndToUserId(comment.getFileName(),comment.getUserName(),comment.getCommentText(),comment.getToUserId());
    }
    /**
     * 写评论
     */
    public Result<Comment> insertComment(Comment comment) throws SongException{
        Comment baseComment = commentRepository.findByFileNameAndUserNameAndCommentTextAndToUserId(comment.getFileName(),comment.getUserName(),comment.getCommentText(),comment.getToUserId());
        //评论已存在
        if(baseComment != null){
            return ResultUtil.error(ResultEnum.COMMENT_ERROR.getCode(),ResultEnum.COMMENT_ERROR.getMsg());
        }
        String userName = comment.getUserName();
        String fileName = comment.getFileName();
        int toUserId = comment.getToUserId();
        comment.setFileName(fileName);
        comment.setUserName(userName);
        comment.setCommentText(comment.getCommentText());
        comment.setToUserId(toUserId);
        //如果评论了别人，别人的评论数加一
        if(toUserId != 0){
            Comment toComment = commentRepository.getOne(comment.getId());
            toComment.setGetComment(toComment.getGetComment() + 1);
            commentRepository.save(toComment);
        }
        comment.setGetComment(0);
        comment.setGetLike(0);
        User user = userRepository.findByUserName(userName);
        comment.setUserHeader(user.getImg());
        Song song = songRepository.findByFileName(fileName);
        song.setCommentNum(song.getCommentNum() + 1);
        songRepository.save(song);
        comment.setSongImage(song.getImg());
        return ResultUtil.success(commentRepository.save(comment));
    }

}
