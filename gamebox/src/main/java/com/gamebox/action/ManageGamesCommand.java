package com.gamebox.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.GameDAO;
import com.gamebox.dto.GameDTO;

public class ManageGamesCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청 데이터 인코딩 설정
        request.setCharacterEncoding("UTF-8");

        // 응답 데이터 인코딩 설정
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        GameDAO gameDAO = new GameDAO();

        if (action == null || action.isEmpty() || "view".equals(action)) {
            // 전체 게임 조회
            List<GameDTO> gameList = gameDAO.getAllGames();
            request.setAttribute("gameList", gameList);
            return "/WEB-INF/views/admin/manage_games.jsp";
        } else if ("add_form".equals(action)) {
            // 게임 추가 폼 페이지로 이동
            return "/WEB-INF/views/admin/add_game.jsp";
        } else if ("add".equals(action)) {
            // 게임 추가
            GameDTO game = new GameDTO();
            game.setTitle(request.getParameter("title"));
            game.setDescription(request.getParameter("description"));
            game.setGenre(request.getParameter("genre"));
            game.setReleaseDate(java.sql.Date.valueOf(request.getParameter("releaseDate")));
            game.setDeveloper(request.getParameter("developer"));
            game.setPrice(Double.parseDouble(request.getParameter("price")));
            gameDAO.addGame(game);
            request.getSession().setAttribute("adminAlert", "게임 추가 성공!");
        } else if ("edit".equals(action)) {
            // 게임 수정
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            GameDTO game = gameDAO.getGameById(gameId);
            game.setTitle(request.getParameter("title"));
            game.setDescription(request.getParameter("description"));
            game.setGenre(request.getParameter("genre"));
            game.setReleaseDate(java.sql.Date.valueOf(request.getParameter("releaseDate")));
            game.setDeveloper(request.getParameter("developer"));
            game.setPrice(Double.parseDouble(request.getParameter("price")));
            gameDAO.updateGame(game);
            request.getSession().setAttribute("adminAlert", "게임 수정 성공!");
        } else if ("delete".equals(action)) {
            // 게임 삭제
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            gameDAO.deleteGame(gameId);
            request.getSession().setAttribute("adminAlert", "게임 삭제 성공!");
        }

        // 처리 후 목록 페이지로 이동
        return "/manage_games.do?action=view";
    }
}
