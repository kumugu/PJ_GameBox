<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>My Cart</title>
    <link rel="stylesheet" href="./resources/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<h1>장바구니</h1>

<c:if test="${empty cart}">
    <p>장바구니가 비어 있습니다.</p>
</c:if>
<c:if test="${not empty cart}">
    <table>
        <thead>
            <tr>
                <th>게임 ID</th>
                <th>게임 제목</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="gameId" items="${cart}">
                <tr>
                    <td>${gameId}</td>
                    <td>게임 제목 표시 필요</td> <!-- 실제 게임 제목을 추가하려면 DB 연결 필요 -->
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
