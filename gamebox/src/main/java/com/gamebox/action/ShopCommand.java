package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShopCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 회원 추가 페이지로 이동
        return "/WEB-INF/views/common/shop.jsp";
	}

}



