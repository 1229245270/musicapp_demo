package com.example.musicdemo.repository;

import com.example.musicdemo.entity.Comment;
import com.example.musicdemo.entity.SongIn;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByFileNameOrderByCreateDateDesc(String fileName,Pageable pageable);
    Comment findByFileNameAndUserNameAndCommentTextAndToUserId(String fileName,String userName,String commentText,Integer toUserId);
    List<Comment> findAllByOrderByGetLike(Pageable pageable);
}
