<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>  
 <div id="wrap">
    <nav>
    	<c:set var="info" value="${store.storeInfo }" />
        <h1 id="store_name" data-store_name="${info.storeName }" >${info.storeName }</h1>
        <%-- <div id="is_open" data-is_open="${store.storeInfo.isOpen }"></div> --%>
		<div class="inf">
			<div>
				<%-- 
				<span class="score_box">
             		<c:forEach begin="0" end="4" var="i">
             			<c:choose>
	           				<c:when test="${Math.round(info.score) > i }">
		                   		<i class="far fas fa-star"></i>
		                   	</c:when>
		                   	<c:otherwise>
		                   		<i class="far fa-star"></i>
		                   	</c:otherwise>
             			</c:choose> 
             		</c:forEach>
                  		
                  	<span class="store_score" data-score="${info.score }">${info.score }</span>
                  	
				</span><br>
                 --%>
                   
           		<span><i class="fas fa-heart" ></i> 찜 </span>
                   	
                    
                <span class="likes_count" data-count=0 >0</span>
                  
			</div>
               	<div>
               		<span class="store_review_count" data-review_count="0"> 리뷰 0</span>
               		<span>사장님 댓글 0</span>
            	</div>
                
               	<div id="min_delevery" data-min_delevery="${info.minDelevery }">최소주문금액 <fm:formatNumber value="${info.minDelevery }" pattern="###,###" />원</div>
               	<div>예상 배달시간 ${info.deleveryTime  }분</div>
               	<div id="delevery_tip" data-delevery_tip="${info.deleveryTip }">배달팁 <fm:formatNumber value="${info.deleveryTip }" pattern="###,###" />원</div>
		</div>
    </nav>


	<!-- 모바일 카트 -->
   	<div class="m_cart_img">
   		<div class="m_cart_img_box">
   			<i class="fas fa-shopping-cart"></i>
   			<span class="m_cart_count"></span>
   		</div>
   	</div>
  	<!-- 모바일 카트 -->
    
    
    
	<!-- 장바구니 -->    
    <aside id="cart">
        <div class="cart">	
            <h2>장바구니</h2>
            <i class="far fa-trash-alt deleteAll" ></i>
            
            <div class="cart_list">
	            <ul>
	            	<!-- 
					<li>
						<h3>메뉴</h3>
  							<div>가격</div>
  							<div>수량 : 0 </div>
  							<div> 옵션  </div>
  							<div>합계 : 0원</div>
  							<button class="cancle_btn"> ｘ </button> 
		            </li>
  							-->
	            </ul>
            </div>
            
            <div class="order_btn_box">
            	<div class="total">장바구니가 비었습니다.</div>
	            <button class="order_btn" disabled>주문하기</button>
            </div>
        </div>
        
    </aside>
    <div class="alarm">장바구니에 담았습니다</div>
	<!-- 장바구니 -->    
	   

	<main>
		<div class="offset"></div>
        <ul class="tab ">
            <li class="select">메뉴</li>
            <li>정보</li>
            <li>리뷰</li>
        </ul>
	
			
		<!-- 메뉴 탭 -->	
        <ul class="menu">
        
        
        
        </ul>
		<!-- 메뉴 탭 -->	
		
		
		<!-- 정보 탭 -->
	    <ul class="info" >
	    
	    
	    
	       
	    </ul>
		<!-- 메뉴 탭 -->    
	
		
		
		<!-- 리뷰 탭 -->        
		<ul class="comment" >
	    
	    
	    
	        
		</ul>
	</main>
</div>

	
	<input type="hidden" value="${info.id }" id="store_id">
	<input type="hidden" value="${info.category }" id="store_category">  
	<input type="hidden" value="${info.openingTime }" id="store_opening_time"> 
	<input type="hidden" value="${info.closingTime }" id="store_closing_time"> 
	
	<input type="hidden" value="${BMaddress.address2 }" id="delevery_address">
	
	
	
	
	
	


 