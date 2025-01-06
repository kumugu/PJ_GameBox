package com.gamebox.dto;

import java.util.Date;

public class CommunityDTO {
    private int postId;        // 게시글 ID
    private int userId;        // 작성자 ID
    private String userName;   // 작성자 이름
    private String title;      // 제목
    private String content;    // 내용
    private int viewCount;     // 조회수
    private Date createdAt;    // 작성일
    private Date updatedAt;    // 수정일

    // 기본 생성자
    public CommunityDTO() {}

    // 모든 필드를 포함하는 생성자
    public CommunityDTO(int postId, int userId, String userName, String title, String content, int viewCount, Date createdAt, Date updatedAt) {
        this.postId = postId;
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "CommunityDTO{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", viewCount=" + viewCount +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
