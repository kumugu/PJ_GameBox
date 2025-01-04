<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>My Cart</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/cart_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<h1>장바구니</h1>

<c:if test="${empty cartItems}">
    <p>장바구니가 비어 있습니다.</p>
</c:if>
<c:if test="${not empty cartItems}">
    <div class="cart-container">
        <!-- 왼쪽: 장바구니 항목 -->
        <div class="cart-items">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>이미지</th>
                        <th>게임 제목</th>
                        <th>가격</th>
                        <th>추가 날짜</th>
                        <th>작업</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- 장바구니 항목 출력 -->
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>
                                <!-- 이미지 추가 -->
                                <img src="${pageContext.request.contextPath}${item.imagePath}" alt="${item.gameTitle}" class="game-image">
                                
                            </td>
                            <td>${item.gameTitle}</td>
                            <td>₩${item.gamePrice}</td>
                            <td>${item.addedDate}</td>
                            <td>
                                <!-- 삭제 버튼 -->
                                <form action="deleteCartItem.do" method="post" style="display: inline;">
                                    <input type="hidden" name="cartId" value="${item.cartId}" />
                                    <button type="submit" class="cart-btn delete-btn">삭제</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- 오른쪽: 총 금액 및 결제 버튼 -->
        <div class="cart-summary">
            <h2>예상 합계</h2>
            <c:set var="totalPrice" value="0" />
            <c:forEach var="item" items="${cartItems}">
                <c:set var="totalPrice" value="${totalPrice + item.gamePrice}" />
            </c:forEach>
            <p>총 금액: <span class="total-price">₩${totalPrice}</span></p>
            <form action="checkout.do" method="post">
                <button type="submit" class="cart-btn checkout-btn">결제하기</button>
            </form>
        </div>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
