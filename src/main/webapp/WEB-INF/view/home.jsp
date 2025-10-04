<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/home.css">
 
<%@ include file="/WEB-INF/view/include/header.jsp" %>
 	<!-- 콘텐츠 -->
    <div class="wrap">
        <main>
            <section class="address_search">
                 <div id="search_box">
                     <div>
                         <input type="hidden" id="deleveryAddress1" placeholder="우편번호" value="${BMaddress.address1 }" name="address1" readonly>
                         <input type="text" value="${BMaddress.address2 }" onclick ="modifyAddress()"
                             id="deleveryAddress2" readonly placeholder="주소를 입력해 주세요" name="address2"><br>
                     </div>
 
                     <div class="search_btn">
                         <label for="search_btn">
                             <i class="fas fa-search"></i>
                         </label>
                         <input type="button" name="search" id="search_btn">
 
                     </div>
 					<%@ include file= "/WEB-INF/view/include/modifyAddress.jsp" %>
                 </div>
            </section>
            <section class="category_box">
                <div class="box">
                    <ul class="category">
                    
                                <li>
									<div>
										<div class="img_box">
                                           <img src="/img/파프리카GYM.png" alt="이미지">
										</div>
                                    </div>
                                    <div class="name">파프리카짐</div>
                                </li>
                    
                                <li>
									<div>
										<div class="img_box">
                                           <img src="/img/스포애니.png" alt="이미지">
										</div>
                                    </div>
                                    <div class="name">스포애니</div>
                                </li>
                    
                                <li>
									<div>
										<div class="img_box">
                                           <img src="/img/고투.png" alt="이미지">
										</div>
                                    </div>
                                    <div class="name">고투</div>
                                </li>
                    
                                <li>
									<div>
										<div class="img_box">
                                           <img src="/img/바디스펙.png" alt="이미지">
										</div>
                                    </div>
                                    <div class="name">바디스펙</div>
                                </li>
                                
                    
                                <li>
									<div>
										<div class="img_box">
                                           <img src="/img/헬스보이.png" alt="이미지">
										</div>
                                    </div>
                                    <div class="name">헬스보이</div>
                                </li>
                                
                            </ul>
                	</div>
            </section>
        </main>
    </div>
    <!-- 콘텐츠 -->
 
 
    <!-- 하단 메뉴 -->
	<%@ include file="/WEB-INF/view/include/nav.jsp" %>
    <!-- 하단 메뉴 -->
 
    <!-- 푸터 -->
    <%@ include file="/WEB-INF/view//include/footer.jsp" %>
    <!-- 푸터 -->
 
<script>
	// 주소 검색 버튼 클릭 이벤트
	$("#search_btn").click(function(){
		// 다음 우편번호 API 사용
		new daum.Postcode({
			oncomplete: function(data) {
				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('Address1').value = data.zonecode;
				document.getElementById('Address2').value = data.address;
			}
		}).open();
	});

	$(".category li").click(function(){
		let address1 = $("#Address1").val();
		if(!address1) {
			swal("헬스장 위치를 선택해 주세요");
			return false;
		}
		
		const index = $(this).index();
		
		location.href = "/store/" + (100+index) + "/" +address1;
	})

</script>
 
 
</body>
 
</html>