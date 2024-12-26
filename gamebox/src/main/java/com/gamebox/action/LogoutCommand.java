package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션 무효화
        if (request.getSession(false) != null) { // 세션이 있는 경우에만 무효화
            request.getSession().invalidate();
        }

        // 리다이렉트 후 반환값 없음
        return "index.jsp";
    }
}
