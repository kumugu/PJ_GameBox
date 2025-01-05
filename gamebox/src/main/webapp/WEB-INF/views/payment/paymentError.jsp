<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>결제 실패 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/payment_style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

    <h1>결제 실패</h1>
    <p>결제 처리 중 오류가 발생했습니다.</p>

    <c:if test="${not empty error}">
        <p><strong>오류 메시지:</strong> ${error}</p>
    </c:if>

    <!-- 다시 시도 버튼 -->
    <p>
        <a href="viewCart.do" class="btn btn-primary">장바구니로 돌아가기</a>
    </p>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
