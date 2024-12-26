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
        <h1>관리자 페이지</h1>
        <div class="user-info">
			<c:choose>
			    <c:when test="${not empty sessionScope.user}">
			        <span>Welcome, ${sessionScope.user.name}</span>
			        <a href="logout.do">로그아웃</a>
			    </c:when>
			</c:choose>
        </div>
    </div>
</header>

