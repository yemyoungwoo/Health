package ymw.web.dao;

import java.util.List;

import ymw.web.dto.Cart;
import ymw.web.dto.OrderDetail;
import ymw.web.dto.OrderInfo;
import ymw.web.dto.OrderList;

public interface OrderDAO {

	// 메뉴 총합가격 계산시 배달팁 가져오기
	int getDeleveryTip(long storeId);
	
	//	메뉴 총합가격 계산시 음식가격
	List<Integer> foodPriceList(List<Cart> cartList);
	
	//	메뉴 총합가격 계산시 음식 추가 옵션가격
	List<Integer> optionPriceList(List<Cart> cart);
	
	// 주문 정보 입력
	void order(OrderInfo info);
	
	// 주문 상세정보 입력
	void orderDetail(OrderDetail[] detail, long userId);
	
	//주문한 내역 가져오기
	List<OrderList> orderList(long userId);
	
	// 주문목록 상세보기 페이지
	OrderList orderListDetail(String orderNum);
}