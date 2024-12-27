<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 추가 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_manageUsers_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
    <div class="form-container">
        <h1>회원 추가</h1>

        <form action="manage_users.do" method="post">
            <input type="hidden" name="action" value="add">

            <!-- 이름 입력 -->
            <div class="form-group">
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" placeholder="회원 이름을 입력하세요" required>
            </div>

            <!-- 이메일 입력 -->
            <div class="form-group">
                <label for="email">이메일:</label>
                <input type="email" id="email" name="email" placeholder="example@email.com" required>
            </div>

            <!-- 비밀번호 입력 -->
            <div class="form-group">
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
            </div>

            <!-- 역할 선택 -->
            <div class="form-group">
                <label for="role">역할:</label>
                <select id="role" name="role" required>
                    <option value="USER">일반 사용자</option>
                    <option value="ADMIN">관리자</option>
                </select>
            </div>

            <!-- 버튼 -->
            <div class="buttons">
                <button type="button" class="btn btn-secondary" onclick="window.location.href='manage_users.do'">취소</button>
                <button type="submit" class="btn btn-primary">추가</button>
            </div>
        </form>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
