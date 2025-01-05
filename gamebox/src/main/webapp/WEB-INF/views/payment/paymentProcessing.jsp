<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Payment | GameBox</title>
    <link rel="stylesheet" href="./resources/css/payment_style.css">
    <link rel="stylesheet" href="./resources/css/style.css">
</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

    <h1>결제 진행 중</h1>
    
    <div class="payment-container">

        <!-- 결제 정보 -->
        
        <div class="payment-details">
            <p>결제 금액: <span class="total-price">￦${totalPrice}</span></p>
            <!-- hidden input for totalAmount -->
            <input type="hidden" id="totalAmount" value="${totalPrice}" />
        </div>

		<!-- 결제 버튼 -->
		<div class="payment-actions">
		    <!-- 카카오페이 결제 버튼 -->
		    <button onclick="requestPayment()" class="kakao-pay-button">카카오페이 결제</button>
		    <form action="viewCart.do" method="get" class="cancel-form">
		    <br>
		        <button type="submit" class="btn btn-secondary">결제 취소</button>
		    </form>
		</div>

    </div>

    <!-- 아임포트 스크립트 -->
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script>
    function requestPayment() {
        const IMP = window.IMP; // 아임포트 객체 초기화
        IMP.init("imp01157832"); // 아임포트 고객사 식별코드

        IMP.request_pay({
            pg: "kakaopay", // 결제창에 표시할 PG사 (카카오페이)
            pay_method: "card", // 결제 수단
            merchant_uid: "order_" + new Date().getTime(), // 주문번호 (고유 값)
            name: "GameBox 상품 결제", // 결제 상품명
            amount: document.getElementById("totalAmount").value, // 결제 금액
            buyer_email: "user@example.com", // 구매자 이메일
            buyer_name: "홍길동", // 구매자 이름
            buyer_tel: "010-1234-5678", // 구매자 전화번호
        }, function (rsp) {
            if (rsp.success) {
                // 결제 성공 시 바로 성공 페이지로 이동
                window.location.href = "<%= request.getContextPath() %>/paymentSuccess.do";
            } else {
                alert("결제 실패: " + rsp.error_msg);
            }
        });
    }
    </script>
</body>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</html>
