<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
    <title>Admin Home|GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_home_style.css">
</head>

<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
	<div class="sidebar">
	    <ul class="folder-structure">
	        <li>
	            <span>관리 기능</span>
	            <ul class="visible">
	                <li><a href="manage_users.do">회원 관리</a></li>
	                <li><a href="manage_games.do">게임 관리</a></li>
	                <li><a href="manage_reviews.do">리뷰 관리</a></li>
	                <li><a href="manage_support.do">문의 관리</a></li>
    	            <li><a href="manage_community.do">커뮤니티 관리</a></li>
	            </ul>
	        </li>
	        <li>
	            <span>설정</span>
	            <ul class="visible">
	                <li><a href="manage_roles.do">권한 관리</a></li>
	                <li><a href="site_settings.do">사이트 설정</a></li>
	            </ul>
	        </li>
	    </ul>
	</div>
</body>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

</html>

