package ymw.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ymw.web.dto.Cart;
import ymw.web.dto.CartList;
import ymw.web.util.FoodPriceCalc;

@Controller
public class CartController {
	
	@ResponseBody
	@PostMapping("/addCart")
	public CartList addCart(
			Cart cart, 
			long storeId, 
			String storeName, 
			Integer deleveryTip,
			@RequestParam(required = false) String[] optionName,
			@RequestParam(required = false) long[] optionId,
			@RequestParam(required = false) int[] optionPrice,
			HttpSession session) {
		
		// RequestParam으로 받은 배열을 Cart에 설정
		if (optionName != null) {
			cart.setOptionName(optionName);
		}
		if (optionId != null) {
			cart.setOptionId(optionId);
		}
		if (optionPrice != null) {
			cart.setOptionPrice(optionPrice);
		}

		// 디버깅: 옵션 데이터 확인
		System.out.println("받은 Cart 데이터:");
		System.out.println("  Gym: " + cart.getFoodId());
		System.out.println("  GymPrice: " + cart.getFoodPrice());
		System.out.println("  amount: " + cart.getAmount());
//		System.out.println("  totalPrice (모달창에서 계산된 값): " + cart.getTotalPrice());
//		if (cart.getOptionPrice() != null) {
//			System.out.println("  optionPrice 배열: " + java.util.Arrays.toString(cart.getOptionPrice()));
//		} else {
//			System.out.println("  optionPrice: null");
//		}

		CartList cartList = (CartList) session.getAttribute("cartList");
		
		// 모달창에서 계산한 totalPrice를 우선 사용 (없으면 서버에서 계산)
		int totalPrice = cart.getTotalPrice();
		if (totalPrice == 0) {
			// totalPrice가 0이거나 없으면 서버에서 계산
			totalPrice = FoodPriceCalc.foodPriceCalc(cart);
//			System.out.println("서버에서 계산한 가격: " + totalPrice);
		} else {
//			System.out.println("모달창에서 전송된 가격 사용: " + totalPrice);
		}
		
		if(cartList == null) {
			List<Cart> newCart = new ArrayList<>();
			cart.setTotalPrice(totalPrice);
			newCart.add(cart);
			cartList = new CartList(storeId, storeName, totalPrice, deleveryTip, newCart);
		} else {
			List<Cart> prevCart = cartList.getCart();
			int prevCartTotal = cartList.getCartTotal();
			cartList.setCartTotal(prevCartTotal + totalPrice);
		
			if(prevCart.contains(cart)) {
				int cartIndex = prevCart.indexOf(cart);
				int amount = cart.getAmount();
				
				Cart newCart = prevCart.get(cartIndex);
				int newAmount = newCart.getAmount() + amount;
				int newTotal = newCart.getTotalPrice() + totalPrice;
				
				newCart.setAmount(newAmount);
				newCart.setTotalPrice(newTotal);
				prevCart.set(cartIndex, newCart);
			} else {
				cart.setTotalPrice(totalPrice);
				prevCart.add(cart);
			}
		}
		
		session.setAttribute("cartList", cartList);
        
//        System.out.println("cartList = " + cartList);

		return cartList;
	}
	
	@ResponseBody
	@GetMapping("/cartList")
	public CartList cartList(HttpSession session) {
		CartList cartList = (CartList) session.getAttribute("cartList");
		if (cartList != null) {
			return cartList;
		}
		return null;
	}
	
	@ResponseBody
	@DeleteMapping("/cartAll")
	public void deleteAllCart(HttpSession session) {
		session.removeAttribute("cartList");
	}

	@ResponseBody
	@DeleteMapping("/cartOne")
	public CartList deleteOneCart(HttpSession session, int index) {
		CartList cartList = (CartList) session.getAttribute("cartList");
		if (cartList == null) {
			return null;
		}
		int cartTotal = cartList.getCartTotal();
		List<Cart> cart = cartList.getCart();
		int removeCartPrice = cart.get(index).getTotalPrice();
		
		cart.remove(index);
		
		if(cart.size() == 0) {
			session.removeAttribute("cartList");
			return null;
		}
		
		cartTotal -=  removeCartPrice;
		cartList.setCartTotal(cartTotal);
		return cartList;
	}
	@ResponseBody
	@PatchMapping("/cartAmount")
	public CartList cartAmount(int cartNum, String clickBtn, HttpSession session) {
	    CartList cartList = (CartList) session.getAttribute("cartList");
	    List<Cart> cart = cartList.getCart();
	    
	    Cart prevCart = cart.get(cartNum);
	    
	    int amount = prevCart.getAmount();
	    int foodPrice = prevCart.getTotalPrice() / amount;
	    int total = cartList.getCartTotal() - prevCart.getTotalPrice();
	    
	    if(clickBtn.equals("plus")) {
	        amount++;
	        foodPrice = foodPrice * amount;
	        
	        prevCart.setAmount(amount);
	        prevCart.setTotalPrice(foodPrice);
	        
	    } else {
	        if (amount <= 1) {
	            return cartList;
	        }
	        amount--;
	        foodPrice = foodPrice * amount;
	        
	        prevCart.setAmount(amount);
	        prevCart.setTotalPrice(foodPrice);
	    }
	    
	    cartList.setCartTotal(total + foodPrice);
	    cart.set(cartNum, prevCart);
	    
	    return cartList;
	}
}
