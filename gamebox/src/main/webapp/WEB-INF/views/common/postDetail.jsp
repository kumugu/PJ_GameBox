<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 상세 | GameBox</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/community_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="community-container">
<div class="post-detail-container">
    <h1>${post.title}</h1>
    <p>작성자: ${post.userName}</p>
    <p>작성일: <fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
    <p>조회수: ${post.viewCount}</p>
    <div class="post-content">
        ${post.content}
    </div>
    <a href="${pageContext.request.contextPath}/community.do?action=list" class="btn btn-secondary">목록으로 돌아가기</a>
</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
