<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Shop | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/shop_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<!-- 게임 목록 -->
<div class="game-list">
    <table align="center" class="game-table">
        <thead>
            <tr>
                <th>이미지</th>
                <th>게임 제목</th>
                <th>장르</th>
                <th>출시일</th>
                <th>평점</th>
                <th>가격</th>
                <th>장바구니</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="game" items="${gameList}">
                <tr class="game-row" onclick="window.location.href='${pageContext.request.contextPath}/gameDetail.do?gameId=${game.gameId}'">
                    <td>
                        <img src="${pageContext.request.contextPath}${game.imagePath}" alt="${game.title}" class="game-image">
                    </td>
                    <td>${game.title}</td>
                    <td>${game.genre}</td>
                    <td>${game.releaseDate}</td>
                    <td>${game.rating}</td>
                    <td>₩${game.price}</td>
                    <td>
                        <!-- 장바구니 추가 버튼 -->
                        <form action="${pageContext.request.contextPath}/cart.do" method="post" style="display:inline;">
                            <input type="hidden" name="gameId" value="${game.gameId}">
                            <button type="submit" class="cart-btn">장바구니에 추가</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
