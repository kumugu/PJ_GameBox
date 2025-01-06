<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <link rel="stylesheet" href="./resources/css/header_style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>

<header>
    <div class="navbar">
        <div class="logo">
            <a href="index.jsp">GameBox</a>
             <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <a href="admin_home.do">관리자 페이지</a>
            </c:if>
        </div>
        
        <nav>
            <a href="shop.do">상점</a>
  			<a href="community.do?action=list">커뮤니티</a>
            <a href="mypage.do">마이페이지</a>
            <a href="support.do?action=list">지원</a>
        </nav>
        
		<div class="user-info">
		    <c:choose>

		        <c:when test="${not empty sessionScope.user}">
			        <a href="viewCart.do" class="cart-btn-icon">
					    <i class="fas fa-shopping-cart"></i>
					</a>
		            <span>${sessionScope.user.name}</span>
		            <a href="logout.do">로그아웃</a>
		            <!-- 장바구니로 이동 버튼 추가 -->
		        </c:when>
		        <c:otherwise>
		            <a href="login.do" class="login-btn">로그인</a>
		            <a href="signup_form.do" class="signup-btn">회원가입</a>
		        </c:otherwise>
		    </c:choose>
		</div>
    </div>
</header>


