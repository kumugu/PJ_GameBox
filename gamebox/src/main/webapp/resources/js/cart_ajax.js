
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

