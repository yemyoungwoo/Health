<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/modal.css">
<link rel="stylesheet" href="/css/user/myReview.css">

<%@ include file="/WEB-INF/view/include/header.jsp" %>

<c:set var="address1Value" value="35408" />
<c:if test="${not empty BMaddress}">
    <c:set var="address1Value" value="${BMaddress.address1 }" />
</c:if>

<div class="wrap review_wrap">
    <section class="title">
        <h1>내가 쓴 리뷰</h1>
        <p>작성한 리뷰를 한눈에 확인하고 수정하거나 삭제할 수 있어요.</p>
    </section>

    <main>
        <c:choose>
            <c:when test="${empty reviewList}">
                <div class="empty_box">
                    <p>아직 작성한 리뷰가 없어요.</p>
                    <a href="/store/100/${address1Value }" class="btn primary">가게 둘러보기</a>
                </div>
            </c:when>
            <c:otherwise>
                <ul class="my_review_list">
                    <c:forEach items="${reviewList }" var="review">
                    <li>
                            <div class="store_info">
                                <div class="thumb">
                                    <c:choose>
                                        <c:when test="${not empty review.storeImg}">
                                            <img src="${review.storeImg }" alt="${review.storeName }">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/img/none.gif" alt="no image">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="text">
                                    <h2>${review.storeName }</h2>
                                    <span class="date">
                                        <fm:formatDate value="${review.regiDate }" pattern="yyyy.MM.dd HH:mm" />
                                    </span>
                                </div>
                                <a class="detail_link" href="/store/detail/${review.storeId }">가게보기</a>
                            </div>

                            <div class="score_box_static">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="far fa-star <c:if test='${i <= review.score }'>fas</c:if>"></i>
                                </c:forEach>
                                <span class="score_text">${review.score }점</span>
                            </div>

                            <p class="content">${review.reviewContent }</p>

                            <c:if test="${not empty review.reviewImg}">
                                <div class="review_image">
                                    <img src="${review.reviewImg }" alt="review image">
                                </div>
                            </c:if>

                            <div class="actions">
                                <div class="btn line review_modify_btn"
                                        data-order-num="${review.orderNum }"
                                        data-store-id="${review.storeId }"
                                        data-score="${review.score }"
                                        data-content="${fn:escapeXml(review.reviewContent) }"
                                        data-img="${review.reviewImg }">
                                    수정
                                </div>
                                <div class="btn gray review_delete_btn"
                                        data-order-num="${review.orderNum }">
                                    삭제
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
    </main>
</div>

<%@ include file="/WEB-INF/view/modal/modal_review.jsp" %>
<%@ include file="/WEB-INF/view/include/nav.jsp" %>
<%-- <%@ include file="/WEB-INF/view/include/footer.jsp" %> --%>

<script src="/js/util/util.js"></script>
<script src="/js/user/myReview.js"></script>

</body>
</html>

