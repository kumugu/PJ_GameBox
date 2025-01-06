package com.gamebox.dao;

import com.gamebox.dto.CommunityDTO;
import com.gamebox.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommunityDAO {

    // 게시글 전체 조회
    public List<CommunityDTO> getAllPosts() {
        String sql = "SELECT c.POST_ID, c.USER_ID, u.NAME AS USER_NAME, c.TITLE, c.CONTENT, " +
                     "c.VIEW_COUNT, c.CREATED_AT, c.UPDATED_AT " +
                     "FROM community c " +
                     "INNER JOIN users u ON c.USER_ID = u.USER_ID " +
                     "ORDER BY c.CREATED_AT DESC";
        List<CommunityDTO> posts = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                CommunityDTO post = new CommunityDTO();
                post.setPostId(rs.getInt("POST_ID"));
                post.setUserId(rs.getInt("USER_ID"));
                post.setUserName(rs.getString("USER_NAME"));
                post.setTitle(rs.getString("TITLE"));
                post.setContent(rs.getString("CONTENT"));
                post.setViewCount(rs.getInt("VIEW_COUNT"));
                post.setCreatedAt(rs.getDate("CREATED_AT"));
                post.setUpdatedAt(rs.getDate("UPDATED_AT"));
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    // 특정 게시글 조회
    public CommunityDTO getPostById(int postId) {
        String sql = "SELECT c.POST_ID, c.USER_ID, u.NAME AS USER_NAME, c.TITLE, c.CONTENT, " +
                     "c.VIEW_COUNT, c.CREATED_AT, c.UPDATED_AT " +
                     "FROM community c " +
                     "INNER JOIN users u ON c.USER_ID = u.USER_ID " +
                     "WHERE c.POST_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CommunityDTO post = new CommunityDTO();
                    post.setPostId(rs.getInt("POST_ID"));
                    post.setUserId(rs.getInt("USER_ID"));
                    post.setUserName(rs.getString("USER_NAME"));
                    post.setTitle(rs.getString("TITLE"));
                    post.setContent(rs.getString("CONTENT"));
                    post.setViewCount(rs.getInt("VIEW_COUNT"));
                    post.setCreatedAt(rs.getDate("CREATED_AT"));
                    post.setUpdatedAt(rs.getDate("UPDATED_AT"));
                    return post;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 게시글 추가
    public boolean addPost(CommunityDTO post) {
        String sql = "INSERT INTO community (post_id, user_id, title, content, view_count, created_at) " +
                     "VALUES (Community_SEQ.NEXTVAL, ?, ?, ?, 0, SYSDATE)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("CommunityDAO - Adding post with Title: " + post.getTitle());
            System.out.println("CommunityDAO - User ID: " + post.getUserId());

            pstmt.setInt(1, post.getUserId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());

            int result = pstmt.executeUpdate();
            System.out.println("CommunityDAO - Insert result: " + result);
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // 게시글 수정
    public boolean updatePost(CommunityDTO post) {
        String sql = "UPDATE community SET title = ?, content = ?, updated_at = SYSDATE WHERE post_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setInt(3, post.getPostId());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 게시글 삭제
    public boolean deletePost(int postId) {
        String sql = "DELETE FROM community WHERE post_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 게시글 조회수 증가
    public boolean incrementViewCount(int postId) {
        String sql = "UPDATE community SET view_count = view_count + 1 WHERE post_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    // 특정 사용자가 작성한 게시글 가져오기
    public List<CommunityDTO> getPostsByUserId(int userId) throws Exception {
        String sql = "SELECT post_id, user_id, title, content, view_count, created_at " +
                     "FROM Community WHERE user_id = ? ORDER BY created_at DESC";
        List<CommunityDTO> posts = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CommunityDTO post = new CommunityDTO();
                    post.setPostId(rs.getInt("post_id"));
                    post.setUserId(rs.getInt("user_id"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setViewCount(rs.getInt("view_count"));
                    post.setCreatedAt(rs.getTimestamp("created_at"));
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.err.println("CommunityDAO - getPostsByUserId 오류: " + e.getMessage());
            e.printStackTrace();
        }

        return posts;
    }
}
