<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>${game.title} | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/shopDetail_style.css">
    <link rel="stylesheet" href="./resources/css/star_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="content-container">
    <!-- 상품 상세 정보 -->
    <div class="game-detail">
        <h1>${game.title}</h1>
        <img src="${pageContext.request.contextPath}${game.imagePath}" alt="${game.title}">
        <p><strong>장르:</strong> ${game.genre}</p>
        <p><strong>출시일:</strong> ${game.releaseDate}</p>
        <p><strong>개발사:</strong> ${game.developer}</p>
        <p><strong>최근 평가:</strong> ${game.reviewSummary}</p>
        <div class="price-cart-container">
            <div class="price-info">
                <span class="discount">-10%</span>
                <span class="current-price">₩${game.price}</span>
            </div>
            <button class="cart-btn" onclick="addToCart('${game.gameId}')">장바구니에 추가</button>
        </div>
        <p><strong>설명:</strong> ${game.description}</p>
        <p><strong>최소 사양</strong></p>
        <pre>${game.minRequirements}</pre>
        <p><strong>권장 사양</strong></p>
        <pre>${game.recRequirements}</pre>
    </div>

    <!-- 리뷰 섹션 -->
	<div class="review-section">
	    <h2>리뷰</h2>
	    <c:choose>
	        <c:when test="${!isLoggedIn}">
	            <p>리뷰를 작성하려면 <a href="login.do">로그인</a>해주세요.</p>
	        </c:when>
	
		<c:when test="${isLoggedIn && empty userReview}">
		    <form id="reviewForm" action="${pageContext.request.contextPath}/gameDetail.do" method="post">
		        <input type="hidden" name="action" value="addReview">
		        <input type="hidden" name="gameId" value="${game.gameId}">
		        
		        <!-- 별점 입력 -->
		        <div class="star-rating">
		            <c:forEach var="i" begin="1" end="10" step="1">
		                <input type="radio" name="rating" id="star${10 - i + 1}" value="${10 - i + 1}">
		                <label for="star${10 - i + 1}" title="${10 - i + 1}점"></label>
		            </c:forEach>
		        </div>
		        
		        <!-- 리뷰 내용 입력 -->
		        <textarea name="content" placeholder="리뷰를 작성해주세요..." required></textarea>
		        <button type="submit">작성</button>
		    </form>
		</c:when>

	
	        <c:when test="${isLoggedIn && not empty userReview}">
	            <p>리뷰를 작성하셨습니다.</p>
	        </c:when>
	    </c:choose>
	
	    <h2>리뷰 목록</h2>
	    <div class="review-list">
		<c:forEach var="review" items="${reviews}">
		    <div class="review-item">
		        <p><strong>${review.userName}</strong></p>
		        <p>${review.content}</p>
		        <div class="star-rating-display">
		            <c:forEach var="i" begin="1" end="10" step="1">
		                <span class="star ${i <= review.rating ? 'filled' : ''}">★</span>
		            </c:forEach>
		        </div>
		        <p><em>${review.createdAt}</em></p>
		    </div>
		</c:forEach>
	    </div>
	</div>


</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
