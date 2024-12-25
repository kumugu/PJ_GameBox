<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <link rel="stylesheet" href="./resources/css/header_style.css">
</head>
<header>
    <div class="navbar">
        <div class="logo">
            <a href="index.jsp">GameBox</a>
        </div>
        <nav>
            <a href="index.jsp">홈</a>
            <a href="games.jsp">상점</a>
            <a href="community.jsp">커뮤니티</a>
            <a href="library.jsp">마이페이지</a>
            <a href="support.jsp">지원</a>
        </nav>
        <div class="user-info">
			<c:choose>
			    <c:when test="${not empty sessionScope.user}">
			        <span>Welcome, ${sessionScope.user.name}</span>
			        <a href="logout.do">로그아웃</a>
			    </c:when>
			    <c:otherwise>
			        <a href="login.do" class="login-btn">로그인</a>
			        <a href="signup.do" class="signup-btn">회원가입</a>
			    </c:otherwise>
			</c:choose>
        </div>
    </div>
</header>


