package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ViewCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<String> cart = (List<String>) session.getAttribute("cart");

        // 장바구니 데이터를 JSP에 전달
        request.setAttribute("cart", cart);
        return "/WEB-INF/views/common/cart.jsp";
    }
}
