package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.GameDAO;
import com.gamebox.dao.ReviewDAO;
import com.gamebox.dto.GameDTO;
import com.gamebox.dto.ReviewDTO;
import java.util.List;

public class GameDetailCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청 및 응답 데이터 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String gameId = request.getParameter("gameId");

        // gameId 유효성 검증
        if (gameId == null || gameId.isEmpty()) {
            throw new IllegalArgumentException("게임 ID가 유효하지 않습니다.");
        }

        // DAO 초기화
        GameDAO gameDAO = new GameDAO();
        ReviewDAO reviewDAO = new ReviewDAO();

        // CRUD 작업 확인
        String action = request.getParameter("action");
        if ("addReview".equals(action)) {
            handleAddReview(request, reviewDAO, Integer.parseInt(gameId));
        }

        // 게임 상세 정보 로드
        GameDTO game = gameDAO.getGameById(gameId);

        // 리뷰 리스트 로드
        List<ReviewDTO> reviews = reviewDAO.getReviewsByGameId(Integer.parseInt(gameId));

        // 로그인 상태 확인
        Integer userId = (Integer) request.getSession().getAttribute("loggedInUserId");
        boolean isLoggedIn = userId != null;

        // 사용자 리뷰 확인
        ReviewDTO userReview = null;
        if (isLoggedIn) {
            userReview = reviewDAO.getUserReviewForGame(userId, Integer.parseInt(gameId));
        }

        // 데이터 JSP로 전달
        request.setAttribute("game", game);
        request.setAttribute("reviews", reviews);
        request.setAttribute("isLoggedIn", isLoggedIn);
        request.setAttribute("userReview", userReview);

        return "/WEB-INF/views/common/shopDetail.jsp";
    }

    private void handleAddReview(HttpServletRequest request, ReviewDAO reviewDAO, int gameId) throws Exception {
        Integer userId = (Integer) request.getSession().getAttribute("loggedInUserId");
        String ratingStr = request.getParameter("rating");
        String content = request.getParameter("content");

        // 입력값 검증
        if (userId == null || ratingStr == null || ratingStr.isEmpty() || content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("모든 입력값이 필요합니다.");
        }

        int rating = Integer.parseInt(ratingStr);

        // 중복 리뷰 확인
        if (reviewDAO.getUserReviewForGame(userId, gameId) != null) {
            throw new IllegalArgumentException("이미 이 게임에 대한 리뷰를 작성하셨습니다.");
        }

        // DTO 생성 및 데이터 설정
        ReviewDTO newReview = new ReviewDTO();
        newReview.setUserId(userId);
        newReview.setGameId(gameId);
        newReview.setRating(rating);
        newReview.setContent(content);

        // DB에 리뷰 저장
        reviewDAO.addReview(newReview);
    }
}
