<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>

<link rel="stylesheet" href="/css/user/login.css">
</head>
<body>
    <main>
        <div class="login_box">
			<a href="/"><img src="/img/로고.png" alt="이미지" ></a>    
            
            <h2 style="margin: 20px 0; font-size: 20px;">아이디 찾기</h2>
            
            <form id="findUsernameForm">
	            <div class="input_aera">
	            	<input type="email" name="email" required placeholder="이메일을 입력해 주세요" maxlength="50">
	            </div>

				<button type="submit" class="login_btn">아이디 찾기</button>
            </form>
            
			<div class="join">
				<a href="/login">로그인하러 가기</a>
			</div>
        </div>
    </main>
    
    <script>
    $(document).ready(function() {
    	$("#findUsernameForm").submit(function(e) {
    		e.preventDefault();
    		
    		const email = $("input[name='email']").val();
    		
    		$.ajax({
    			url: "/findUsername",
    			type: "POST",
    			data: { email: email }
    		})
    		.done(function(result) {
    			swal({
    				title: "성공",
    				text: result,
    				icon: "success"
    			}).then(function() {
    				location.href = "/login";
    			});
    		})
    		.fail(function(xhr) {
    			swal({
    				title: "실패",
    				text: xhr.responseText || "오류가 발생했습니다.",
    				icon: "error"
    			});
    		});
    	});
    });
    </script>
    
</body>
</html>

