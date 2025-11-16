package ymw.web.service;

import javax.servlet.http.HttpSession;

import ymw.web.dto.CartList;
import ymw.web.dto.OrderInfo;
import ymw.web.login.LoginService;

public interface OrderService {

	//	장바구니에 담긴 금액과 db의 금액이 같은지 확인
	long orderPriceCheck(CartList cartList);

	public String order(CartList cart, OrderInfo info, LoginService user, HttpSession session);
	
}