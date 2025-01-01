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
    	commandMap.put("/admin_home.do", new com.gamebox.action.AdminHomeCommand());		// 관리자 메뉴 진입

    	commandMap.put("/manage_users.do", new com.gamebox.action.ManageUsersCommand()); 	// 관리자 메뉴 - 회원관리(CRUD) 
    	commandMap.put("/add_user_form.do", new com.gamebox.action.AddUserFormCommand());	// 관리자 메뉴 - 회원관리 - 회원추가 진입
    	commandMap.put("/add_user.do", new com.gamebox.action.AddUserCommand()); 			// 관리자 메뉴 - 회원관리 - 회원추가 처리
    	commandMap.put("/edit_user.do", new com.gamebox.action.EditUserCommand()); 			// 관리자 메뉴 - 회원관리 - 회원수정 진입

    	commandMap.put("/manage_games.do", new com.gamebox.action.ManageGamesCommand());    // 관리자 메뉴 - 게임관리(CRUD)		

	    
    	// 일반 메뉴
        commandMap.put("/user_home.do", new com.gamebox.action.UserHomeCommand());			// 메인 페이지(index.jsp)
        commandMap.put("/shop.do", new com.gamebox.action.ShopCommand());					// 상점 페이지(shop.jsp)
        commandMap.put("/gameDetail.do", new com.gamebox.action.GameDetailCommand()); 		// 상점 상세 페이지(shopDetail.jsp)

        
     	// 회원가입 및 로그인
	    commandMap.put("/signup_form.do", new com.gamebox.action.SignupFormCommand());		// 회원가입 진입
    	commandMap.put("/signup.do", new com.gamebox.action.SignupCommand());				// 회원가입 처리
        commandMap.put("/login.do", new com.gamebox.action.LoginCommand());					// 로그인
        commandMap.put("/logout.do", new com.gamebox.action.LogoutCommand());				// 로그아웃

        
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

            // view가 null인지 확인
            if (view == null || view.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "View path not found");
                return;
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(view);
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Command not found");
        }
    }

}
