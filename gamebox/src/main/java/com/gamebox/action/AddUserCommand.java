package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.UserDAO;
import com.gamebox.dto.UserDTO;

public class AddUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 회원 추가 요청 처리
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        UserDTO user = new UserDTO();
        user.setName(name);
        user.setEmail(email);        
        user.setPassword(password); 
        user.setRole(role);

        UserDAO dao = new UserDAO();
        dao.addUser(user);

        // 성공 메시지 설정
        request.setAttribute("adminAlert", "회원 추가 성공!");

        // 관리 페이지로 리다이렉트
        return "/manage_users.do?action=view";
    }
}
