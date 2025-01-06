package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.gamebox.dao.CommunityDAO;
import com.gamebox.dto.CommunityDTO;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CommunityCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
	    response.setContentType("text/html; charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String action = request.getParameter("action");
	    CommunityDAO communityDAO = new CommunityDAO();
	    System.out.println("Received action: " + action);

	    try {
	        // 로그인 확인 (공통 로직)
	        HttpSession session = request.getSession(false); // 기존 세션 가져오기
	        Integer userId = (session != null) ? (Integer) session.getAttribute("loggedInUserId") : null;
	        System.out.println("Logged in user ID: " + userId);

	        if (("write".equals(action) || "submit".equals(action)) && userId == null) {
	            // 로그인 필요한 요청에서 세션이 없으면 로그인 페이지로 리다이렉트
	            request.setAttribute("error", "로그인이 필요합니다.");
	            return "/login.do";
	        }

	        if ("list".equals(action)) {
	            // 게시글 목록 조회
	            List<CommunityDTO> posts = communityDAO.getAllPosts();
	            System.out.println("Fetched posts: " + posts);
	            request.setAttribute("posts", posts);
	            return "/WEB-INF/views/common/community.jsp";

	        } else if ("detail".equals(action)) {
	            // 게시글 상세 조회
	            String postIdParam = request.getParameter("postId");
	            if (postIdParam == null || postIdParam.isEmpty()) {
	                request.setAttribute("error", "유효하지 않은 게시글입니다.");
	                return "/WEB-INF/views/error.jsp";
	            }
	            int postId = Integer.parseInt(postIdParam);

	            // 조회수 증가
	            communityDAO.incrementViewCount(postId);

	            CommunityDTO post = communityDAO.getPostById(postId);
	            if (post == null) {
	                request.setAttribute("error", "해당 게시글을 찾을 수 없습니다.");
	                return "/WEB-INF/views/error.jsp";
	            }
	            request.setAttribute("post", post);
	            return "/WEB-INF/views/common/postDetail.jsp";

	        } else if ("write".equals(action)) {
	            // 게시글 작성 페이지
	            return "/WEB-INF/views/common/postWrite.jsp";

	        } else if ("submit".equals(action)) {
	            // 게시글 작성 처리
	            String title = request.getParameter("title");
	            String content = request.getParameter("content");

	            if (title == null || content == null || title.isEmpty() || content.isEmpty()) {
	                request.setAttribute("error", "제목과 내용을 모두 입력해야 합니다.");
	                return "/WEB-INF/views/error.jsp";
	            }

	            CommunityDTO post = new CommunityDTO();
	            post.setUserId(userId); // 로그인된 사용자 ID
	            post.setTitle(title);
	            post.setContent(content);

	            boolean success = communityDAO.addPost(post);
	            System.out.println("Post submission success: " + success);
	            return success ? "community.do?action=list" : "/WEB-INF/views/error.jsp";

	        } else {
	            // 유효하지 않은 action 요청
	            request.setAttribute("error", "유효하지 않은 요청입니다.");
	            return "/WEB-INF/views/error.jsp";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "처리 중 오류가 발생했습니다: " + e.getMessage());
	        return "/WEB-INF/views/error.jsp";
	    }
	}

}
