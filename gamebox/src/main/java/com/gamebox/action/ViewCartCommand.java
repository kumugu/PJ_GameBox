package com.gamebox.action;

import com.gamebox.util.DBConnection;
import com.gamebox.dto.CartItemDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ViewCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("loggedInUserId");

        // 로그인 확인
        if (userId == null) {
            request.setAttribute("error", "로그인이 필요합니다.");
            return "/WEB-INF/views/login.jsp";
        }

        List<CartItemDTO> cartItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Database connection established for viewing cart");

            // 수정된 쿼리: GAMES 테이블의 기본 키를 `GAME_ID`로 참조
            String query = """
                SELECT c.ID AS cartId, 
                       c.GAME_ID AS gameId, 
                       g.TITLE AS gameTitle, 
                       c.GAME_PRICE AS gamePrice, 
                       c.QUANTITY AS quantity, 
                       c.ADDED_DATE AS addedDate, 
                       g.IMAGE_PATH AS imagePath
                FROM CART_ITEMS c
                JOIN GAMES g ON c.GAME_ID = g.GAME_ID
                WHERE c.USER_ID = ?
            """;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                System.out.println("Executing query to retrieve cart items for userId: " + userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        CartItemDTO item = new CartItemDTO();
                        item.setCartId(rs.getInt("cartId"));
                        item.setGameId(rs.getInt("gameId"));
                        item.setGameTitle(rs.getString("gameTitle"));
                        item.setGamePrice(rs.getDouble("gamePrice"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setAddedDate(rs.getDate("addedDate"));
                        item.setImagePath(rs.getString("imagePath"));
                        cartItems.add(item);
                        System.out.println("Cart Item Added: " + item);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching cart items:");
            e.printStackTrace();
            request.setAttribute("error", "장바구니 데이터를 불러오는 중 오류가 발생했습니다.");
        }

        // 장바구니 데이터 설정
        request.setAttribute("cartItems", cartItems);
        return "/WEB-INF/views/common/cart.jsp";
    }
}
