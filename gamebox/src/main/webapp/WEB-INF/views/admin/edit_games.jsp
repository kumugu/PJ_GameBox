<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>게임 수정 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/admin_manageGames_style.css">
</head>
<%@ include file="/WEB-INF/views/admin/admin_header.jsp" %>
<body>
    <div class="form-container">
        <h1>게임 수정</h1>

        <form action="manage_games.do" method="post">
            <!-- 동작 구분 -->
            <input type="hidden" name="action" value="edit">

            <!-- 게임 ID (수정 시 필요) -->
            <input type="hidden" name="gameId" value="${game.gameId}">

            <!-- 제목 입력 -->
            <div class="form-group">
                <label for="title">게임 제목:</label>
                <input type="text" id="title" name="title" value="${game.title}" placeholder="게임 제목을 입력하세요" required>
            </div>

            <!-- 설명 입력 -->
            <div class="form-group">
                <label for="description">게임 설명:</label>
                <textarea id="description" name="description" placeholder="게임 설명을 입력하세요">${game.description}</textarea>
            </div>

            <!-- 장르 선택 -->
            <div class="form-group">
                <label for="genre">장르:</label>
                <select id="genre" name="genre" required>
                    <option value="action" ${game.genre == 'action' ? 'selected' : ''}>액션</option>
                    <option value="puzzle" ${game.genre == 'puzzle' ? 'selected' : ''}>퍼즐</option>
                    <option value="adventure" ${game.genre == 'adventure' ? 'selected' : ''}>어드벤처</option>
                    <!-- 필요한 장르 추가 -->
                </select>
            </div>

            <!-- 등록일 -->
            <div class="form-group">
                <label for="releaseDate">등록일:</label>
                <input type="date" id="releaseDate" name="releaseDate" value="${game.releaseDate}" required>
            </div>

            <!-- 개발자 입력 -->
            <div class="form-group">
                <label for="developer">개발자(회사):</label>
                <input type="text" id="developer" name="developer" value="${game.developer}" placeholder="개발자 이름 또는 회사명을 입력하세요" required>
            </div>

            <!-- 가격 입력 -->
            <div class="form-group">
                <label for="price">가격:</label>
                <input type="number" id="price" name="price" value="${game.price}" placeholder="게임 가격을 입력하세요" step="0.01" required>
            </div>

            <!-- 버튼 -->
            <div class="buttons">
                <button type="button" class="btn btn-secondary" onclick="window.location.href='manage_games.do'">취소</button>
                <button type="submit" class="btn btn-primary">수정</button>
            </div>
        </form>
    </div>
</body>
</html>
