package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.ReviewDAO;
import com.gamebox.dto.ReviewDTO;
import java.util.List;

public class ManageReviewsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action"); // CRUD 작업 확인
        ReviewDAO reviewDAO = new ReviewDAO();

        if ("delete".equals(action)) {
            // 리뷰 삭제 처리
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            reviewDAO.deleteReview(reviewId);
            request.setAttribute("adminAlert", "리뷰가 삭제되었습니다.");
        }

        // 모든 리뷰 목록 로드
        List<ReviewDTO> reviews = reviewDAO.getAllReviews();
        request.setAttribute("reviewList", reviews);

        // 관리 페이지로 이동
        return "/WEB-INF/views/admin/manage_reviews.jsp";
    }
}
