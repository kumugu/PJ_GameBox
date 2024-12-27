<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 수정 | GameBox</title>
        <link rel="stylesheet" href="./resources/css/admin_manageUsers_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
    <div class="form-container">
        <h1>회원 수정</h1>

        <form action="manage_users.do" method="post">
            <!-- 동작 구분 -->
            <input type="hidden" name="action" value="edit">

            <!-- 회원 ID (수정 시 필요) -->
            <input type="hidden" name="userId" value="${user.userId}">

            <!-- 이름 입력 -->
            <div class="form-group">
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" value="${user.name}" placeholder="회원 이름을 입력하세요" required>
            </div>

            <!-- 이메일 입력 -->
            <div class="form-group">
                <label for="email">이메일:</label>
                <input type="email" id="email" name="email" value="${user.email}" placeholder="example@email.com" required>
            </div>

            <!-- 역할 선택 -->
            <div class="form-group">
                <label for="role">역할:</label>
                <select id="role" name="role" required>
                    <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>일반 사용자</option>
                    <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>관리자</option>
                </select>
            </div>

            <!-- 버튼 -->
            <div class="buttons">
                <button type="button" class="btn btn-secondary" onclick="window.location.href='manage_users.do'">취소</button>
                <button type="submit" class="btn btn-primary">수정</button>
            </div>
        </form>
    </div>
</body>
</html>
