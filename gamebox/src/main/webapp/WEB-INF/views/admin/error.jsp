<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>에러 발생 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_error_style.css">
    
<style type="text/css">
    
    .error-container {
    text-align: center;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #f44336;
    background-color: #ffebee;
    color: #d32f2f;
    border-radius: 5px;
    width: 50%;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.error-container h1 {
    font-size: 24px;
    margin-bottom: 10px;
}

.error-container p {
    font-size: 16px;
    margin: 10px 0;
}

.error-container .buttons {
    margin-top: 20px;
}

.error-container .buttons button,
.error-container .buttons .btn-home {
    display: inline-block;
    padding: 10px 20px;
    font-size: 14px;
    color: #fff;
    background-color: #d32f2f;
    text-decoration: none;
    border: none;
    border-radius: 3px;
    cursor: pointer;
}

.error-container .buttons button:hover,
.error-container .buttons .btn-home:hover {
    background-color: #b71c1c;
}
</style>
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
    <div class="error-container">
        <h1>오류가 발생했습니다</h1>
        <p>
            <c:if test="${not empty errorMessage}">
                <strong>${errorMessage}</strong>
            </c:if>
        </p>
        <p>문제가 지속될 경우 관리자에게 문의하세요.</p>
        <div class="buttons">
            <button onclick="history.back()">이전 페이지로</button>
            <a href="admin_home.do" class="btn btn-home">홈으로</a>
        </div>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
