package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.UserDAO;
import com.gamebox.dto.UserDTO;

public class SignupCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청 데이터 인코딩 설정
        request.setCharacterEncoding("UTF-8");

        // 응답 데이터 인코딩 설정
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 입력값 가져오기
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        // UserDTO 객체 생성 및 값 설정
        UserDTO user = new UserDTO();
        user.setEmail(email);
        user.setPassword(password); // 비밀번호는 해싱하지 않음 (현재 단계)
        user.setName(name);
        user.setRole("USER"); // 기본 역할 USER 설정

        // DAO를 사용해 회원가입 처리
        UserDAO dao = new UserDAO();
        dao.registerUser(user);

        // 성공 메시지 설정
        request.setAttribute("message", "회원가입 성공!");
        return "/WEB-INF/views/login.jsp"; // 회원가입 후 로그인 페이지로 이동
    }
}
