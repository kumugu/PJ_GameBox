package com.gamebox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gamebox.dto.PaymentDTO;
import com.gamebox.util.DBConnection;

public class PaymentDAO {

    // 결제 정보 저장
    public int createPayment(PaymentDTO payment) {
        String sql = "INSERT INTO Payments (payment_id, user_id, amount, status) " +
                     "VALUES (PAYMENT_SEQ.NEXTVAL, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"payment_id"})) {

            pstmt.setInt(1, payment.getUserId());
            pstmt.setDouble(2, payment.getAmount());
            pstmt.setString(3, payment.getStatus());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // 생성된 payment_id 반환
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 실패 시 -1 반환
    }

    // 결제 상태 업데이트
    public boolean updatePaymentStatus(int paymentId, String status) {
        String sql = "UPDATE Payments SET status = ? WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, paymentId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 결제 정보 조회
    public PaymentDTO getPaymentById(int paymentId) {
        String sql = "SELECT payment_id, user_id, amount, status, created_at FROM Payments WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PaymentDTO payment = new PaymentDTO();
                    payment.setPaymentId(rs.getInt("payment_id"));
                    payment.setUserId(rs.getInt("user_id"));
                    payment.setAmount(rs.getDouble("amount"));
                    payment.setStatus(rs.getString("status"));
                    payment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return payment;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 결제 정보 없음
    }
    
    
    public List<PaymentDTO> getPaymentsByUserId(Integer userId) {
        String sql = "SELECT payment_id, user_id, amount, status, created_at FROM Payments WHERE user_id = ?";
        List<PaymentDTO> payments = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PaymentDTO payment = new PaymentDTO();
                    payment.setPaymentId(rs.getInt("payment_id"));
                    payment.setUserId(rs.getInt("user_id"));
                    payment.setAmount(rs.getDouble("amount"));
                    payment.setStatus(rs.getString("status"));
                    payment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    payments.add(payment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payments;
    }
}
