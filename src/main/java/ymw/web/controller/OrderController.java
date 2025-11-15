package ymw.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ymw.web.dto.Cart;
import ymw.web.dto.CartList;
import ymw.web.dto.OrderInfo;
import ymw.web.login.LoginService;
import ymw.web.service.OrderService;
import ymw.web.util.CreateOrderNum;
import ymw.web.util.FoodPriceCalc;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/order")
	public String order(HttpSession session, Model model, @AuthenticationPrincipal LoginService user) {

		CartList cartList = (CartList) session.getAttribute("cartList");

		model.addAttribute("cartList", cartList);

		System.out.println(user);

		if (user != null) {
			model.addAttribute("user", user.getUser());
		}
		String orderNum = CreateOrderNum.createOrderNum();
		model.addAttribute("orderNum", orderNum);
		return "order/order";
	}
	
	@ResponseBody
	@PostMapping("/order/payment-cash")
	public ResponseEntity<String> payment(HttpSession session, OrderInfo orderInfo, long totalPrice, @AuthenticationPrincipal LoginService user) throws IOException {
	    
	    CartList cartList = (CartList) session.getAttribute("cartList");
	    
	    long orderPriceCheck = orderService.orderPriceCheck(cartList);
	    
	    System.out.println("계산금액 = " + totalPrice + " 실제 계산해야할 금액 = " + orderPriceCheck );
	    
	    if(orderPriceCheck == totalPrice) {
	        orderService.order(cartList, orderInfo, user, session);
	        session.removeAttribute("cartList");
	    }

	    return new ResponseEntity<>(HttpStatus.OK);
	}
}
