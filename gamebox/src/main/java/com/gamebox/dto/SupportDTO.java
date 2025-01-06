package com.gamebox.dto;

import java.util.Date;

public class SupportDTO {
    private int supportId;   // 문의 ID
    private int userId;      // 작성자 ID
    private String userName; // 작성자 이름
    private String title;    // 문의 제목
    private String content;  // 문의 내용
    private String status;   // 상태
    private String reply;    // 답변 내용
    private Date createdAt;  // 작성일
    private Date updatedAt;  // 수정일

    // 기본 생성자
    public SupportDTO() {}

    // 모든 필드를 포함하는 생성자
    public SupportDTO(int supportId, int userId, String userName, String title, String content, String status, String reply, Date createdAt, Date updatedAt) {
        this.supportId = supportId;
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.status = status;
        this.reply = reply;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getSupportId() {
        return supportId;
    }

    public void setSupportId(int supportId) {
        this.supportId = supportId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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
}
