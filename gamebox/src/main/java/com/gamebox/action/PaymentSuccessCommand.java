package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.gamebox.util.DBConnection;

public class PaymentSuccessCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 사용자 세션에서 userId 가져오기
            Integer userId = (Integer) request.getSession().getAttribute("loggedInUserId");
            if (userId == null) {
                throw new IllegalArgumentException("로그인 상태가 아닙니다.");
            }

            System.out.println("PaymentSuccessCommand - User ID: " + userId);

            // DB에서 해당 사용자의 가장 최근 결제 정보 가져오기
            try (Connection conn = DBConnection.getConnection()) {
                String query = """
                    SELECT payment_id, user_id, amount, status, created_at
                    FROM (
                        SELECT payment_id, user_id, amount, status, created_at
                        FROM Payments
                        WHERE user_id = ?
                        ORDER BY created_at DESC
                    )
                    WHERE ROWNUM = 1
                """;
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, userId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            request.setAttribute("paymentId", rs.getInt("payment_id"));
                            request.setAttribute("userId", rs.getInt("user_id"));
                            request.setAttribute("amount", rs.getDouble("amount"));
                            request.setAttribute("status", rs.getString("status"));
                            request.setAttribute("createdAt", rs.getTimestamp("created_at"));
                        } else {
                            throw new RuntimeException("결제 정보를 찾을 수 없습니다.");
                        }
                    }
                }
            }

            // 결제 성공 화면으로 이동
            return "/WEB-INF/views/payment/paymentSuccess.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "결제 정보를 로드하는 중 오류가 발생했습니다.");
            return "/WEB-INF/views/payment/paymentError.jsp";
        }
    }
}
