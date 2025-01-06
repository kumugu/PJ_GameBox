package com.gamebox.dao;

import com.gamebox.dto.SupportDTO;
import com.gamebox.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupportDAO {

    // 사용자 ID 기준 문의 목록 조회
    public List<SupportDTO> getSupportByUserId(int userId) {
        String sql = "SELECT s.SUPPORT_ID, s.USER_ID, u.NAME AS USER_NAME, s.TITLE, s.CONTENT, s.STATUS, s.REPLY, s.CREATED_AT, s.UPDATED_AT " +
                     "FROM Support s " +
                     "INNER JOIN Users u ON s.USER_ID = u.USER_ID " +
                     "WHERE s.USER_ID = ? ORDER BY s.CREATED_AT DESC";
        List<SupportDTO> supportList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SupportDTO support = new SupportDTO();
                    support.setSupportId(rs.getInt("SUPPORT_ID"));
                    support.setUserId(rs.getInt("USER_ID"));
                    support.setUserName(rs.getString("USER_NAME"));
                    support.setTitle(rs.getString("TITLE"));
                    support.setContent(rs.getString("CONTENT"));
                    support.setStatus(rs.getString("STATUS"));
                    support.setReply(rs.getString("REPLY"));
                    support.setCreatedAt(rs.getDate("CREATED_AT"));
                    support.setUpdatedAt(rs.getDate("UPDATED_AT"));
                    supportList.add(support);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return supportList;
    }

    // 모든 문의 조회 (관리자용)
    public List<SupportDTO> getAllSupports() {
        String sql = "SELECT s.SUPPORT_ID, s.USER_ID, u.NAME AS USER_NAME, s.TITLE, s.CONTENT, s.STATUS, s.REPLY, s.CREATED_AT, s.UPDATED_AT " +
                     "FROM Support s " +
                     "INNER JOIN Users u ON s.USER_ID = u.USER_ID " +
                     "ORDER BY s.CREATED_AT DESC";
        List<SupportDTO> supportList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                SupportDTO support = new SupportDTO();
                support.setSupportId(rs.getInt("SUPPORT_ID"));
                support.setUserId(rs.getInt("USER_ID"));
                support.setUserName(rs.getString("USER_NAME"));
                support.setTitle(rs.getString("TITLE"));
                support.setContent(rs.getString("CONTENT"));
                support.setStatus(rs.getString("STATUS"));
                support.setReply(rs.getString("REPLY"));
                support.setCreatedAt(rs.getDate("CREATED_AT"));
                support.setUpdatedAt(rs.getDate("UPDATED_AT"));
                supportList.add(support);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return supportList;
    }

    // 문의 작성
    public boolean addSupport(SupportDTO support) {
        String sql = "INSERT INTO Support (support_id, user_id, title, content, status, created_at) " +
                     "VALUES (Support_SEQ.NEXTVAL, ?, ?, ?, 'PENDING', SYSDATE)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, support.getUserId());
            pstmt.setString(2, support.getTitle());
            pstmt.setString(3, support.getContent());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 문의 상세 조회
    public SupportDTO getSupportById(int supportId) {
        String sql = "SELECT s.SUPPORT_ID, s.USER_ID, u.NAME AS USER_NAME, s.TITLE, s.CONTENT, s.STATUS, s.REPLY, s.CREATED_AT, s.UPDATED_AT " +
                     "FROM Support s " +
                     "INNER JOIN Users u ON s.USER_ID = u.USER_ID " +
                     "WHERE s.SUPPORT_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, supportId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SupportDTO support = new SupportDTO();
                    support.setSupportId(rs.getInt("SUPPORT_ID"));
                    support.setUserId(rs.getInt("USER_ID"));
                    support.setUserName(rs.getString("USER_NAME"));
                    support.setTitle(rs.getString("TITLE"));
                    support.setContent(rs.getString("CONTENT"));
                    support.setStatus(rs.getString("STATUS"));
                    support.setReply(rs.getString("REPLY"));
                    support.setCreatedAt(rs.getDate("CREATED_AT"));
                    support.setUpdatedAt(rs.getDate("UPDATED_AT"));
                    return support;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 문의 상태 변경
    public boolean updateSupportStatus(int supportId, String status) {
        String sql = "UPDATE Support SET status = ?, updated_at = SYSDATE WHERE support_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, supportId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 문의 답변 작성
    public boolean addReplyToSupport(int supportId, String reply) {
        String sql = "UPDATE Support SET reply = ?, status = 'ANSWERED', updated_at = SYSDATE WHERE support_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reply);
            pstmt.setInt(2, supportId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 문의 삭제
    public boolean deleteSupport(int supportId) {
        String sql = "DELETE FROM Support WHERE support_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, supportId);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
