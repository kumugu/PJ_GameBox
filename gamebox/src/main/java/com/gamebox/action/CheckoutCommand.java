package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.gamebox.dao.PaymentDAO;
import com.gamebox.dto.PaymentDTO;
import com.gamebox.util.DBConnection;

public class CheckoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 세션에서 사용자 ID 가져오기
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("loggedInUserId");
            System.out.println("CheckoutCommand - 세션에서 가져온 userId: " + userId);

            if (userId == null) {
                throw new IllegalStateException("로그인 상태가 아닙니다. 사용자 ID를 찾을 수 없습니다.");
            }

            // 2. DB에서 총 결제 금액 계산
            double totalPrice = 0.0;
            try (Connection conn = DBConnection.getConnection()) {
                String query = """
                    SELECT SUM(c.GAME_PRICE * c.QUANTITY) AS totalPrice
                    FROM CART_ITEMS c
                    WHERE c.USER_ID = ?
                """;
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, userId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            totalPrice = rs.getDouble("totalPrice");
                        }
                    }
                }
            }

            if (totalPrice <= 0) {
                throw new IllegalArgumentException("장바구니가 비어 있거나 결제 금액이 0원입니다.");
            }

            System.out.println("CheckoutCommand - 계산된 총 금액: " + totalPrice);

            // 3. Payment 객체 생성 및 초기화
            PaymentDTO payment = new PaymentDTO();
            payment.setUserId(userId);
            payment.setAmount(totalPrice);
            payment.setStatus("PENDING");

            // 4. DB에 Payment 저장
            PaymentDAO paymentDAO = new PaymentDAO();
            int paymentId = paymentDAO.createPayment(payment);

            // 5. 결제 ID와 총 금액을 View로 전달
            request.setAttribute("paymentId", paymentId);
            request.setAttribute("totalPrice", String.valueOf(totalPrice));

            // 6. 결제 진행 페이지로 이동
            return "/WEB-INF/views/payment/paymentProcessing.jsp";

        } catch (IllegalStateException e) {
            e.printStackTrace();
            request.setAttribute("error", "로그인이 필요합니다. " + e.getMessage());
            return "/WEB-INF/views/login.jsp"; // 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("error", "유효하지 않은 요청 데이터: " + e.getMessage());
            return "/WEB-INF/views/payment/paymentError.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "결제 처리 중 알 수 없는 오류가 발생했습니다.");
            return "/WEB-INF/views/payment/paymentError.jsp";
        }
    }
}
