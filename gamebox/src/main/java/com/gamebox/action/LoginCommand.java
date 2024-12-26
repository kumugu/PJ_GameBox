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

        // 입력값 검증
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "이메일과 비밀번호를 모두 입력해주세요.");
            return "/WEB-INF/views/login.jsp";
        }

        UserDAO dao = new UserDAO();
        UserDTO user = dao.loginUser(email, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            System.out.println("로그인 성공: " + user.getEmail());
            System.out.println("사용자 역할: [" + (user.getRole() != null ? user.getRole() : "UNKNOWN") + "]");

            String role = (user.getRole() != null) ? user.getRole().trim() : "";
            if ("ADMIN".equalsIgnoreCase(role)) {
                System.out.println("관리자 계정으로 로그인");
                return "admin_home.do";
            } else {
                System.out.println("일반 사용자 계정으로 로그인");
                return "user_home.do";
            }
        } else {
            System.out.println("로그인 실패: " + email);
            request.setAttribute("error", "이메일 또는 비밀번호가 잘못되었습니다.");
            return "/WEB-INF/views/login.jsp";
        }
    }
}
