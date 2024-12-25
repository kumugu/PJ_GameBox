<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Admin Home|GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<c:if test="${not empty adminAlert}">
	    <div class="alert-message">
	        ${adminAlert}
	    </div>
	</c:if>
	
	<div>
		<hr>
		<h1>관리자 페이지</h1>
		<hr>
		<br><br>
		
	    <p>환영합니다, ${sessionScope.user.name}님! 관리자 권한으로 로그인하셨습니다.</p>
	    <ul>
	        <li><a href="manage_users.do">회원 관리</a></li>
	        <li><a href="manage_games.do">게임 관리</a></li>
	        <li><a href="manage_reviews.do">리뷰 관리</a></li>
	    </ul>
	</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>

