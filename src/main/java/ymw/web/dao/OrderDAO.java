package ymw.web.dao;

import java.util.List;

import ymw.web.dto.Cart;

public interface OrderDAO {

	// 메뉴 총합가격 계산시 배달팁 가져오기
	int getDeleveryTip(long storeId);
	
	//	메뉴 총합가격 계산시 음식가격
	List<Integer> foodPriceList(List<Cart> cartList);
	
	//	메뉴 총합가격 계산시 음식 추가 옵션가격
	List<Integer> optionPriceList(List<Cart> cart);
}