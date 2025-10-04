<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/myPage.css">

<%@ include file="/WEB-INF/view/include/header.jsp" %>

<div class="wrap">
    <main class="mypage_main">
        <div class="mypage_container">
            <h1 class="mypage_title">마이페이지</h1>
            
            <div class="user_info">
                <div class="profile_section">
                    <div class="profile_img">
                        <img src="/img/profile_default.png" alt="프로필 이미지">
                    </div>
                    <div class="user_details">
                        <h2>헬스장 회원</h2>
                        <p>회원 등급: 일반 회원</p>
                    </div>
                </div>
            </div>
            
            <div class="menu_section">
                <h3>내 정보</h3>
                <ul class="menu_list">
                    <li><a href="/profile">프로필 수정</a></li>
                    <li><a href="/change-password">비밀번호 변경</a></li>
                    <li><a href="/membership">멤버십 관리</a></li>
                </ul>
            </div>
            
            <div class="menu_section">
                <h3>헬스장 이용</h3>
                <ul class="menu_list">
                    <li><a href="/reservations">예약 내역</a></li>
                    <li><a href="/favorites">찜한 헬스장</a></li>
                    <li><a href="/reviews">내 리뷰</a></li>
                </ul>
            </div>
            
            <div class="menu_section">
                <h3>고객지원</h3>
                <ul class="menu_list">
                    <li><a href="/inquiry">문의하기</a></li>
                    <li><a href="/notice">공지사항</a></li>
                    <li><a href="/faq">자주 묻는 질문</a></li>
                </ul>
            </div>
            
            <div class="logout_section">
                <a href="/logout" class="logout_btn">로그아웃</a>
            </div>
        </div>
    </main>
</div>

<!-- 하단 메뉴 -->
<%@ include file="/WEB-INF/view/include/nav.jsp" %>

<!-- 푸터 -->
<%@ include file="/WEB-INF/view/include/footer.jsp" %>

</body>
</html>
