<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>글 작성 | GameBox</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/community_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="community-container">
	<form action="${pageContext.request.contextPath}/community.do?action=submit" method="post" accept-charset="UTF-8">
	    <div class="form-group">
	        <label for="title">제목</label>
	        <input type="text" id="title" name="title" required class="form-control" placeholder="제목을 입력하세요">
	    </div>
	    <div class="form-group">
	        <label for="content">내용</label>
	        <textarea id="content" name="content" required class="form-control" rows="10" placeholder="내용을 입력하세요"></textarea>
	    </div>
	    <button type="submit" class="btn">작성 완료</button>
	</form>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
