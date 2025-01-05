package com.gamebox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.gamebox.util.DBConnection;

public class CartDAO {
    // 사용자의 장바구니 데이터 삭제
    public boolean clearCartByUserId(int userId) {
        String sql = "DELETE FROM CART_ITEMS WHERE USER_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0; // 삭제된 행 수가 0보다 크면 true 반환
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // 실패 시 false 반환
    }
}
