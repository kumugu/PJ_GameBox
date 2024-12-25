package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션 무효화
        request.getSession().invalidate();
        
        // 리다이렉트
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        
        // 리다이렉트 후에는 JSP를 반환하지 않음
        return null;
    }
}
