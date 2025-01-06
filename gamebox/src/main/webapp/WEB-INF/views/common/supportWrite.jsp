<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>문의 작성 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/support_write_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="support-container">
    <h1>문의 작성</h1>

    <!-- 문의 작성 폼 -->
    <form action="${pageContext.request.contextPath}/support.do?action=submit" method="post">
        <div class="form-group">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" required>
        </div>
        
        <div class="form-group">
            <label for="content">내용:</label>
            <textarea id="content" name="content" rows="10" required></textarea>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">제출</button>
            <a href="${pageContext.request.contextPath}/support.do?action=list" class="btn btn-secondary">취소</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
