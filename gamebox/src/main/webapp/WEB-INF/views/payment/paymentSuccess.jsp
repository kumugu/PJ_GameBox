<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>결제 성공 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/payment.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

    <h1>결제 성공</h1>
    <p>결제 ID: ${paymentId}</p>
    <p>결제 금액: ￦${amount}</p>
    <p>결제 상태: ${status}</p>
    <p>결제 일자: ${createdAt}</p>
    <p>결제가 성공적으로 완료되었습니다. 감사합니다!</p>

    <!-- 돌아가기 버튼 -->
    <p>
        <a href="user_home.do" class="btn btn-secondary">홈으로 돌아가기</a>
    </p>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
