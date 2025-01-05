package com.gamebox.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import com.gamebox.action.Command;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HashMap<String, Command> commandMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
    	// Command 등록

    	// 관리자 계정 메뉴 
    	commandMap.put("/admin_home.do", new com.gamebox.action.AdminHomeCommand());			// 관리자 메뉴 진입
    	commandMap.put("/manage_users.do", new com.gamebox.action.ManageUsersCommand()); 		// 관리자 메뉴 - 회원관리(CRUD) 
    	commandMap.put("/add_user_form.do", new com.gamebox.action.AddUserFormCommand());		// 관리자 메뉴 - 회원관리 - 회원추가 진입
    	commandMap.put("/add_user.do", new com.gamebox.action.AddUserCommand()); 				// 관리자 메뉴 - 회원관리 - 회원추가 처리
    	commandMap.put("/edit_user.do", new com.gamebox.action.EditUserCommand()); 				// 관리자 메뉴 - 회원관리 - 회원수정 진입
    	commandMap.put("/manage_games.do", new com.gamebox.action.ManageGamesCommand());  		// 관리자 메뉴 - 게임관리(CRUD)	
    	commandMap.put("/manage_reviews.do", new com.gamebox.action.ManageReviewsCommand()); 	// 관리자 메뉴 - 리뷰관리	
	    
    	// 일반 메뉴
        commandMap.put("/user_home.do", new com.gamebox.action.UserHomeCommand());				// 메인 페이지
        commandMap.put("/shop.do", new com.gamebox.action.ShopCommand());						// 상점 페이지
        commandMap.put("/gameDetail.do", new com.gamebox.action.GameDetailCommand()); 			// 상점 상세 페이지
        commandMap.put("/review.do", new com.gamebox.action.ReviewCommand());					// 상점 상세 페이지 - 리뷰
         
        commandMap.put("/addToCart.do", new com.gamebox.action.AddToCartCommand());				// 장바구니 - 추가
        commandMap.put("/viewCart.do", new com.gamebox.action.ViewCartCommand());				// 장바구니 - 페이지
        commandMap.put("/deleteCartItem.do", new com.gamebox.action.DeleteCartItemCommand());	// 장바구니 - 삭제

        commandMap.put("/checkout.do", new com.gamebox.action.CheckoutCommand());				// 결제 - 결제 진행
        commandMap.put("/paymentResult.do", new com.gamebox.action.PaymentResultCommand());		// 결제 - 결제 결과
        commandMap.put("/verifyPayment.do", new com.gamebox.action.VerifyPaymentCommand()); 	// 결제 - 결제 검증
        commandMap.put("/paymentSuccess.do", new com.gamebox.action.PaymentSuccessCommand());	// 결제 - 결제 완료


        
     	// 회원가입 및 로그인
	    commandMap.put("/signup_form.do", new com.gamebox.action.SignupFormCommand());			// 회원가입 진입
    	commandMap.put("/signup.do", new com.gamebox.action.SignupCommand());					// 회원가입 처리
        commandMap.put("/login.do", new com.gamebox.action.LoginCommand());						// 로그인
        commandMap.put("/logout.do", new com.gamebox.action.LogoutCommand());					// 로그아웃

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getServletPath();
        Command command = commandMap.get(path);

        if (command != null) {
            String view = command.execute(request, response);
            // JSON 응답의 경우 view가 null이어도 정상적인 케이스
            if (view != null && !view.isEmpty()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(view);
                dispatcher.forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Command not found");
        }
    }

}
