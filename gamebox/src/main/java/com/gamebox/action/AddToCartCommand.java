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
       System.out.println("\n=== Session Debug Info ===");
       java.util.Enumeration<String> attributeNames = session.getAttributeNames();
       while (attributeNames.hasMoreElements()) {
           String attributeName = attributeNames.nextElement();
           System.out.println(attributeName + ": " + session.getAttribute(attributeName));
       }
       System.out.println("Session ID: " + session.getId());
       System.out.println("======================\n");

       // === 요청 파라미터 디버깅 ===
       System.out.println("=== Request Parameters ===");
       java.util.Enumeration<String> paramNames = request.getParameterNames();
       while (paramNames.hasMoreElements()) {
           String paramName = paramNames.nextElement();
           System.out.println(paramName + ": " + request.getParameter(paramName));
       }
       System.out.println("======================\n");

       Integer userId = (Integer) session.getAttribute("loggedInUserId");
       System.out.println("User ID from session: " + userId);

       if (userId == null) {
           System.out.println("Error: User not logged in");
           response.getWriter().write("{\"success\": false, \"message\": \"로그인 후 이용 가능합니다.\"}");
           return null;
       }

       String gameId = request.getParameter("gameId");
       System.out.println("Received gameId: " + gameId);

       if (gameId == null || gameId.isEmpty()) {
           System.out.println("Error: Invalid game ID");
           response.getWriter().write("{\"success\": false, \"message\": \"유효하지 않은 게임 ID입니다.\"}");
           return null;
       }

       try (Connection conn = DBConnection.getConnection()) {
           System.out.println("Database connection established");
           
           // 중복 확인
           String checkQuery = "SELECT COUNT(*) FROM CART WHERE USER_ID = ? AND GAME_ID = ?";
           try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
               checkStmt.setInt(1, userId);
               checkStmt.setInt(2, Integer.parseInt(gameId));
               System.out.println("Executing query: " + checkQuery);
               System.out.println("Parameters - userId: " + userId + ", gameId: " + gameId);
               
               ResultSet rs = checkStmt.executeQuery();
               if (rs.next() && rs.getInt(1) > 0) {
                   System.out.println("Item already exists in cart");
                   response.getWriter().write("{\"success\": false, \"message\": \"이미 장바구니에 추가된 상품입니다.\"}");
                   return null;
               }
           }

           // 데이터 추가
           String insertQuery = "INSERT INTO CART (ID, USER_ID, GAME_ID, ADDED_DATE) VALUES (CART_SEQ.NEXTVAL, ?, ?, SYSDATE)";
           try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
               insertStmt.setInt(1, userId);
               insertStmt.setInt(2, Integer.parseInt(gameId));
               System.out.println("Executing insert query: " + insertQuery);
               System.out.println("Insert parameters - userId: " + userId + ", gameId: " + gameId);
               
               insertStmt.executeUpdate();
               System.out.println("Successfully inserted item into cart");
           }

           response.getWriter().write("{\"success\": true, \"message\": \"장바구니에 추가되었습니다.\"}");
           
       } catch (Exception e) {
           System.out.println("Error occurred during database operation:");
           e.printStackTrace();
           response.getWriter().write("{\"success\": false, \"message\": \"서버 오류가 발생했습니다.\"}");
       }

       return null;
   }
}