package com.gamebox.action;

import com.gamebox.dao.FAQDAO;
import com.gamebox.dao.SupportDAO;
import com.gamebox.dto.FAQDTO;
import com.gamebox.dto.SupportDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SupportCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        FAQDAO faqDAO = new FAQDAO();
        SupportDAO supportDAO = new SupportDAO();

        try {
            HttpSession session = request.getSession(false);
            Integer userId = (session != null) ? (Integer) session.getAttribute("loggedInUserId") : null;

            if (("write".equals(action) || "submit".equals(action) || "detail".equals(action)) && userId == null) {
                request.setAttribute("error", "로그인이 필요합니다.");
                return "/login.do";
            }

            if ("list".equals(action)) {
                List<FAQDTO> faqs = faqDAO.getAllFAQs();
                System.out.println("Command에서 로드된 FAQ 수: " + (faqs != null ? faqs.size() : "null"));

                List<SupportDTO> supports = userId != null ? supportDAO.getSupportByUserId(userId) : null;

                request.setAttribute("faqs", faqs);
                request.setAttribute("supports", supports);

                System.out.println("Request 속성 'faqs' 설정됨: " + (request.getAttribute("faqs") != null));
                System.out.println("FAQ 목록: " + faqs);

                return "/WEB-INF/views/common/support.jsp";

            } else if ("write".equals(action)) {
                return "/WEB-INF/views/common/supportWrite.jsp";

            } else if ("submit".equals(action)) {
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                if (title == null || content == null || title.isEmpty() || content.isEmpty()) {
                    request.setAttribute("error", "제목과 내용을 모두 입력해야 합니다.");
                    return "/WEB-INF/views/error.jsp";
                }

                SupportDTO support = new SupportDTO();
                support.setUserId(userId);
                support.setTitle(title);
                support.setContent(content);

                boolean success = supportDAO.addSupport(support);

                if (success) {
                    return "support.do?action=list";
                } else {
                    request.setAttribute("error", "문의 작성에 실패했습니다.");
                    return "/WEB-INF/views/error.jsp";
                }

            } else if ("detail".equals(action)) {
                String supportIdParam = request.getParameter("supportId");

                if (supportIdParam == null || supportIdParam.isEmpty()) {
                    request.setAttribute("error", "유효하지 않은 문의 ID입니다.");
                    return "/WEB-INF/views/error.jsp";
                }

                int supportId = Integer.parseInt(supportIdParam);
                SupportDTO support = supportDAO.getSupportById(supportId);

                if (support != null) {
                    request.setAttribute("support", support);
                    return "/WEB-INF/views/common/supportDetail.jsp";
                } else {
                    request.setAttribute("error", "해당 문의를 찾을 수 없습니다.");
                    return "/WEB-INF/views/error.jsp";
                }
            } else {
                request.setAttribute("error", "유효하지 않은 요청입니다.");
                return "/WEB-INF/views/error.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "처리 중 오류가 발생했습니다. " + e.getMessage());
            return "/WEB-INF/views/error.jsp";
        }
    }
}

