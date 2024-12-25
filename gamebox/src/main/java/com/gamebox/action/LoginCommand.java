package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.UserDAO;
import com.gamebox.dto.UserDTO;

public class LoginCommand implements Command {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    UserDAO dao = new UserDAO();
	    UserDTO user = dao.loginUser(email, password);

	    if (user != null) {
	        request.getSession().setAttribute("user", user);
	        System.out.println("로그인 성공: " + user.getEmail());
	        System.out.println("사용자 역할: [" + user.getRole() + "]");

	        // ROLE에 따라 Command로 분기
	        if ("ADMIN".equalsIgnoreCase(user.getRole().trim())) {
	            System.out.println("관리자 계정으로 로그인");
	            return "admin_home.do"; // 관리자 Command로 이동
	        } else {
	            System.out.println("일반 사용자 계정으로 로그인");
	            return "user_home.do"; // 사용자 Command로 이동
	        }
	    } else {
	        System.out.println("로그인 실패: " + email);
	        request.setAttribute("error", "이메일 또는 비밀번호가 잘못되었습니다.");
	        return "/WEB-INF/views/login.jsp";
	    }
	}
}
