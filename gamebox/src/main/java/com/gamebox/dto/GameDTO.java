package com.gamebox.dto;

import java.sql.Timestamp;
import java.util.Date;

public class GameDTO {
    private String gameId;           // 고유 ID를 문자열로 저장
    private String title;            // 게임 제목
    private String imagePath;        // 이미지 경로
    private String description;      // 게임 설명
    private String genre;            // 장르
    private double rating;           // 평점
    private Date releaseDate;        // 출시일
    private String developer;        // 개발사
    private double price;            // 가격
    private String videoUrl;         // 동영상 URL
    private String reviewSummary;    // 리뷰 요약
    private String minRequirements;  // 최소 시스템 요구 사항
    private String recRequirements;  // 권장 시스템 요구 사항
    private Timestamp createdAt;     // 등록 시간
    private Timestamp updatedAt;     // 업데이트 시간

    // 기본 생성자
    public GameDTO() {}

    // 모든 필드를 포함한 생성자
    public GameDTO(String gameId, String title, String imagePath, String description, String genre, 
                   double rating, Date releaseDate, String developer, double price, 
                   String videoUrl, String reviewSummary, String minRequirements, 
                   String recRequirements, Timestamp createdAt, Timestamp updatedAt) {
        this.gameId = gameId;
        this.title = title;
        this.imagePath = imagePath;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.price = price;
        this.videoUrl = videoUrl;
        this.reviewSummary = reviewSummary;
        this.minRequirements = minRequirements;
        this.recRequirements = recRequirements;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getReviewSummary() {
        return reviewSummary;
    }

    public void setReviewSummary(String reviewSummary) {
        this.reviewSummary = reviewSummary;
    }

    public String getMinRequirements() {
        return minRequirements;
    }

    public void setMinRequirements(String minRequirements) {
        this.minRequirements = minRequirements;
    }

    public String getRecRequirements() {
        return recRequirements;
    }

    public void setRecRequirements(String recRequirements) {
        this.recRequirements = recRequirements;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "GameDTO{" +
                "gameId='" + gameId + '\'' +
                ", title='" + title + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", releaseDate=" + releaseDate +
                ", developer='" + developer + '\'' +
                ", price=" + price +
                ", videoUrl='" + videoUrl + '\'' +
                ", reviewSummary='" + reviewSummary + '\'' +
                ", minRequirements='" + minRequirements + '\'' +
                ", recRequirements='" + recRequirements + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
