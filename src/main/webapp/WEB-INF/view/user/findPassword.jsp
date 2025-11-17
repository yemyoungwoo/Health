<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>

<link rel="stylesheet" href="/css/user/login.css">
</head>
<body>
    <main>
        <div class="login_box">
			<a href="/"><img src="/img/로고.png" alt="이미지" ></a>    
            
            <h2 style="margin: 20px 0; font-size: 20px;">비밀번호 찾기</h2>
            
            <form id="findPasswordForm">
	            <div class="input_aera">
	            	<input type="text" name="username" required placeholder="아이디를 입력해 주세요" maxlength="30">
	            </div>
	            
	            <div class="input_aera">
	            	<input type="email" name="email" required placeholder="이메일을 입력해 주세요" maxlength="50">
	            </div>

				<button type="submit" class="login_btn">임시 비밀번호 발급</button>
            </form>
            
			<div class="join">
				<a href="/login">로그인하러 가기</a>
			</div>
        </div>
    </main>
    
    <script>
    $(document).ready(function() {
    	$("#findPasswordForm").submit(function(e) {
    		e.preventDefault();
    		
    		const username = $("input[name='username']").val();
    		const email = $("input[name='email']").val();
    		
    		$.ajax({
    			url: "/findPassword",
    			type: "POST",
    			data: { 
    				username: username,
    				email: email
    			}
    		})
    		.done(function(result) {
    			swal({
    				title: "성공",
    				text: result + "\n이메일로 임시 비밀번호가 전송되었습니다.\n로그인 후 비밀번호를 변경해주세요.",
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

