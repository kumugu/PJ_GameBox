package com.gamebox.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamebox.dao.SupportDAO;
import com.gamebox.dto.SupportDTO;

public class ManageSupportCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        SupportDAO supportDAO = new SupportDAO();

        try {
            if ("delete".equals(action)) {
                // 문의 삭제 처리
                int supportId = Integer.parseInt(request.getParameter("supportId"));
                boolean success = supportDAO.deleteSupport(supportId);
                request.setAttribute("adminAlert", success ? "문의가 삭제되었습니다." : "문의 삭제에 실패했습니다.");
            } else if ("resolve".equals(action)) {
                // 문의 상태 변경 처리
                int supportId = Integer.parseInt(request.getParameter("supportId"));
                boolean success = supportDAO.updateSupportStatus(supportId, "ANSWERED");
                request.setAttribute("adminAlert", success ? "문의가 해결 상태로 변경되었습니다." : "문의 상태 변경에 실패했습니다.");
            } else if ("reply".equals(action)) {
                // 문의 답변 작성 처리
                int supportId = Integer.parseInt(request.getParameter("supportId"));
                String reply = request.getParameter("reply");
                boolean success = supportDAO.addReplyToSupport(supportId, reply);
                request.setAttribute("adminAlert", success ? "문의 답변이 등록되었습니다." : "문의 답변 등록에 실패했습니다.");
            }

            // 모든 문의 목록 로드
            List<SupportDTO> supports = supportDAO.getAllSupports();
            request.setAttribute("supportList", supports);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "처리 중 오류가 발생했습니다: " + e.getMessage());
            return "/WEB-INF/views/error.jsp";
        }

        // 관리 페이지로 이동
        return "/WEB-INF/views/admin/manage_support.jsp";
    }
}
