<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/login.css">

<%@ include file="/WEB-INF/view/include/header.jsp" %>

<div class="wrap">
    <main class="login_main">
        <div class="login_container">
            <h1 class="login_title">헬스장 로그인</h1>
            
            <form class="login_form" action="/login" method="post">
                <div class="input_group">
                    <label for="username">아이디</label>
                    <input type="text" id="username" name="username" placeholder="아이디를 입력하세요" required>
                </div>
                
                <div class="input_group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
                </div>
                
                <button type="submit" class="login_btn">로그인</button>
            </form>
            
            <div class="login_links">
                <a href="/register" class="register_link">회원가입</a>
                <a href="/find-password" class="find_password_link">비밀번호 찾기</a>
            </div>
            
            <div class="social_login">
                <p>간편 로그인</p>
                <div class="social_buttons">
                    <button class="social_btn kakao">
                        <img src="/img/kakao_login_btn.png" alt="카카오 로그인">
                    </button>
                    <button class="social_btn naver">
                        <img src="/img/naver_login_btn.png" alt="네이버 로그인">
                    </button>
                    <button class="social_btn google">
                        <img src="/img/google_login_btn.png" alt="구글 로그인">
                    </button>
                </div>
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
