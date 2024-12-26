package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.UserDAO;
import com.gamebox.dto.UserDTO;
import java.util.List;

public class ManageUsersCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청 데이터 인코딩 설정
        request.setCharacterEncoding("UTF-8");

        // 응답 데이터 인코딩 설정
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    	
        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();

        if (action == null || action.isEmpty() || "view".equals(action)) {
            // 전체 회원 조회
            List<UserDTO> userList = dao.getAllUsers();
            request.setAttribute("userList", userList);
            return "/WEB-INF/views/admin/manage_users.jsp";
        } else if ("add_form".equals(action)) {
            // 회원 추가 폼 페이지로 이동
            return "/WEB-INF/views/admin/add_user.jsp";
        } else if ("add".equals(action)) {
            // 회원 추가
            UserDTO user = new UserDTO();
            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setRole(request.getParameter("role"));
            dao.addUser(user);
            request.setAttribute("adminAlert", "회원 추가 성공!");
        } else if ("edit".equals(action)) {
            // 회원 수정
            int userId = Integer.parseInt(request.getParameter("userId"));
            UserDTO user = dao.getUserById(userId);
            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));
            user.setRole(request.getParameter("role"));
            dao.updateUser(user);
            request.setAttribute("adminAlert", "회원 수정 성공!");
        } else if ("delete".equals(action)) {
            // 회원 삭제
            int userId = Integer.parseInt(request.getParameter("userId"));
            dao.deleteUser(userId);
            request.setAttribute("adminAlert", "회원 삭제 성공!");
        }

        // 처리 후 목록 페이지로 이동
        return "/manage_users.do?action=view";
    }
}
