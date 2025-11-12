<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="wrap">
	<nav>
		<c:set var="info" value="${store.storeInfo }" />
		<h1 id="store_name" data-store_name="${info.storeName }">${info.storeName }</h1>
		<div class="inf">
			<div>
				<span><i class="fas fa-heart"></i> 찜 </span> <span
					class="likes_count" data-count=0>0</span>
			</div>
			<div>
				<span class="store_review_count" data-review_count="0"> 리뷰 0
				</span> <span>사장님 댓글 0</span>
			</div>
			<div id="min_delevery" data-min_delevery="${info.minDelevery }">
				하루 이용권
				<fm:formatNumber value="${info.minDelevery }" pattern="###,###" />
				원
			</div>
			<div id="delevery_tip" data-delevery_tip="${info.deleveryTip }" style="display:none;"></div>
		</div>
	</nav>

	<!-- 장바구니 -->
	<aside id="cart">
		<div class="cart">
			<h2>장바구니</h2>
			<i class="far fa-trash-alt deleteAll"></i>
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
			<c:forEach items="${store.foodList }" var="foodList">
				<li>
					<div class="menu_box">
						<div>
							<h2>${foodList.foodName }</h2>

							<fm:formatNumber value="${foodList.foodPrice }" pattern="###,###" />원 
								<input type="hidden" value="${foodList.storeId }"name="storeId"> 
								<input type="hidden" value="${foodList.id }" name="foodId" class="food_id"> 
								<input type="hidden" value="${foodList.foodName }" name="foodName" class="food_name"> 
								<input type="hidden"value="${foodList.foodPrice }" name="foodPrice" class="food_price"> 
								<input type="hidden" value="${foodList.foodDec }" name="foodDec" class="food_dec">
								<input type="hidden" value="${foodList.foodImg }" name="foodImg"class="food_img"> 
								<input type="hidden" value="${foodList.foodThumb }" name="foodThumb" class="food_thumb">
						</div>

						<div>
							<img src="${foodList.foodImg }" alt="이미지">
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
		<!-- 메뉴 탭 -->

	<!-- 정보 탭 -->
	    <ul class="info" >
	    	
	    	<li>
    			<div>
        			<h2>찾아 오시는 길</h2>
        
        		<div id="map_box">
            	<div id="map"></div>
            
            	<div id="position_box">
                	<button class="storePosition" ><i class="far fa-dot-circle"></i> 가게 위치로</button>
                	<button class="userPosition"> <i class="far fa-dot-circle"></i> 내 위치로</button>
            	</div>
        	</div>
        
        	<h2>위치안내</h2>
        	<div id="store_address" data-address="${info.storeAddress2 }">${info.storeAddress2 }  ${info.storeAddress3 }</div>
    	</div>
	</li>

		<li>
    		<div>
       			<h2>가게 소개</h2>
       			<div>${info.storeDes }</div>
   			</div>
		</li>

		<li>
    		<div>
        		<h2>영업 정보</h2>
        
		        <div class="info_detail_title">
		            <div>상호명</div>
		            <div>영업시간</div>
		            <div>전화번호</div>
		        </div>
        
        <div class="info_detail">
            <div>${info.storeName }</div>
            <div>
                <span><fm:formatNumber value="${info.openingTime }" minIntegerDigits="2" />시 ~</span>
                <span><fm:formatNumber value="${info.closingTime }" minIntegerDigits="2" />시 </span>
            </div>
            <div>${info.storePhone }</div>
            
        </div>
    </div>
</li>

<li>
    <div>
        <h2>가계 통계</h2>
        <div class="info_detail_title">
            <div>최근 주문수</div>
            <div>전체 리뷰 수</div>
            <div>찜</div>
        </div>
        
        <div class="info_detail">
            <%-- 
            <div>${info.orderCount }</div>
            <div>${info.reviewCount }</div>
            <div>${info.likesCount }</div> 
            --%>
        </div>
    </div>	
</li>
	    </ul>

		<!-- 리뷰 탭 -->
		<ul class="comment"></ul>
		<!-- 리뷰 탭 -->
	</main>
</div>

<input type="hidden" value="${info.id }" id="store_id">
<input type="hidden" value="${info.category }" id="store_category">
<input type="hidden" value="${info.openingTime }"id="store_opening_time">
<input type="hidden" value="${info.closingTime }"id="store_closing_time">
<input type="hidden" value="${BMaddress.address2 }"id="delevery_address">
	
	
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=dd2ab2afa0a56cadfcc1579a01196ffd&libraries=services"></script>
<script>
$(document).ready(function(){
    var map; // 전역 변수로 선언
    
    function initMap() {
        var storeAddress = $("#store_address").data("address");
        var storeName = $("#store_name").data("store_name");
        
        var mapContainer = document.getElementById('map');
        
        var mapOption = {
        	    center: new kakao.maps.LatLng(36.3504, 127.3845), // 대전 좌표
        	    level: 3
        	};
        
        
        map = new kakao.maps.Map(mapContainer, mapOption); 
        var geocoder = new kakao.maps.services.Geocoder();
        
        geocoder.addressSearch(storeAddress, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });
                
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:3px 0;">' + storeName + '</div>'
                });
                infowindow.open(map, marker);
                map.setCenter(coords);
                
                $(".storePosition").off('click').on('click', function(){
                    map.panTo(coords);  
                });
            } 
        });    
        
        var userAddress = $("#delevery_address").val();
        
        if(userAddress != "" ) {
            $(".userPosition").css("display", "inline");
            
            geocoder.addressSearch(userAddress, function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    var coords2 = new kakao.maps.LatLng(result[0].y, result[0].x);
                    
                    var marker2 = new kakao.maps.Marker({
                        map: map,
                        position: coords2
                    });
                    
                    var infowindow2 = new kakao.maps.InfoWindow({
                        content: '<div style="width:150px;text-align:center;padding:3px 0;">배달받을위치</div>'
                    });
                    infowindow2.open(map, marker2);
                    
                    $(".userPosition").off('click').on('click', function(){
                        map.panTo(coords2);  
                    });
                } 
            }); 
        }
    }
    
    // 정보 탭 클릭 시 지도 초기화
    $("ul.tab > li").eq(1).on('click', function() {
        setTimeout(function() {
            if (map) {
                map.relayout(); // 지도 다시 그리기
            } else {
                initMap(); // 첫 클릭 시 지도 초기화
            }
        }, 100);
    });
});
</script>