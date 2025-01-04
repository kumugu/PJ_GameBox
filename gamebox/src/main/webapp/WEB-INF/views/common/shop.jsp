<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Shop | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/shop_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="games" align="center">
    <img src="./resources/image/game1.jpg" alt="Game 1: God of War">
    <img src="./resources/image/game2.jpg" alt="Game 2: Metaphor">
    <img src="./resources/image/game3.jpg" alt="Game 3: Satisfactory">
    <img src="./resources/image/game4.jpg" alt="Game 4: Forza Horizon 5">
    <img src="./resources/image/game5.jpg" alt="Game 5: Ghost of Tsushima">
    <img src="./resources/image/game6.jpg" alt="Game 6: Stardew Valley">
</div>

<div class="game-list">
    <table align="center" class="game-table">
        <thead>
            <tr>
                <th>이미지</th>
                <th>게임 제목</th>
                <th>장르</th>
                <th>출시일</th>
                <th>평점</th>
                <th>가격</th>
                <th>장바구니</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="game" items="${gameList}">
				<tr class="game-row" onclick="window.location.href='${pageContext.request.contextPath}/gameDetail.do?gameId=${game.gameId}'">
				    <td>
				        <img src="${pageContext.request.contextPath}${game.imagePath}" alt="${game.title}" class="game-image">
				    </td>
				    <td>${game.title}</td>
				    <td>${game.genre}</td>
				    <td>${game.releaseDate}</td>
				    <td>${game.rating}</td>
				    <td>₩${game.price}</td>
				    <td>
					  	<button class="cart-btn" onclick="addToCart('${game.gameId}'); event.stopPropagation();">장바구니에 추가</button>
					</td>

				</tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<script>
	function addToCart(gameId) {
	    event.preventDefault();
	    event.stopPropagation();
	    
	    // 현재 페이지의 컨텍스트 경로를 동적으로 가져옴
	    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));
	    
	    console.log('Sending gameId:', gameId); // 디버깅용
	    
	    fetch(contextPath + '/addToCart.do', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/x-www-form-urlencoded',
	        },
	        body: 'gameId=' + gameId
	    })
	    .then(response => {
	        console.log('Response status:', response.status);
	        if (!response.ok) {
	            throw new Error(`HTTP error ${response.status}`);
	        }
	        return response.json();
	    })
	    .then(data => {
	        console.log('Response data:', data);
	        if (data.success) {
	            alert(data.message || "장바구니에 추가되었습니다!");
	        } else {
	            alert(data.message || "장바구니 추가 중 문제가 발생했습니다.");
	        }
	    })
	    .catch(error => {
	        console.error("AJAX 오류:", error);
	        alert("서버와 통신 중 문제가 발생했습니다.");
	    });
	}

</script>
</body>
</html>
