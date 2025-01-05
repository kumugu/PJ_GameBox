package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.gamebox.util.DBConnection;

public class DeleteCartItemCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청 파라미터에서 cartId 가져오기
        String cartId = request.getParameter("cartId");

        // cartId 유효성 검증
        if (cartId == null || cartId.isEmpty()) {
            System.out.println("Error: Invalid cart item ID");
            response.sendRedirect("viewCart.do?error=Invalid cart item ID.");
            return null;
        }

        // DB 연결 및 삭제 처리
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Database connection established for deleting cart item");

            String query = "DELETE FROM CART_ITEMS WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(cartId));
                System.out.println("Executing query: " + query);
                System.out.println("Parameter - cartId: " + cartId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cart item deleted successfully");
                } else {
                    System.out.println("No cart item found with ID: " + cartId);
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while deleting cart item:");
            e.printStackTrace();
            response.sendRedirect("viewCart.do?error=Unable to delete cart item.");
            return null;
        }

        // 삭제 후 장바구니로 리다이렉트
        response.sendRedirect("viewCart.do?success=Cart item deleted successfully.");
        return null;
    }
}
