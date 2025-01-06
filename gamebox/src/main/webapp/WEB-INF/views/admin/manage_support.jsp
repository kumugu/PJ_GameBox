<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>문의 관리 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_manageCommunity_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>

<div class="admin-container">
    <h1>문의 관리</h1>

    <c:if test="${not empty supportList}">
        <table>
            <thead>
                <tr>
                    <th>문의 ID</th>
                    <th>작성자</th>
                    <th>제목</th>
                    <th>상태</th>
                    <th>작성일</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="support" items="${supportList}">
                    <tr>
                        <td>${support.supportId}</td>
                        <td>${support.userName}</td>
                        <td>${support.title}</td>
                        <td>${support.status}</td>
                        <td><fmt:formatDate value="${support.createdAt}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <form action="manage_support.do" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="reply">
                                <input type="hidden" name="supportId" value="${support.supportId}">
                                <input type="text" name="reply" placeholder="답변 작성" required>
                                <button type="submit" class="btn btn-primary">답변 등록</button>
                            </form>
                            <a href="manage_support.do?action=resolve&supportId=${support.supportId}" class="btn btn-success">해결</a>
                            <a href="manage_support.do?action=delete&supportId=${support.supportId}" class="btn btn-danger">삭제</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty supportList}">
        <p>등록된 문의가 없습니다.</p>
    </c:if>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
