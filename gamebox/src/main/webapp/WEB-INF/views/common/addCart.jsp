<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>장바구니 추가 성공</title>
    <link rel="stylesheet" href="./resources/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<h1>${successMessage}</h1>
<div class="cart-options">
    <button onclick="location.href='shop.do'">계속 쇼핑</button>
    <button onclick="location.href='viewCart.do'">장바구니로 바로가기</button>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
