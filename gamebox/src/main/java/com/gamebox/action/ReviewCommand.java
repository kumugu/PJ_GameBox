package com.gamebox.action;

import com.gamebox.dao.ReviewDAO;
import com.gamebox.dto.ReviewDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ReviewCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        ReviewDAO reviewDAO = new ReviewDAO();

        switch (action) {
            case "addReview":
                handleAddReview(request, reviewDAO);
                break;
            case "editReview":
                handleEditReview(request, reviewDAO);
                break;
            case "deleteReview":
                handleDeleteReview(request, reviewDAO);
                break;
        }

        // 리뷰 목록 갱신
        int gameId = Integer.parseInt(request.getParameter("gameId"));
        List<ReviewDTO> reviews = reviewDAO.getReviewsByGameId(gameId);
        request.setAttribute("reviews", reviews);

        return "/WEB-INF/views/common/reviewList.jsp";
    }

    private void handleAddReview(HttpServletRequest request, ReviewDAO reviewDAO) throws Exception {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int gameId = Integer.parseInt(request.getParameter("gameId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String content = request.getParameter("content");

        ReviewDTO review = new ReviewDTO();
        review.setUserId(userId);
        review.setGameId(gameId);
        review.setRating(rating);
        review.setContent(content);

        reviewDAO.addReview(review);
    }

    private void handleEditReview(HttpServletRequest request, ReviewDAO reviewDAO) throws Exception {
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String content = request.getParameter("content");

        ReviewDTO review = new ReviewDTO();
        review.setReviewId(reviewId);
        review.setRating(rating);
        review.setContent(content);

        reviewDAO.updateReview(review);
    }

    private void handleDeleteReview(HttpServletRequest request, ReviewDAO reviewDAO) throws Exception {
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        reviewDAO.deleteReview(reviewId);
    }
}
