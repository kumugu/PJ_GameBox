<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 관리 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_manageReviews_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
<div class="form-container">
    <h1>리뷰 관리</h1>

    <!-- 관리자 경고 메시지 -->
    <c:if test="${not empty adminAlert}">
        <div class="alert">${adminAlert}</div>
    </c:if>

    <!-- 리뷰 검색 -->
    <form action="manage_reviews.do" method="get" class="search-form">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" placeholder="게임 제목 또는 사용자 이름으로 검색" required>
        <button type="submit">검색</button>
    </form>

    <!-- 리뷰 목록 테이블 -->
    <table class="review-table">
        <thead>
            <tr>
                <th>리뷰 ID</th>
                <th>게임 제목</th>
                <th>작성자</th>
                <th>평점</th>
                <th>내용</th>
                <th>작성일</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="review" items="${reviewList}">
                <tr>
                    <td>${review.reviewId}</td>
                    <td>${review.gameTitle}</td>
                    <td>${review.userName}</td>
                    <td>${review.rating}</td>
                    <td>${review.content}</td>
                    <td>${review.createdAt}</td>
                    <td>
                        <a href="manage_reviews.do?action=delete&reviewId=${review.reviewId}" class="btn btn-delete" 
                           onclick="return confirm('정말 이 리뷰를 삭제하시겠습니까?');">삭제</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
