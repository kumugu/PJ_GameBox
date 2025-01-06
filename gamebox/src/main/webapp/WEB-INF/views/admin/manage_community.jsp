<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>커뮤니티 관리 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_manageCommunity_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
<div class="admin-container">
    <h1>커뮤니티 관리</h1>

    <c:if test="${not empty postList}">
        <table>
            <thead>
                <tr>
                    <th>게시글 ID</th>
                    <th>작성자 ID</th>
                    <th>제목</th>
                    <th>작성일</th>
                    <th>조회수</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="post" items="${postList}">
                    <tr>
                        <td>${post.postId}</td>
                        <td>${post.userId}</td>
                        <td>${post.title}</td>
                        <td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd"/></td>
                        <td>${post.viewCount}</td>
                        <td>
                            <a href="manage_community.do?action=delete&postId=${post.postId}" class="btn btn-danger">삭제</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty postList}">
        <p>등록된 게시글이 없습니다.</p>
    </c:if>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
