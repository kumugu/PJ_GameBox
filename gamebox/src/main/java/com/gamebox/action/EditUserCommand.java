package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.UserDAO;
import com.gamebox.dto.UserDTO;

public class EditUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();

        if ("edit".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            UserDTO user = dao.getUserById(userId);
            request.setAttribute("user", user);
        }

        request.setAttribute("action", action);
        return "/WEB-INF/views/admin/edit_user.jsp";
    }
}
