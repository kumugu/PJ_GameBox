<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>결제 성공 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/payment_success_style.css">
    <link rel="stylesheet" href="./resources/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <h1>결제 성공</h1>
    <div class="success-container">
        <div class="success-details">
            <p><strong>결제 ID:</strong> ${paymentId}</p>
            <p><strong>결제 금액:</strong> <span class="total-price">￦${amount}</span></p>
            <p><strong>결제 상태:</strong> ${status}</p>
            <p><strong>결제 일자:</strong> ${createdAt}</p>
            <p class="thank-you-message">결제가 성공적으로 완료되었습니다. 감사합니다!</p>
        </div>

        <!-- 돌아가기 버튼 -->
        <div class="success-actions">
            <a href="user_home.do" class="btn btn-secondary">홈으로 돌아가기</a>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
