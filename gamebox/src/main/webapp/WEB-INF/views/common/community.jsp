<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>커뮤니티 | GameBox</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/community_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="community-container">
    <h1>커뮤니티 게시판</h1>

    <c:if test="${not empty posts}">
        <table class="community-table">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="post" items="${posts}">
                    <tr>
                        <td>${post.postId}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/community.do?action=detail&postId=${post.postId}">
                                ${post.title}
                            </a>
                        </td>
                        <td>${post.userName}</td>
                        <td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd" /></td>
                        <td>${post.viewCount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty posts}">
        <p>게시글이 없습니다.</p>
    </c:if>

    <c:if test="${not empty sessionScope.loggedInUserId}">
        <div class="actions">
            <a href="${pageContext.request.contextPath}/community.do?action=write" class="btn btn-primary">글 작성하기</a>
        </div>
    </c:if>

</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
