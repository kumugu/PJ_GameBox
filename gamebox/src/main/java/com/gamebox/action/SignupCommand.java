package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.UserDAO;
import com.gamebox.dto.UserDTO;

public class SignupCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        int userId = Integer.parseInt(request.getParameter("userId")); 
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        UserDTO user = new UserDTO();
//        user.setUserId(userId);
        user.setPassword(password); 
        user.setName(name);
        user.setEmail(email);
        user.setRole("USER");

        UserDAO dao = new UserDAO();
        if (dao.registerUser(user)) {
            request.setAttribute("message", "Signup successful!");
            return "/WEB-INF/views/login.jsp";
        } else {
            request.setAttribute("error", "Signup failed. Try again.");
            return "/WEB-INF/views/signup.jsp";
        }
    }
}
