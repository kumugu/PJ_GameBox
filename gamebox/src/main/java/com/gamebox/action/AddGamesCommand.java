package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamebox.dao.GameDAO;
import com.gamebox.dto.GameDTO;

public class AddGamesCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 게임 추가 요청 처리
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String genre = request.getParameter("genre");
        String releaseDate = request.getParameter("releaseDate");
        String developer = request.getParameter("developer");
        double price = Double.parseDouble(request.getParameter("price"));

        // GameDTO 객체 생성 및 데이터 설정
        GameDTO game = new GameDTO();
        game.setTitle(title);
        game.setDescription(description);
        game.setGenre(genre);
        game.setReleaseDate(java.sql.Date.valueOf(releaseDate));
        game.setDeveloper(developer);
        game.setPrice(price);

        // DAO를 사용하여 데이터베이스에 저장
        GameDAO dao = new GameDAO();
        dao.addGame(game);

        // 성공 메시지 설정
        request.setAttribute("adminAlert", "게임 추가 성공!");

        // 관리 페이지로 리다이렉트
        return "/manage_games.do?action=view";
    }
}
