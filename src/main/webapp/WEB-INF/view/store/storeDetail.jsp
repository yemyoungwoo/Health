<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<div id="wrap">
    <nav>
        <c:set var="info" value="${store.storeInfo }" />
        <h1 id="store_name" data-store_name="${info.storeName }" >${info.storeName }</h1>
        <div class="inf">
            <div>
                <span><i class="fas fa-heart" ></i> 찜 </span>
                <span class="likes_count" data-count=0 >0</span>
            </div>
            <div>
                <span class="store_review_count" data-review_count="0"> 리뷰 0 </span>
                <span>사장님 댓글 0</span>
            </div>
            <div id="min_delevery" data-min_delevery="${info.minDelevery }">
                하루 이용권 <fm:formatNumber value="${info.minDelevery }" pattern="###,###" />원
            </div>
        </div>
    </nav>

    <!-- 장바구니 -->    
    <aside id="cart">
        <div class="cart">	
            <h2>장바구니</h2>
            <i class="far fa-trash-alt deleteAll" ></i>
            <div class="cart_list">
                <ul></ul>
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
            <c:forEach items="${store.foodList }" var="foodList" >
	            <li>
	                <div class="menu_box">
	                    <div>
							<h2>${foodList.foodName } </h2>
		                    
   		                    <fm:formatNumber value="${foodList.foodPrice }" pattern="###,###" />원 
		                    <input type="hidden" value="${foodList.storeId }" name="storeId" >
				            <input type="hidden" value="${foodList.id }" name="foodId" class="food_id"   >
				            <input type="hidden" value="${foodList.foodName }" name="foodName" class="food_name" >
				            <input type="hidden" value="${foodList.foodPrice }" name="foodPrice" class="food_price"   >
				            <input type="hidden" value="${foodList.foodDec }" name="foodDec" class="food_dec"   >
				            <input type="hidden" value="${foodList.foodImg }" name="foodImg" class="food_img"   >
				            <input type="hidden" value="${foodList.foodThumb }" name="foodThumb" class="food_thumb"   >
		                </div>
		                
                    	<div><img src="${foodList.foodImg }" alt="이미지"></div>
                    </div>
	             </li>
	        </c:forEach>
        </ul>
        <!-- 메뉴 탭 -->

        <!-- 정보 탭 -->
        <ul class="info" ></ul>
        <!-- 정보 탭 -->

        <!-- 리뷰 탭 -->        
        <ul class="comment" ></ul>
        <!-- 리뷰 탭 -->
    </main>
</div>

<input type="hidden" value="${info.id }" id="store_id">
<input type="hidden" value="${info.category }" id="store_category">  
<input type="hidden" value="${info.openingTime }" id="store_opening_time"> 
<input type="hidden" value="${info.closingTime }" id="store_closing_time"> 
<input type="hidden" value="${BMaddress.address2 }" id="delevery_address">