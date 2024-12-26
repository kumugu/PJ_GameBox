<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 추가 | GameBox</title>
    <link rel="stylesheet" href="/resources/css/admin_user_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
    <h1>회원 추가</h1>

	<form action="manage_users.do" method="post">
        <input type="hidden" name="action" value="add">

        <div class="form-group">
            <label for="name">이름:</label>
            <input type="text" id="name" name="name" required>
        </div>

        <div class="form-group">
            <label for="email">이메일:</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="role">역할:</label>
            <select id="role" name="role" required>
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">추가</button>
    </form>
</body>
</html>
