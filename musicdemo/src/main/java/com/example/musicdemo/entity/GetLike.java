package com.example.musicdemo.entity;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class GetLike {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "userName不能为空")
    private String userName;
    @NotBlank(message = "fileName不能为空")
    private String fileName;
    private int toUserId;
    @NotBlank(message = "CommentText不能为空")
    @Type(type = "text")
    private String commentText;
    @NotBlank(message = "getLikeUserName不能为空")
    private String getLikeUserName;
    @CreatedDate
    private Date createDate;

    public GetLike() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getGetLikeUserName() {
        return getLikeUserName;
    }

    public void setGetLikeUserName(String getLikeUserName) {
        this.getLikeUserName = getLikeUserName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
