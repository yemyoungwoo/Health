package ymw.web.util;

import ymw.web.dto.Cart;

public class FoodPriceCalc {

	public static int foodPriceCalc(Cart cart) {
		int[] optionPrice = cart.getOptionPrice();
		
		int optionPriceTotal = 0;
		if(optionPrice != null) {
			for(int i=0;i<optionPrice.length;i++) {
				optionPriceTotal += optionPrice[i];
			}
		}
		
		int foodPrice = cart.getFoodPrice();
		
		// 모달창 계산식과 동일: (기본가격 + 옵션가격) * 수량
		return (foodPrice + optionPriceTotal) * cart.getAmount();
	}
}