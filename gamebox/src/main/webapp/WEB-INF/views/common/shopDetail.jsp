<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>${game.title} | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/shopDetail_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<c:if test="${not empty game}">
    <div class="game-detail">
        <h1>${game.title}</h1>
        <img src="${pageContext.request.contextPath}${game.imagePath}" alt="${game.title}">
   
        <p><strong>장르:</strong> ${game.genre}</p>
        <p><strong>출시일:</strong> ${game.releaseDate}</p>
        <p><strong>개발사:</strong> ${game.developer}</p>
        <p><strong>최근 평가:</strong> ${game.reviewSummary}</p>
        
        
        
        <!-- 가격 및 장바구니 섹션 -->
		<div class="price-cart-container">
		    <div class="price-info">
		        <!-- 하드코딩된 할인율 -->
		        <span class="discount">-10%</span>
		
		        <!-- 현재 가격 -->
		        <span class="current-price">₩${game.price}</span>
		    </div>
		
		    <!-- 장바구니 버튼 -->
		    <button class="cart-btn" onclick="addToCart('${game.gameId}')">장바구니에 추가</button>
		</div>
        
        <p><strong>설명:</strong> ${game.description}</p>
        <p><strong>최소 사양</strong></p>
        <pre>${game.minRequirements}</pre>
        <p><strong>권장 사양</strong></p>
        <pre>${game.recRequirements}</pre>
    </div>
</c:if>


<c:if test="${not empty error}">
    <div class="error-message">
        <p>${error}</p>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
