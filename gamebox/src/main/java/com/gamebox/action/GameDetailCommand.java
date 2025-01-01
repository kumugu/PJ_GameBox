package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.GameDAO;
import com.gamebox.dto.GameDTO;

public class GameDetailCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 요청에서 gameId 가져오기
        String gameId = request.getParameter("gameId");

        // 2. DAO 호출하여 게임 정보 가져오기
        GameDAO gameDAO = new GameDAO();
        GameDTO game = gameDAO.getGameById(gameId);

        // 3. 게임 정보를 request 속성에 저장
        if (game != null) {
            request.setAttribute("game", game);
        } else {
            // 게임이 없을 경우 에러 처리
            request.setAttribute("error", "게임 정보를 찾을 수 없습니다.");
        }

        // 4. 상세 페이지 JSP로 이동
        return "/WEB-INF/views/common/shopDetail.jsp";
    }
}
