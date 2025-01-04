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

        if (userId == null) {
            request.setAttribute("error", "로그인이 필요합니다.");
            return "/WEB-INF/views/login.jsp";
        }

        List<CartItemDTO> cartItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT c.ID AS cartId, g.GAME_ID AS gameId, g.TITLE AS gameTitle, g.PRICE AS gamePrice, " +
                           "c.ADDED_DATE AS addedDate, g.IMAGE_PATH AS imagePath " +
                           "FROM CART c " +
                           "JOIN GAMES g ON c.GAME_ID = g.GAME_ID " +
                           "WHERE c.USER_ID = ?";
            System.out.println("Executing query: " + query);

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        CartItemDTO item = new CartItemDTO();
                        item.setCartId(rs.getInt("cartId"));
                        item.setGameId(rs.getInt("gameId"));
                        item.setGameTitle(rs.getString("gameTitle"));
                        item.setGamePrice(rs.getDouble("gamePrice"));
                        item.setAddedDate(rs.getDate("addedDate"));
                        item.setImagePath(rs.getString("imagePath")); // 이미지 경로 추가
                        cartItems.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("cartItems", cartItems);
        return "/WEB-INF/views/common/cart.jsp";
    }
}
