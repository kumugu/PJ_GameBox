package com.gamebox.action;

import com.gamebox.dao.GameDAO;
import com.gamebox.dto.GameDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShopCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        GameDAO gameDAO = new GameDAO();
        List<GameDTO> gameList = gameDAO.getAllGames();
        request.setAttribute("gameList", gameList);
        return "/WEB-INF/views/common/shop.jsp";
    }
}
