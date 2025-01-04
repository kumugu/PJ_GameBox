<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Shop | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/shop_style.css">
    <style>
        /* 모달 스타일 */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background-color: #2c3e50;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 30%;
            border-radius: 10px;
            color: white;
            text-align: center;
        }

        .modal-content p {
            margin-bottom: 20px;
        }

        .modal-content button {
            margin: 5px;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .modal-content .cart-btn {
            background-color: #4caf50;
            color: white;
        }

        .modal-content .continue-btn {
            background-color: #007bff;
            color: white;
        }

        .modal-content button:hover {
            background-color: #0056b3;
        }
    </style>
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

<!-- 모달 구조 -->
<div id="cart-modal" class="modal">
    <div class="modal-content">
        <p id="cart-modal-message">장바구니에 추가되었습니다!</p>
        <button class="cart-btn" onclick="goToCart()">장바구니로 이동</button>
        <button class="continue-btn" onclick="closeModal()">계속 쇼핑</button>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<script>
	function addToCart(gameId) {
	    event.preventDefault();
	    event.stopPropagation();
	
	    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));
	
	    console.log('Sending gameId:', gameId);
	
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
	            const modal = document.getElementById("cart-modal");
	            const message = document.getElementById("cart-modal-message");
	            message.innerText = data.message || "장바구니에 추가되었습니다!";
	            modal.style.display = "block"; // 모달 표시
	        } else {
	            alert(data.message || "장바구니 추가 중 문제가 발생했습니다.");
	        }
	    })
	    .catch(error => {
	        console.error("AJAX 오류:", error);
	        alert("서버와 통신 중 문제가 발생했습니다.");
	    });
	}
	
	function closeModal() {
	    const modal = document.getElementById("cart-modal");
	    modal.style.display = "none"; // "계속 쇼핑" 버튼 클릭 시 모달 닫기
	}
	
	function goToCart() {
	    const modal = document.getElementById("cart-modal");
	    modal.style.display = "none"; // "장바구니 이동" 버튼 클릭 시 모달 닫기
	    
	    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));
	    window.location.href = contextPath + '/viewCart.do'; // 장바구니 페이지로 이동
	}


</script>
</body>
</html>
