package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gamebox.dao.UserDAO;
import com.gamebox.dao.PaymentDAO;
import com.gamebox.dao.ReviewDAO;
import com.gamebox.dao.CommunityDAO;
import com.gamebox.dao.SupportDAO;

import com.gamebox.dto.UserDTO;
import com.gamebox.dto.PaymentDTO;
import com.gamebox.dto.ReviewDTO;
import com.gamebox.dto.CommunityDTO;
import com.gamebox.dto.SupportDTO;

import java.util.List;

public class MypageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("loggedInUserId");
            if (userId == null) {
                return "login.do"; // 로그인 페이지로 리다이렉트
            }

            // 사용자 정보 가져오기
            UserDAO userDAO = new UserDAO();
            UserDTO user = userDAO.getUserById(userId);
            request.setAttribute("user", user);

            // 결제 내역 가져오기
            PaymentDAO paymentDAO = new PaymentDAO();
            List<PaymentDTO> payments = paymentDAO.getPaymentsByUserId(userId);
            request.setAttribute("payments", payments);

            // 리뷰 정보 가져오기
            ReviewDAO reviewDAO = new ReviewDAO();
            List<ReviewDTO> reviews = reviewDAO.getReviewsByUserId(userId); 
            request.setAttribute("reviews", reviews);

            // 커뮤니티 게시글 가져오기
            CommunityDAO communityDAO = new CommunityDAO();
            List<CommunityDTO> posts = communityDAO.getPostsByUserId(userId);
            request.setAttribute("posts", posts);

            // 문의 내역 가져오기
            SupportDAO supportDAO = new SupportDAO();
            List<SupportDTO> supports = supportDAO.getSupportByUserId(userId);
            request.setAttribute("supports", supports);

            return "/WEB-INF/views/common/mypage.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "데이터를 불러오는 중 문제가 발생했습니다.");
            return "/WEB-INF/views/error.jsp";
        }
    }
}
