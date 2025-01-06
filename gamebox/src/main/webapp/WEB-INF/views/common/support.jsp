<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>지원 | GameBox</title>
    <link rel="stylesheet" href="./resources/css/style.css">
    <link rel="stylesheet" href="./resources/css/support_style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="support-container">
    <h1>고객센터</h1>
    
    <!-- 공지사항 -->
    <div class="notice">
        <p>[공지] 서비스 관련 안내: 예시 공지가 여기에 표시됩니다.</p>
    </div>

    <!-- 카테고리 선택 버튼 -->
    <div class="categories">
        <button onclick="filterFAQ('all')">전체</button>
        <button onclick="filterFAQ('계정 관리')">계정 관리</button>
        <button onclick="filterFAQ('결제')">결제</button>
        <button onclick="filterFAQ('검색')">검색</button>
        <button onclick="filterFAQ('기타')">기타</button>
    </div>

	<!-- 자주하는 질문 섹션 -->
	<div class="faq-section">
	    <h2>자주하는 질문</h2>
	    <c:choose>
	        <c:when test="${not empty faqs}">
	            <div class="faq-list">
	                <c:forEach var="faq" items="${faqs}" varStatus="status">
	                    <div class="faq-item" data-category="${faq.category}">
	                        <div class="faq-question" onclick="toggleAnswer(${status.index})">
	                            <span class="category-badge">${faq.category}</span>
	                            ${faq.question}
	                        </div>
	                        <div class="faq-answer" id="answer-${status.index}">
	                            ${faq.answer}
	                        </div>
	                    </div>
	                </c:forEach>
	            </div>
	        </c:when>
	        <c:otherwise>
	            <div class="no-data">
	                <p>현재 등록된 FAQ가 없습니다.</p>
	            </div>
	        </c:otherwise>
	    </c:choose>
	</div>
	
    <!-- 문의 작성 버튼 -->
    <div class="actions">
        <a href="${pageContext.request.contextPath}/support.do?action=write" class="btn btn-primary">문의 작성하기</a>
    </div>
    
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>
