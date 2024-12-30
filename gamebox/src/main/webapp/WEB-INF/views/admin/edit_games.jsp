<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <form action="manage_games.do?action=edit" method="post" enctype="multipart/form-data">
            <input type="hidden" name="gameId" value="${game.gameId}">

            <!-- 게임 제목 입력 -->
            <label for="title">게임 제목:</label>
            <input type="text" id="title" name="title" value="${game.title}" required placeholder="게임 제목을 입력하세요.">

            <!-- 대표 이미지 업로드 -->
            <label for="image">대표 이미지:</label>
            <input type="file" id="image" name="image" accept="image/*">

            <!-- 게임 설명 -->
            <label for="description">게임 설명:</label>
            <textarea id="description" name="description" placeholder="게임 설명을 입력하세요.">${game.description}</textarea>

            <!-- 장르 선택 -->
            <label for="genre">장르:</label>
            <select id="genre" name="genre" required>
                <option value="" disabled>장르를 선택하세요</option>
                <option value="action" ${game.genre == 'action' ? 'selected' : ''}>액션</option>
                <option value="puzzle" ${game.genre == 'puzzle' ? 'selected' : ''}>퍼즐</option>
                <option value="adventure" ${game.genre == 'adventure' ? 'selected' : ''}>어드벤처</option>
                <option value="rpg" ${game.genre == 'rpg' ? 'selected' : ''}>RPG</option>
                <option value="simulation" ${game.genre == 'simulation' ? 'selected' : ''}>시뮬레이션</option>
            </select>

            <!-- 평점 입력 -->
            <label for="rating">평점:</label>
            <input type="number" id="rating" name="rating" value="${game.rating}" step="0.1" min="0.0" max="10.0" required>

            <!-- 출시일 입력 -->
            <label for="releaseDate">출시일:</label>
            <input type="date" id="releaseDate" name="releaseDate" value="${game.releaseDate}" required>

            <!-- 개발자 정보 -->
            <label for="developer">개발자(회사):</label>
            <input type="text" id="developer" name="developer" value="${game.developer}" required placeholder="개발자 또는 개발사를 입력하세요.">

            <!-- 가격 입력 -->
            <label for="price">가격:</label>
            <input type="number" id="price" name="price" value="${game.price}" step="0.01" min="0" required placeholder="게임 가격을 입력하세요.">

            <!-- 동영상 URL -->
            <label for="videoUrl">동영상 URL:</label>
            <input type="url" id="videoUrl" name="videoUrl" value="${game.videoUrl}" placeholder="동영상 URL을 입력하세요.">

            <!-- 리뷰 요약 -->
            <label for="reviewSummary">리뷰 요약:</label>
            <textarea id="reviewSummary" name="reviewSummary" placeholder="리뷰 요약을 입력하세요.">${game.reviewSummary}</textarea>

            <!-- 최소 시스템 요구 사항 -->
            <label for="minRequirements">최소 시스템 요구 사항:</label>
            <textarea id="minRequirements" name="minRequirements" placeholder="최소 요구 사항을 입력하세요.">${game.minRequirements}</textarea>

            <!-- 권장 시스템 요구 사항 -->
            <label for="recRequirements">권장 시스템 요구 사항:</label>
            <textarea id="recRequirements" name="recRequirements" placeholder="권장 요구 사항을 입력하세요.">${game.recRequirements}</textarea>

            <!-- 버튼 -->
            <div class="buttons">
                <button type="button" onclick="window.location.href='manage_games.do'">취소</button>
                <button type="submit">수정</button>
            </div>
        </form>
    </div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
