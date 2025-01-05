package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gamebox.dao.PaymentDAO;

public class PaymentResultCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 요청 파라미터에서 결제 ID 가져오기
            int paymentId = Integer.parseInt(request.getParameter("paymentId"));

            // 2. Mock 결제 결과 (80% 성공, 20% 실패)
            boolean isSuccess = Math.random() > 0.2;
            String status = isSuccess ? "SUCCESS" : "FAILED";

            // 3. 결제 상태 업데이트
            PaymentDAO paymentDAO = new PaymentDAO();
            paymentDAO.updatePaymentStatus(paymentId, status);

            // 4. 결과에 따라 View 선택
            request.setAttribute("paymentStatus", status);
            return isSuccess ? "/WEB-INF/views/payment/paymentSuccess.jsp" : "/WEB-INF/views/payment/paymentFailure.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "결제 결과 처리 중 오류가 발생했습니다.");
            return "/WEB-INF/views/payment/paymentError.jsp";
        }
    }
}
