package com.gamebox.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamebox.dao.CommunityDAO;
import com.gamebox.dto.CommunityDTO;

public class ManageCommunityCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        CommunityDAO communityDAO = new CommunityDAO();

        if ("delete".equals(action)) {
            // 게시글 삭제 처리
            int postId = Integer.parseInt(request.getParameter("postId"));
            communityDAO.deletePost(postId);
            request.setAttribute("adminAlert", "게시글이 삭제되었습니다.");
        }

        // 모든 게시글 목록 로드
        List<CommunityDTO> posts = communityDAO.getAllPosts();
        request.setAttribute("postList", posts);

        // 관리 페이지로 이동
        return "/WEB-INF/views/admin/manage_community.jsp";
    }
}
