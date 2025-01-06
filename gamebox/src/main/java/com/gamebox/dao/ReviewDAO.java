package com.gamebox.dao;

import com.gamebox.dto.ReviewDTO;
import com.gamebox.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // 특정 게임의 리뷰 조회
	public List<ReviewDTO> getReviewsByGameId(int gameId) {
	    String sql = "SELECT r.REVIEW_ID, r.USER_ID, u.NAME AS USER_NAME, r.GAME_ID, r.RATING, r.CONTENT, r.CREATED_AT " +
	                 "FROM REVIEWS r " +
	                 "INNER JOIN USERS u ON r.USER_ID = u.USER_ID " +
	                 "WHERE r.GAME_ID = ?";
	    List<ReviewDTO> reviews = new ArrayList<>();

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, gameId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                ReviewDTO review = new ReviewDTO();
	                review.setReviewId(rs.getInt("REVIEW_ID"));
	                review.setUserId(rs.getInt("USER_ID"));
	                review.setUserName(rs.getString("USER_NAME"));
	                review.setGameId(rs.getInt("GAME_ID"));
	                review.setRating(rs.getInt("RATING"));
	                review.setContent(rs.getString("CONTENT"));
	                review.setCreatedAt(rs.getDate("CREATED_AT"));
	                reviews.add(review);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return reviews;
	}

	
	public List<ReviewDTO> getReviewsByUserId(int userId) {
	    String sql = "SELECT r.REVIEW_ID, r.USER_ID, r.GAME_ID, g.TITLE as GAME_TITLE, " +
	                 "r.RATING, r.CONTENT, r.CREATED_AT " +
	                 "FROM REVIEWS r " +
	                 "INNER JOIN GAMES g ON r.GAME_ID = g.GAME_ID " +
	                 "WHERE r.USER_ID = ? " +
	                 "ORDER BY r.CREATED_AT DESC";
	                 
	    List<ReviewDTO> reviews = new ArrayList<>();

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, userId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                ReviewDTO review = new ReviewDTO();
	                review.setReviewId(rs.getInt("REVIEW_ID"));
	                review.setUserId(rs.getInt("USER_ID"));
	                review.setGameId(rs.getInt("GAME_ID"));
	                review.setGameTitle(rs.getString("GAME_TITLE"));
	                review.setRating(rs.getInt("RATING"));
	                review.setContent(rs.getString("CONTENT"));
	                review.setCreatedAt(rs.getDate("CREATED_AT"));
	                reviews.add(review);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return reviews;
	}

	
	// 리뷰 가져오기
    public ReviewDTO getUserReviewForGame(int userId, int gameId) {
        String sql = "SELECT r.REVIEW_ID, r.USER_ID, r.GAME_ID, r.RATING, r.CONTENT, r.CREATED_AT " +
                     "FROM REVIEWS r " +
                     "WHERE r.USER_ID = ? AND r.GAME_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ReviewDTO review = new ReviewDTO();
                    review.setReviewId(rs.getInt("REVIEW_ID"));
                    review.setUserId(rs.getInt("USER_ID"));
                    review.setGameId(rs.getInt("GAME_ID"));
                    review.setRating(rs.getInt("RATING"));
                    review.setContent(rs.getString("CONTENT"));
                    review.setCreatedAt(rs.getDate("CREATED_AT"));
                    return review;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    // 리뷰 추가
	public boolean addReview(ReviewDTO review) {
	    String sql = "INSERT INTO REVIEWS (REVIEW_ID, USER_ID, GAME_ID, RATING, CONTENT, CREATED_AT) " +
	                 "VALUES (REVIEW_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, review.getUserId());
	        pstmt.setInt(2, review.getGameId());
	        pstmt.setInt(3, review.getRating());
	        pstmt.setString(4, review.getContent());

	        int result = pstmt.executeUpdate();
	        return result > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}


    // 리뷰 수정
    public void updateReview(ReviewDTO review) {
        String sql = "UPDATE REVIEWS SET CONTENT = ?, RATING = ?, UPDATED_AT = SYSDATE WHERE REVIEW_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, review.getContent());
            pstmt.setInt(2, review.getRating());
            pstmt.setInt(3, review.getReviewId());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 리뷰 삭제
    public void deleteReview(int reviewId) {
        String sql = "DELETE FROM REVIEWS WHERE REVIEW_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 중복확인
    public boolean hasUserReviewed(Integer userId, int gameId) {
        String sql = "SELECT COUNT(*) FROM REVIEWS WHERE USER_ID = ? AND GAME_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // 리뷰가 존재하면 true 반환
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // 예외 발생 시 false 반환
    }

    public List<ReviewDTO> getAllReviews() {
        String sql = "SELECT r.REVIEW_ID, r.USER_ID, u.NAME AS USER_NAME, r.GAME_ID, g.TITLE AS GAME_TITLE, " +
                     "r.RATING, r.CONTENT, r.CREATED_AT " +
                     "FROM REVIEWS r " +
                     "INNER JOIN USERS u ON r.USER_ID = u.USER_ID " +
                     "INNER JOIN GAMES g ON r.GAME_ID = g.GAME_ID";
        List<ReviewDTO> reviews = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ReviewDTO review = new ReviewDTO();
                review.setReviewId(rs.getInt("REVIEW_ID"));
                review.setUserId(rs.getInt("USER_ID"));
                review.setUserName(rs.getString("USER_NAME"));
                review.setGameId(rs.getInt("GAME_ID"));
                review.setGameTitle(rs.getString("GAME_TITLE"));
                review.setRating(rs.getInt("RATING"));
                review.setContent(rs.getString("CONTENT"));
                review.setCreatedAt(rs.getDate("CREATED_AT"));
                reviews.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }



}
