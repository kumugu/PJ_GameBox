<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Mypage | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/mypage_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <!-- 사용자 기본 정보 -->
    <div class="header">
        <h1>안녕하세요, ${loggedInUserName}님!</h1>
    </div>
    <div class="section">
        <h2>사용자 기본 정보</h2>
        <div class="info-box">
            <p><strong>이름:</strong> ${user.name}</p>
            <p><strong>메일:</strong> ${user.email}</p>
         	<p><strong>가입일:</strong> <fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd"/></p>
        </div>
    </div>

    <!-- 구매 및 결제 내역 -->
    <div class="section">
        <h2>구매 및 결제 내역</h2>
        <c:if test="${not empty payments}">
            <table>
                <thead>
                    <tr>
                        <th>결제 ID</th>
                        <th>금액</th>
                        <th>상태</th>
                        <th>결제 일시</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="payment" items="${payments}">
                        <tr>
                            <td>${payment.paymentId}</td>
                            <td>${payment.amount}</td>
                            <td>${payment.status}</td>
                            <td>${payment.createdAt}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty payments}">
            <p>결제 내역이 없습니다.</p>
        </c:if>
    </div>

    <!-- 리뷰 평가 -->
	<div class="section">
	    <h2>작성한 리뷰</h2>
	    <c:if test="${not empty reviews}">
	        <table>
	            <thead>
	                <tr>
	                    <th>리뷰 ID</th>
	                    <th>게임 제목</th>
	                    <th>평점</th>
	                    <th>리뷰 내용</th>
	                    <th>작성일</th>
	                </tr>
	            </thead>
	            <tbody>
	                <c:forEach var="review" items="${reviews}">
	                    <tr>
	                        <td>${review.reviewId}</td>
	                        <td>${review.gameTitle}</td>
	                        <td>${review.rating}</td>
	                        <td>${review.content}</td>
	                        <td><fmt:formatDate value="${review.createdAt}" pattern="yyyy-MM-dd"/></td>
	                    </tr>
	                </c:forEach>
	            </tbody>
	        </table>
	    </c:if>
	    <c:if test="${empty reviews}">
	        <p>작성한 리뷰가 없습니다.</p>
	    </c:if>
	</div>
	
	<!-- 커뮤니티 게시글 내역 -->
	<div class="section">
	    <h2>작성한 커뮤니티 게시글</h2>
	    <c:if test="${not empty posts}">
	        <table>
	            <thead>
	                <tr>
	                    <th>게시글 ID</th>
	                    <th>제목</th>
	                    <th>작성일</th>
	                    <th>조회수</th>
	                </tr>
	            </thead>
	            <tbody>
	                <c:forEach var="post" items="${posts}">
	                    <tr>
	                        <td>${post.postId}</td>
	                        <td>${post.title}</td>
	                        <td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd"/></td>
	                        <td>${post.viewCount}</td>
	                    </tr>
	                </c:forEach>
	            </tbody>
	        </table>
	    </c:if>
	    <c:if test="${empty posts}">
	        <p>작성한 커뮤니티 게시글이 없습니다.</p>
	    </c:if>
	</div>
	
	<!-- 문의 내역 -->
	<div class="section">
	    <h2>문의 내역</h2>
	    <c:if test="${not empty supports}">
	        <table>
	            <thead>
	                <tr>
	                    <th>문의 ID</th>
	                    <th>제목</th>
	                    <th>상태</th>
	                    <th>작성일</th>
	                </tr>
	            </thead>
	            <tbody>
	                <c:forEach var="support" items="${supports}">
	                    <tr>
	                        <td>${support.supportId}</td>
	                        <td>${support.title}</td>
	                        <td>${support.status}</td>
	                        <td><fmt:formatDate value="${support.createdAt}" pattern="yyyy-MM-dd"/></td>
	                    </tr>
	                </c:forEach>
	            </tbody>
	        </table>
	    </c:if>
	    <c:if test="${empty supports}">
	        <p>문의 내역이 없습니다.</p>
	    </c:if>
	</div>
		
	
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>

</body>
</html>
