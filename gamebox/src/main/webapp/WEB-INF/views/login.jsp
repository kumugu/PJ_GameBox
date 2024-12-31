<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <title>Login</title>
    <link rel="stylesheet" href="./resources/css/login_singup_style.css">
    <link rel="stylesheet" href="./resources/css/style.css">
</head>

 <%@ include file="/WEB-INF/views/common/header.jsp" %>
<body>
    <div class="login-container">
        <h1>로그인</h1>

        <form action="login.do" method="post" class="login-form">
            <div class="form-group">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary">로그인</button>
        </form>

        <div class="signup-link">
            계정이 없으신가요? <a href="signup.do">회원가입</a>
        </div>
    </div>
</body>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

</html>
