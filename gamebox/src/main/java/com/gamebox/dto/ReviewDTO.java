package com.gamebox.dto;

import java.util.Date;

public class ReviewDTO {
    private int reviewId;      // 리뷰 ID
    private int userId;        // 사용자 ID
    private String userName;   // 작성자 이름
    private int gameId;        // 게임 ID
    private String gameTitle;  // 게임 제목 (추가)
    private int rating;        // 평점
    private String content;    // 리뷰 내용
    private Date createdAt;    // 작성일

    // 기본 생성자
    public ReviewDTO() {}

    // 모든 필드를 포함하는 생성자
    public ReviewDTO(int reviewId, int userId, String userName, int gameId, String gameTitle, int rating, String content, Date createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userName = userName;
        this.gameId = gameId;
        this.gameTitle = gameTitle; // 추가
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getter 및 Setter
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "reviewId=" + reviewId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", gameId=" + gameId +
                ", gameTitle='" + gameTitle + '\'' + // 추가
                ", rating=" + rating +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
