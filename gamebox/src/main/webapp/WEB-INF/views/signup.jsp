<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
 <%@ include file="/WEB-INF/views/common/header.jsp" %>
<head>
    <title>Signup</title>
    <link rel="stylesheet" href="./resources/css/login_singup_style.css">
    <link rel="stylesheet" href="./resources/css/style.css">
</head>
<body>
	<div class="signup-container">
        <h1>회원가입</h1>
        
	    <form action="signup.do" method="post" class="login-form">
	    	<div class="form-group">
		        <label for="userId">User ID:</label>
		        <input type="text" id="userId" name="userId" required><br>
			</div>
			<div class="form-group">
		        <label for="password">Password:</label>
		        <input type="password" id="password" name="password" required><br>
			</div>
			<div class="form-group">
		        <label for="name">Name:</label>
		        <input type="text" id="name" name="name" required><br>
			</div>
			<div class="form-group">
	        	<label for="email">Email:</label>
	        	<input type="email" id="email" name="email" required><br>
			</div>
	        <button type="submit" class="btn btn-primary">Signup</button>
	    </form>
    </div>
</body>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</html>
