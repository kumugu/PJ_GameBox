<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 관리 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_manageUsers_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
<div class="form-container">
    <h1>회원 관리</h1>

    <!-- 관리자 경고 메시지 -->
    <c:if test="${not empty adminAlert}">
        <div class="alert">${adminAlert}</div>
    </c:if>

    <!-- 검색 기능 -->
    <form action="manage_users.do" method="get" class="search-form">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" placeholder="이름 또는 이메일로 검색(미구현)" required>
        <button type="submit">검색</button>
    </form>

    <!-- 회원 추가 버튼 -->
    <div class="button-container">
        <a href="add_user_form.do" class="btn btn-add">회원 추가</a>
    </div>

    <!-- 회원 목록 테이블 -->
    <table class="user-table">
        <thead>
            <tr>
                <th>회원 ID</th>
                <th>이름</th>
                <th>이메일</th>
                <th>역할</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td><a href="edit_user.do?action=edit&userId=${user.userId}" class="btn btn-edit">수정</a></td>
                    <td><a href="manage_users.do?action=delete&userId=${user.userId}" class="btn btn-delete" 
                           onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>


</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
