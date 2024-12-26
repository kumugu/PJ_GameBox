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
    	
    	// admin menu 
	    commandMap.put("/admin_home.do", new com.gamebox.action.AdminHomeCommand());
	    commandMap.put("/manage_users.do", new com.gamebox.action.ManageUsersCommand());
	    commandMap.put("/edit_user.do", new com.gamebox.action.EditUserCommand());
	    commandMap.put("/add_user_form.do", new com.gamebox.action.AddUserFormCommand());
	    commandMap.put("/add_user.do", new com.gamebox.action.AddUserCommand());


	    
    	
    	commandMap.put("/signup.do", new com.gamebox.action.SignupCommand());
        commandMap.put("/login.do", new com.gamebox.action.LoginCommand());
        commandMap.put("/logout.do", new com.gamebox.action.LogoutCommand());
       
        commandMap.put("/user_home.do", new com.gamebox.action.UserHomeCommand());
        
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
            RequestDispatcher dispatcher = request.getRequestDispatcher(view);
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
