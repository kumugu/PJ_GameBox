<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${param.action == 'add' ? '회원 추가' : '회원 수정'} | GameBox</title>
    <link rel="stylesheet" href="/resources/css/admin_user_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
    <h1>${param.action == 'add' ? '회원 추가' : '회원 수정'}</h1>

    <form action="manage_users.do" method="post">
        <!-- 동작 구분 -->
        <input type="hidden" name="action" value="${param.action}">

        <!-- 회원 ID (수정 시 필요) -->
        <c:if test="${param.action == 'edit'}">
            <input type="hidden" name="userId" value="${user.userId}">
        </c:if>

        <div class="form-group">
            <label for="name">이름:</label>
            <input type="text" id="name" name="name" value="${user.name}" required>
        </div>

        <div class="form-group">
            <label for="email">이메일:</label>
            <input type="email" id="email" name="email" value="${user.email}" required>
        </div>

        <div class="form-group">
            <label for="role">역할:</label>
            <select id="role" name="role" required>
                <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
                <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">${param.action == 'add' ? '추가' : '수정'}</button>
    </form>
</body>
</html>
