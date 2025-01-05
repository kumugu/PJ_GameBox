package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.gamebox.util.DBConnection;

public class AddToCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");

        // === 세션 디버깅 정보 ===
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) {
            response.getWriter().write("{\"success\": false, \"message\": \"로그인 후 이용 가능합니다.\"}");
            return null;
        }
        System.out.println("User ID from session: " + userId);

        // === 요청 파라미터 디버깅 ===
        String gameId = request.getParameter("gameId");
        if (gameId == null || gameId.isEmpty()) {
            response.getWriter().write("{\"success\": false, \"message\": \"유효하지 않은 게임 ID입니다.\"}");
            return null;
        }
        System.out.println("Received gameId: " + gameId);

        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Database connection established");

            // === 게임 가격 가져오기 ===
            double gamePrice = 0.0;
            String priceQuery = "SELECT PRICE FROM GAMES WHERE GAME_ID = ?";
            try (PreparedStatement priceStmt = conn.prepareStatement(priceQuery)) {
                priceStmt.setInt(1, Integer.parseInt(gameId));
                try (ResultSet rs = priceStmt.executeQuery()) {
                    if (rs.next()) {
                        gamePrice = rs.getDouble("PRICE");
                        System.out.println("Game price retrieved: " + gamePrice);
                    } else {
                        response.getWriter().write("{\"success\": false, \"message\": \"존재하지 않는 게임입니다.\"}");
                        return null;
                    }
                }
            }

            // === 중복 확인 ===
            String checkQuery = "SELECT QUANTITY FROM CART_ITEMS WHERE USER_ID = ? AND GAME_ID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, Integer.parseInt(gameId));
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        // 이미 장바구니에 있으면 수량 증가
                        String updateQuery = "UPDATE CART_ITEMS SET QUANTITY = QUANTITY + 1 WHERE USER_ID = ? AND GAME_ID = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, userId);
                            updateStmt.setInt(2, Integer.parseInt(gameId));
                            updateStmt.executeUpdate();
                            System.out.println("Item quantity updated in cart");
                            response.getWriter().write("{\"success\": true, \"message\": \"장바구니 수량이 증가했습니다.\"}");
                            return null;
                        }
                    }
                }
            }

            // === 데이터 추가 ===
            String insertQuery = "INSERT INTO CART_ITEMS (ID, USER_ID, GAME_ID, GAME_PRICE, QUANTITY, ADDED_DATE) " +
                                 "VALUES (CART_ITEMS_SEQ.NEXTVAL, ?, ?, ?, 1, SYSDATE)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, Integer.parseInt(gameId));
                insertStmt.setDouble(3, gamePrice);
                insertStmt.executeUpdate();
                System.out.println("Item inserted into cart");
            }

            // === 성공 응답 ===
            response.getWriter().write("{\"success\": true, \"message\": \"장바구니에 추가되었습니다.\"}");

        } catch (Exception e) {
            System.out.println("Error occurred during database operation:");
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"서버 오류가 발생했습니다.\"}");
        }

        return null;
    }
}
