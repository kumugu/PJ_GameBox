<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>게임 관리 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_manageGames_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
<div class="form-container">
    <h1>게임 관리</h1>

    <!-- 관리자 경고 메시지 -->
    <c:if test="${not empty adminAlert}">
        <div class="alert">${adminAlert}</div>
    </c:if>

    <!-- 검색 기능 -->
    <form action="manage_games.do" method="get" class="search-form">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" placeholder="제목 또는 장르로 검색" required>
        <button type="submit">검색</button>
    </form>

    <!-- 게임 추가 버튼 -->
    <div class="button-container">
        <a href="manage_games.do?action=add_form" class="btn btn-add">게임 추가</a>
    </div>

    <!-- 게임 목록 테이블 -->
    <table class="game-table">
        <thead>
            <tr>
                <th>게임 ID</th>
                <th>제목</th>
                <th>장르</th>
                <th>개발자</th>
                <th>가격</th>
                <th>등록일</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="game" items="${gameList}">
                <tr>
                    <td>${game.gameId}</td>
                    <td>${game.title}</td>
                    <td>${game.genre}</td>
                    <td>${game.developer}</td>
                    <td>${game.price}</td>
                    <td>${game.releaseDate}</td>
                    <td><a href="manage_games.do?action=edit_form&gameId=${game.gameId}" class="btn btn-edit">수정</a></td>
					<td><a href="manage_games.do?action=delete&gameId=${game.gameId}" class="btn btn-delete" 
						onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a></td>
                           
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
