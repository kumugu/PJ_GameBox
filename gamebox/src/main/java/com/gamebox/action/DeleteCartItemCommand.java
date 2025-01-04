package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.gamebox.util.DBConnection;

public class DeleteCartItemCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cartId = request.getParameter("cartId");

        if (cartId == null || cartId.isEmpty()) {
            response.sendRedirect("viewCart.do?error=Invalid cart item.");
            return null;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM CART WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(cartId));
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("viewCart.do"); // 삭제 후 장바구니로 리다이렉트
        return null;
    }
}
