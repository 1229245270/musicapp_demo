package com.example.musicapp.Model.CommentList;

public class data {
    private int id;
    //评论人
    private String userName;
    //评论人头像
    private String userHeader;
    //歌曲名字
    private String fileName;
    //被评论人
    private int toUserId;
    //被评论歌曲图片
    private String songImage;
    //评论内容
    private String commentText;
    //评论时间
    private long createDate;
    //点赞数
    private int getLike;
    //评论数
    private int getComment;

    @Override
    public String toString() {
        return "data{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userHeader='" + userHeader + '\'' +
                ", fileName='" + fileName + '\'' +
                ", toUserId=" + toUserId +
                ", songImage='" + songImage + '\'' +
                ", commentText='" + commentText + '\'' +
                ", createDate=" + createDate +
                ", getLike=" + getLike +
                ", getComment=" + getComment +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
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

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getGetLike() {
        return getLike;
    }

    public void setGetLike(int getLike) {
        this.getLike = getLike;
    }

    public int getGetComment() {
        return getComment;
    }

    public void setGetComment(int getComment) {
        this.getComment = getComment;
    }
}
