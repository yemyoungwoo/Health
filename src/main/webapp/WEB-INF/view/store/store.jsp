<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>

<link rel="stylesheet" href="/css/store/store.css">
<link rel="stylesheet" href="/css/store/store-li.css">

<%@ include file="/WEB-INF/view/include/header.jsp" %>


    <!-- 콘텐츠 -->
    <main>
        <div class="container">
            <div class="category" data-category="${category }">
                <ul>
                    <li data-category ='100' onclick="location.href='/store/100/${address1 }'"><span>파프리카짐</span></li>
                    <li data-category ='101' onclick="location.href='/store/101/${address1 }'"><span>스포애니</span></li>
                    <li data-category ='102' onclick="location.href='/store/102/${address1 }'"><span>고투</span></li>
                    <li data-category ='103' onclick="location.href='/store/103/${address1 }'"><span>바디스펙</span></li>
                    <li data-category ='104' onclick="location.href='/store/104/${address1 }'"><span>헬스보이</span></li>
                </ul>
            </div>

			<input type="hidden" value="${address1 }" class="address1">

           <div class="option">
                <ul>    
                	<li data-sort="기본순">기본순</li>
                    <li data-sort="별점 높은 순">별점 높은 순</li>
                    <li data-sort="리뷰 많은 순">리뷰 많은 순</li>
                </ul> 
           </div>
           
           

            <div class="box">
				
				<c:if test="${empty storeList }">
					<img class="temp_img" alt="이미지" src="/img/temp2.png">
					<style>main .box {background: #F6F6F6; max-width: 100%; }</style>
				</c:if>
				
				
                <ul class="store">
                	<c:set var="store_admin" value="/store" />
                	<c:forEach items="${storeList }" var="storeList">
                    	<%@ include file="/WEB-INF/view/store/store-li.jsp" %>
                    </c:forEach>
                </ul>
            </div>

        </div>
    </main>
     <!-- 콘텐츠 -->
      
     


	<script type="text/javascript" src="/js/store/store.js" ></script>
    
</body>
</html>